package org.dorkmaster.scanner.filescanner.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjackson on 5/16/16.
 */
public class FileReference {
    private String host;
    private String id;
    private String path;
    private Long size;
    private Map<String, String> hashes = new HashMap<String,String>();
    private String name;
    private transient File file;

    public File getFile() {
        return file;
    }

    public FileReference setFile(File file) {
        this.file = file;
        setName(file.getName());
        setPath(file.getAbsolutePath());
        if (file.isFile() && file.canRead()) {
            setSize(file.length());
        }
        return this;
    }

    public String getHost() {
        return host;
    }

    public FileReference setHost(String host) {
        this.host = host;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileReference setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public FileReference setId(String id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public FileReference setPath(String path) {
        this.path = path;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public FileReference setSize(Long size) {
        this.size = size;
        return this;
    }

    public String getHash(String level) {
        return hashes.get(level);
    }

    public FileReference setHash(String level, String hash) {
        hashes.put(level, hash);
        return this;
    }
}
