package org.dorkmaster.scanner.processor;

import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.Command;
import org.dorkmaster.scanner.command.HashCmd;
import org.dorkmaster.scanner.command.SaveCmd;
import org.dorkmaster.scanner.command.FileCmd;
import org.dorkmaster.scanner.model.Hash;

public class FileProcessor extends AbstractProcessor  {
    public FileProcessor(CmdProcessor cmdProcessor) {
        super(cmdProcessor, FileCmd.class);
    }

    @Override
    public void doCmd(Command cmd) {
        FileCmd sf = (FileCmd) cmd;
        for (Hash hash: sf.getHashes()) {
            cmdProcessor.process(new HashCmd().setFile(sf.getFile()).setHash(hash));
        }
        cmdProcessor.process(new SaveCmd().setFile(sf));
    }
}
