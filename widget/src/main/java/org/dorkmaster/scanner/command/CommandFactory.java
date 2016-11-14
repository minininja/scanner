package org.dorkmaster.scanner.command;

import com.google.gson.Gson;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final byte[] PREFIX = "cmd".getBytes();
    private static final Map<String, Parser> DESERIALIZERS = new HashMap<>();

    static {
        DESERIALIZERS.put(
                "scan", new Parser() {
                    @Override
                    public Command parse(byte[] buf) {
                        return new Gson().fromJson(new String(buf), ScanCmd.class);
                    }
                }
        );
    }

    public static byte[] serialize(Command cmd) {
        StringBuffer sb = new StringBuffer();

        sb.append(cmd.name());
        int length = sb.length();
        sb.append(new Gson().toJson(cmd));

        byte[] payload = sb.toString().getBytes();

        return ByteBuffer
                .allocate(PREFIX.length + 1 + payload.length)
                .put(PREFIX)
                .put(new Integer(length).byteValue())
                .put(payload)
                .array();
    }

    public static Command deserialize(byte[] buf) {
        boolean prefix = Arrays.equals(PREFIX, Arrays.copyOfRange(buf, 0, PREFIX.length));
        if (prefix) {
            byte cnt = buf[PREFIX.length];
            int start = PREFIX.length + 1;

            byte[] name = Arrays.copyOfRange(buf, start, start + cnt);
            Parser p = DESERIALIZERS.get(new String(name));

            start = start + cnt;
            return p.parse(Arrays.copyOfRange(buf, start, buf.length));
        }
        return null;
    }

    interface Parser {
        Command parse(byte[] buf);
    }
}
