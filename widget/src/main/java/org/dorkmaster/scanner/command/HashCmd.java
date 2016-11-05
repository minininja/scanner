package org.dorkmaster.scanner.command;

import org.dorkmaster.scanner.model.Hash;

import java.io.File;

public class HashCmd implements Command {
    private File file;
    private Hash hash;

    public File getFile() {
        return file;
    }

    public HashCmd setFile(File file) {
        this.file = file;
        return this;
    }

    public Hash getHash() {
        return hash;
    }

    public HashCmd setHash(Hash hash) {
        this.hash = hash;
        return this;
    }
}
