package org.dorkmaster.scanner.agent.cmd;

public class ScanCommand implements Command {
    private int bufferSize;
    private String startingPath;

    public int getBufferSize() {
        return bufferSize;
    }

    public ScanCommand setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public String getStartingPath() {
        return startingPath;
    }

    public ScanCommand setStartingPath(String startingPath) {
        this.startingPath = startingPath;
        return this;
    }


}
