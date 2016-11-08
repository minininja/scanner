package org.dorkmaster.scanner.processor;

import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.Command;
import org.elasticsearch.common.cli.CliToolConfig;

public abstract class AbstractProcessor implements Processor {

    private Class clazz;
    protected CmdProcessor cmdProcessor;

    public AbstractProcessor(CmdProcessor cmdProcessor, Class clazz) {
        this.cmdProcessor = cmdProcessor;
        this.clazz = clazz;
        cmdProcessor.addProcessor(this);
    }

    public boolean canProcess(Object cmd) {
        return cmd instanceof CliToolConfig.Cmd && clazz.isInstance(cmd);
    }

    public void process(Object cmd) {
        doCmd((Command) cmd);
    }

    public abstract void doCmd(Command cmd);
}
