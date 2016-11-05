package org.dorkmaster.scanner.processor;

import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.Command;
import org.dorkmaster.scanner.command.FileCmd;
import org.dorkmaster.scanner.command.ScanCmd;
import org.dorkmaster.scanner.model.Hash;
import org.dorkmaster.scanner.util.FileUtil;

import java.io.File;

public class ScanProcessor extends AbstractProcessor {

    public ScanProcessor(CmdProcessor cmdProcessor) {
        super(cmdProcessor, ScanCmd.class);
    }

    public void process(Command cmd) {
        ScanCmd grr = (ScanCmd) cmd;
        System.out.println("looking at " + grr.getPath());

        String path = grr.getPath();
        File file = new File(path);
        if (file.exists() && file.canRead()) {
            if (file.isDirectory()) {
                for (File f : FileUtil.scan(file)) {
                    cmdProcessor.process(
                            new ScanCmd(f.getAbsolutePath(), grr.getSizes())
                    );
                }
            } else if (file.isFile()) {
                FileCmd sf = new FileCmd(file);
                for (int size : grr.getSizes()) {
                    if (size > 0) {
                        sf.addHash(new Hash().setSize(size));
                    } else {
                        sf.addHash(new Hash().setFull(true).setSize(size));
                    }
                }
                cmdProcessor.process(sf);
            }
        }
    }
}
