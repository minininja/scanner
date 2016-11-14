package org.dorkmaster.scanner.command;

import java.util.Collection;

public class ScanCmd implements Command {
    private String path;
    private Collection<Integer> sizes;

    public ScanCmd(String path, Collection<Integer> sizes) {
        this.path = path;
        this.sizes = sizes;
    }

    public String getPath() {
        return path;
    }

    public Collection<Integer> getSizes() {
        return sizes;
    }

    @Override
    public String name() {
        return "scan";
    }

}
