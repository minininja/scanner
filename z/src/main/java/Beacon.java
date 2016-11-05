import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by mjackson on 10/13/16.
 */
public class Beacon {

    private UUID uuid;
    private int port;

gi    public static byte[] genBeacon(UUID uuid, int port) {
        return genBeacon(1, uuid, port);
    }

    public static byte[] genBeacon(Integer version, UUID uuid, int port) {
        return genBeacon("ZRE", version, uuid, port);
    }

    public static byte[] genBeacon(String prefix , Integer version,UUID uuid, int port) {
        byte[] buf = ByteBuffer
                .allocate(20)
                .put(prefix.getBytes())
                .put(version.byteValue())
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .putInt(port)
                .array();

        return buf;
    }

}
