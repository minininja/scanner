package org.dorkmaster.scanner.command;

public class RescanCmd implements Command {
    private String fileId;
    private String path;
    private long[] sizes;

    public RescanCmd(String fileId, String path, long[] sizes) {
        this.fileId = fileId;
        this.path = path;
        this.sizes = sizes;
    }

    public String getFileId() {
        return fileId;
    }

    public String getPath() {
        return path;
    }

    public long[] getSizes() {
        return sizes;
    }

}
