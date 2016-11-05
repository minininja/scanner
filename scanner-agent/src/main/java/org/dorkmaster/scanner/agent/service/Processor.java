package org.dorkmaster.scanner.agent.service;

import org.dorkmaster.scanner.agent.cmd.Command;

public interface Processor {
    boolean canProcess(Command command);
    void process(Command command);
}
