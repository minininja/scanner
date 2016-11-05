import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.ScanCmd;
import org.dorkmaster.scanner.processor.FileProcessor;
import org.dorkmaster.scanner.processor.HashProcessor;
import org.dorkmaster.scanner.processor.RescanProcessor;
import org.dorkmaster.scanner.processor.SaveProcessor;
import org.dorkmaster.scanner.processor.ScanProcessor;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    CmdProcessor cmdProcessor;

    public Main() throws UnknownHostException {
        cmdProcessor = new CmdProcessor();
        new ScanProcessor(cmdProcessor);
        new RescanProcessor(cmdProcessor);
        new HashProcessor(cmdProcessor);
        new FileProcessor(cmdProcessor);
        new SaveProcessor(cmdProcessor);
    }

    public void scan(String path, Collection<Integer> sizes) {
        cmdProcessor.process(new ScanCmd(path, sizes));
    }

    public void rescan(String fileId, String path, Collection<Integer> sizes) {

    }

    public static Collection<Integer> toSizes(int offset, String[] args) {
        List<Integer> sizes = new ArrayList<>(args.length);
        for (int i = offset; i < args.length; i++) {
            sizes.add(Integer.parseInt(args[i]));
        }
        return sizes;
    }

    public static void main(String[] args) {
        try {
            if (args[0].equalsIgnoreCase("scan")) {
                new Main().scan(args[1], toSizes(2, args));
            } else if (args[0].equalsIgnoreCase("rescan")) {
                new Main().rescan(args[1], args[2], toSizes(3, args));
            }
        }
        catch(UnknownHostException e) {
            System.out.println("Please check your configuration, unable to connect to index server");
            e.printStackTrace();
        }
    }
}
