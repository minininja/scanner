package org.dorkmaster.scanner.agent;

import org.dorkmaster.scanner.agent.cmd.Command;
import org.dorkmaster.scanner.agent.service.Processor;

import java.util.LinkedList;
import java.util.List;

public class CommandProcessor {
    List<Processor> processors = new LinkedList<>();

    CommandProcessor add(Processor processor) {
        processors.add(processor);
        return this;
    }

    void processCommand(Command cmd) {
        for (Processor processor : processors) {
            if (processor.canProcess(cmd)) {
                processor.process(cmd);
            }
        }
    }
}
