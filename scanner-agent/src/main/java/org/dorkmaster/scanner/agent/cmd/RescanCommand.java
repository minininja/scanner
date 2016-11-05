package org.dorkmaster.scanner.agent.cmd;

public class RescanCommand implements Command {
    private int bufferSize;
    private String searchField;
    private String searchHash;

    public int getBufferSize() {
        return bufferSize;
    }

    public RescanCommand setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public String getSearchField() {
        return searchField;
    }

    public RescanCommand setSearchField(String searchField) {
        this.searchField = searchField;
        return this;
    }

    public String getSearchHash() {
        return searchHash;
    }

    public RescanCommand setSearchHash(String searchHash) {
        this.searchHash = searchHash;
        return this;
    }
}
