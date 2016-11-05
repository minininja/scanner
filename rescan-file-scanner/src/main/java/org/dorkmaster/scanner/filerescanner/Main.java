package org.dorkmaster.scanner.filerescanner;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.dorkmaster.scanner.filescanner.Hasher;
import org.dorkmaster.scanner.filescanner.impl.BaseScannerImpl;
import org.dorkmaster.scanner.filescanner.impl.hasher.FullHasherImpl;
import org.dorkmaster.scanner.filescanner.impl.hasher.HasherImpl;
import org.dorkmaster.scanner.filescanner.model.FileReference;

import java.io.File;
import java.util.List;

/**
 * Created by mjackson on 5/17/16.
 */
public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("h", true, "host name");
        options.addOption("H", true, "host name");
        options.addOption("P", true, "host name");
        options.addOption("s", true, "source field");
        options.addOption("b", true, "hash size");

        CommandLineParser parser = new DefaultParser();
        try {
            String host;
            CommandLine cmd = parser.parse(options, args);

            EsDao dao = new EsDao(
                    cmd.getOptionValue("H"),
                    NumberUtils.toInt(cmd.getOptionValue("P"), 9300),
                    host = cmd.getOptionValue("h")
            );

            Hasher hasher = null;
            int dstBuffer = NumberUtils.toInt(cmd.getOptionValue("b"), -1);
            if (dstBuffer > 0) {
                hasher = new HasherImpl(dstBuffer);
            }
            else {
                hasher = new FullHasherImpl();
            }

            List<FileReference> refs;
            for (String hash: cmd.getArgs()) {
                refs = dao.findByHash(host, cmd.getOptionValue("s"), hash);
                for (FileReference ref : refs) {
                    new BaseScannerImpl(hasher).scan(ref.setFile(new File(ref.getPath())), dao);
                }
            }
        } catch (ParseException e) {
            System.out.println( "Unexpected exception:" + e.getMessage() );
            System.out.println("\n");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ant", options );
        }

    }
}
