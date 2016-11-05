package org.dorkmaster.scanner.agent.processor;

import org.dorkmaster.scanner.agent.cmd.Command;
import org.dorkmaster.scanner.agent.cmd.ScanCommand;
import org.dorkmaster.scanner.agent.model.Item;
import org.dorkmaster.scanner.agent.service.Processor;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ScanProcessor implements Processor {
    Queue<File> fileQueue = new ArrayBlockingQueue<File>(100);
    Queue<Item> processedQueue = new ArrayBlockingQueue<Item>(100);

    @Override
    public boolean canProcess(Command cmd) {
        return cmd instanceof ScanCommand;
    }

    @Override
    public void process(Command cmd) {
        File file = new File(((ScanCommand) cmd).getStartingPath());
        if (file.canRead()) {
            fileQueue.add(file);
        }
    }
}
