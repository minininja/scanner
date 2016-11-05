package org.dorkmaster.scanner.filescanner;

public class ESHost {
    private String hostname;
    private int port;

    public String getHostname() {
        return hostname;
    }

    public ESHost setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public int getPort() {
        return port;
    }

    public ESHost setPort(int port) {
        this.port = port;
        return this;
    }
}
