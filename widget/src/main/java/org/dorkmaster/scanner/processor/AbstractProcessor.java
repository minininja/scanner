package org.dorkmaster.scanner.processor;

import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.Command;

public abstract class AbstractProcessor implements Processor {

    private Class clazz;
    protected CmdProcessor cmdProcessor;

    public AbstractProcessor(CmdProcessor cmdProcessor, Class clazz) {
        this.cmdProcessor = cmdProcessor;
        this.clazz = clazz;
        cmdProcessor.addProcessor(this);
    }

    public boolean canProcess(Command cmd) {
        return clazz.isInstance(cmd);
    }
}
