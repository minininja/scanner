package org.dorkmaster.discovery;

import com.google.common.eventbus.EventBus;
import org.dorkmaster.discovery.event.BeaconEvent;
import org.dorkmaster.discovery.mesg.Beacon;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Created by mjackson on 10/16/2016.
 */
public class Discovery {
    public static final int DISCOVERY_PORT = 5555;
    private static final int DELAY = 1 * 1000;

    private DatagramSocket socket;
    private UUID uuid = UUID.randomUUID();
    private int port = 5556 + new Random().nextInt(4096);

    private EventBus eventBus;

    private Runnable sender = new Runnable() {
        @Override
        public void run() {
            try {
                Set<InetAddress> broadcastAddresss = new HashSet<>();

                NetworkInterface ni = null;
                for (Enumeration<NetworkInterface> it = NetworkInterface.getNetworkInterfaces(); it.hasMoreElements();) {
                    ni = it.nextElement();
                    if (!ni.isLoopback() ) {
                        for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                            InetAddress bc = ia.getBroadcast();
                            if (null != bc) {
                                broadcastAddresss.add(bc);
                            }
                        }
                    }
                }

                System.out.println("send to ip: " + broadcastAddresss);

                byte[] buffer;
                while (true) {
                    System.out.println("Sending beacon");
                    buffer = Beacon.beacon(uuid,port);
                    for (InetAddress sendTo : broadcastAddresss) {
                        System.out.println("  to " + sendTo);
                        socket.send(new DatagramPacket(buffer, Beacon.BEACON_LENGTH, sendTo, DISCOVERY_PORT));
                    }
                    Thread.sleep(DELAY);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable receiver = new Runnable() {
        @Override
        public void run() {
            try {
                byte[] buf = new byte[Beacon.BEACON_LENGTH];
                DatagramPacket packet = new DatagramPacket(buf, Beacon.BEACON_LENGTH);
                while (true) {
                    socket.receive(packet);
                    eventBus.post(new BeaconEvent(Beacon.fromBytes(packet.getAddress(), packet.getData())));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public Discovery(EventBus eventBus) {
        this.eventBus = eventBus;
        try {
            socket = new DatagramSocket(DISCOVERY_PORT);
            new Thread(receiver).start();
            new Thread(sender).start();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        eventBus.register(new BeaconEvent.BeaconEventHandler());
    }


    public static void main(String[] args) throws InterruptedException {
        new Discovery(new EventBus());
        Thread.sleep(20*DELAY);
    }
}
