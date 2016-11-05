package org.dorkmaster.scanner.processor;

import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.Command;
import org.dorkmaster.scanner.command.RescanCmd;

public class RescanProcessor extends AbstractProcessor {

    public RescanProcessor(CmdProcessor cmdProcessor) {
        super(cmdProcessor, RescanCmd.class);
    }

    @Override
    public void process(Command cmd) {

    }
}
