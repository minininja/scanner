package org.dorkmaster.scanner.agent.model;

import org.dorkmaster.scanner.agent.service.Hasher;

import java.io.File;

public class Item {
    private File file;
    private Hasher hasher;
    private FileRef fileRef;
    private String host;

    public Item(Hasher hasher, File file, String host) {
        this.hasher = hasher;
        this.file = file;
        this.host = host;
    }

    public Item setFileRef(FileRef fileRef) {
        this.fileRef = fileRef;
        return this;
    }

    public File getFile() {
        return file;
    }

    public FileRef getFileRef() {
        return fileRef;
    }

    public Hasher getHasher() {
        return hasher;
    }

    public String getHost() {
        return host;
    }
}
