package org.dorkmaster.scanner.command;

import org.dorkmaster.scanner.model.Hash;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileCmd implements Command {
    private String id;
    private File file;
    private List<Hash> hashes;

    public FileCmd(String id, File file) {
        this.id = id;
        this.file = file;
    }

    public FileCmd(File file) {
        this.file = file;
    }

    public FileCmd setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public File getFile() {
        return file;
    }

    public List<Hash> getHashes() {
        return hashes;
    }

    public FileCmd addHash(Hash hash) {
        if (null == hashes) {
            hashes = new LinkedList<>();
        }
        hashes.add(hash);
        return this;
    }

    @Override
    public String name() {
        return "file";
    }

}
