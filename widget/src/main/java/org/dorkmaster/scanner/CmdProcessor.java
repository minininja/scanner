package org.dorkmaster.scanner;

import org.dorkmaster.scanner.command.Command;
import org.dorkmaster.scanner.processor.Processor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CmdProcessor {

    private List<Processor> processors;

    public CmdProcessor addProcessor(Processor processor) {
        if (null == processors ) {
            processors = new LinkedList<>();
        }
        processors.add(processor);
        return this;
    }

    public void process(Command cmd) {
//        System.out.println("CMD: " + cmd.getClass().getName());

        for (Processor p : processors) {
            if (p.canProcess(cmd)) {
                p.process(cmd);
            }
//            else {
//                System.out.println("Can't process with: " + p.getClass().getName());
//            }
        }
    }

}
