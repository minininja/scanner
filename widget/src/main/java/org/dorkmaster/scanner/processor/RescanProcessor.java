package org.dorkmaster.scanner.processor;

import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.Command;
import org.dorkmaster.scanner.command.RescanCmd;

public class RescanProcessor extends AbstractProcessor {

    public RescanProcessor(CmdProcessor cmdProcessor) {
        super(cmdProcessor, RescanCmd.class);
    }

    @Override
    public void doCmd(Command cmd) {
        // we should have a hash code, ask an updater for records associated with this
        // agent and that hash code.  that should result in a list of files.  we'll
        // spin through the files creating new hashes.  We'll then send updates to the
        // updater to update elastic search.
    }
}
