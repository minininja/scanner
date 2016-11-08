package org.dorkmaster.scanner.processor;

import org.dorkmaster.scanner.command.Command;

public interface Processor {
    boolean canProcess(Object cmd);

    void process(Object cmd);
}
