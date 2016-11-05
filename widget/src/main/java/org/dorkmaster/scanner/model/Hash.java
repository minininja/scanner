package org.dorkmaster.scanner.model;

public class Hash {
    private boolean full = false;
    private int size;
    private String hash;

    public boolean isFull() {
        return full;
    }

    public Hash setFull(boolean full) {
        this.full = full;
        return this;
    }

    public int getSize() {
        return size;
    }

    public Hash setSize(int size) {
        this.size = size;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public Hash setHash(String hash) {
        this.hash = hash;
        return this;
    }
}
