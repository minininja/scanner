package org.dorkmaster.scanner.agent.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileRef {
    private String id;
    private String name;
    private String path;
    private long length;
    private Map<String, String> hashes = new HashMap<String, String>();
    private transient File file;

    public FileRef(File file) {
        this.file = file;
        this.name = file.getName();
        this.path = file.getAbsolutePath();
        this.length = file.length();
    }

    public FileRef addHash(String key, String hash) {
        hashes.put(key, hash);
        return this;
    }

    public FileRef setId(String id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public long getLength() {
        return length;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getHashes() {
        return hashes;
    }

    public File getFile() {
        return file;
    }
}
