package org.dorkmaster.discovery.mesg;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by mjackson on 10/16/2016.
 */
public class Beacon {
    private static final byte[] PROTOCOL = "MDP".getBytes();
    private static final byte VERSION = new Integer(1).byteValue();
    private static final int LONG_LENGTH = Long.BYTES;
    private static final int INT_LENGTH = Integer.BYTES;
    public static final int BEACON_LENGTH = 24;

    protected InetAddress ip;
    protected UUID peer;
    protected int port;

    public static byte[] beacon(UUID uuid, int port) {
        return ByteBuffer.allocate(BEACON_LENGTH)
                .put(PROTOCOL)
                .put(VERSION)
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .putInt(port)
                .array();
    }

    private static byte[] range(byte[] buf, int offset, int max) {
        return Arrays.copyOfRange(buf, offset, offset + max);
    }

    public static Beacon fromBytes(InetAddress addr, byte[] buf) {
        Beacon beacon = null;
        boolean pCheck = Arrays.equals(PROTOCOL, Arrays.copyOfRange(buf, 0, PROTOCOL.length));
        boolean vCheck = VERSION == buf[PROTOCOL.length];
        if (pCheck && vCheck) {
            beacon = new Beacon();
            beacon.ip = addr;
            beacon.peer = UUID.nameUUIDFromBytes(range(buf, PROTOCOL.length + 1, LONG_LENGTH * 2));
            beacon.port = ByteBuffer
                    .wrap(range(buf, PROTOCOL.length + 1 + LONG_LENGTH * 2, INT_LENGTH))
                    .getInt();
        }
        return beacon;
    }

    public InetAddress getIp() {
        return ip;
    }

    public UUID getPeer() {
        return peer;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Beacon{" +
                "ip=" + ip +
                ", peer=" + peer +
                ", port=" + port +
                '}';
    }
}
