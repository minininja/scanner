package org.dorkmaster.scanner.processor;

import org.dorkmaster.scanner.command.Command;

public interface Processor {
    boolean canProcess(Command cmd);

    void process(Command cmd);
}
