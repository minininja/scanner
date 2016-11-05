import org.zeromq.ZMQ;

import java.util.Random;
import java.util.UUID;

/**
 * Created by mjackson on 10/12/16.
 */
public class Discovery {

    private ZMQ.Context context;
    private ZMQ.Socket pubSocket;
    private ZMQ.Socket subSocket;

    private UUID uuid = UUID.randomUUID();
    private int port = new Random().nextInt(1000) + 1025;

    public Discovery() {
        context = ZMQ.context(1);
        pubSocket = context.socket(ZMQ.PUB);
        pubSocket.bind("udp://*.5555");

        subSocket = context.socket(ZMQ.SUB);
        subSocket.bind("udp://*.5555");


        new Thread(beaconReceiever).start();
        new Thread(beaconSender).start();
    }

    private Runnable beaconReceiever = new Runnable() {
        public void run() {
            try {
                while (true) {
                    byte[] bytes = subSocket.recvStr(0).trim().getBytes();
                    System.out.println("Received beacon");
                    for (int i = 0; i < bytes.length; i++) {
                        System.out.print(bytes[i]);
                    }
                    System.out.println("\n\n");
                }
            }
            finally {
                pubSocket.close();
            }
        }
    };

    private Runnable beaconSender = new Runnable() {
        public void run() {
            try {
                while (true) {
                    byte[] beacon = Beacon.genBeacon(uuid, port);
                    pubSocket.send(beacon, 0);
                    Thread.sleep(1 * 1000); // 1 second
                }
            }
            catch(InterruptedException e) {

            }
            finally {
                pubSocket.close();
            }
        }
    };

    public static void main(String[] args) {
        try {
            new Discovery();
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
