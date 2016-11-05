package org.dorkmaster.scanner.filescanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.math.NumberUtils;
import org.dorkmaster.scanner.filescanner.impl.BaseScannerImpl;
import org.dorkmaster.scanner.filescanner.impl.hasher.FullHasherImpl;
import org.dorkmaster.scanner.filescanner.impl.hasher.HasherImpl;
import org.dorkmaster.scanner.filescanner.impl.hasher.MultiHasher;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mjackson on 5/16/16.
 */
public class Main {

    public static final String ELASTIC_HOST = "H";
    public static final String ELASTIC_PORT = "P";
    public static final String BUF_SIZE = "b";
    public static final String INDEX_NAME = "h";
    public static final String REMOVE_INDEX = "r";

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(INDEX_NAME, true, "host name");
        options.addOption(ELASTIC_HOST, true, "host name");
        options.addOption(ELASTIC_PORT, true, "host name");
        options.addOption(BUF_SIZE, true, "hash buffer size");
        options.addOption(REMOVE_INDEX, false, "remove the index before scanning");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            String elasticHost = cmd.getOptionValue(ELASTIC_HOST);
            int elasticPort = NumberUtils.toInt(cmd.getOptionValue(ELASTIC_PORT), 9300);
            String index = cmd.getOptionValue(INDEX_NAME).toLowerCase();
            String[] bufferSize = cmd.getOptionValue(BUF_SIZE).split(",");
            boolean removeIndex = cmd.hasOption(REMOVE_INDEX);

            Set<Hasher> hashes = new HashSet<Hasher>();
            int size;
            for (String bs : bufferSize) {
                size = NumberUtils.toInt(bs, -1);
                if (size > 0) {
                    hashes.add(new HasherImpl(size));
                }
                else {
                    hashes.add(new FullHasherImpl());
                }
            }
            Hasher finalHasher = new MultiHasher(hashes.toArray(new Hasher[hashes.size()]));

            ScannerCallback sc = new ESCallback(new ESHost[]{new ESHost().setHostname(elasticHost).setPort(elasticPort)}, index, "file", removeIndex);
            if (cmd.getArgs().length > 0) {
                for (String path : cmd.getArgs()) {
                    new BaseScannerImpl(finalHasher).setHost(index).scan(path, sc);
                }
            }
            else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "ant", options );
            }
        } catch (ParseException e) {
            System.out.println( "Unexpected exception:" + e.getMessage() );
            System.out.println("\n");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ant", options );
        }
    }
}
