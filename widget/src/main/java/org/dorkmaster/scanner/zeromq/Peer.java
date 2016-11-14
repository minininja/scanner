package org.dorkmaster.scanner.zeromq;

public class Peer {
    private String uuid;
    private PeerType type;
    private long port;

    public String getUuid() {
        return uuid;
    }

    public Peer setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public PeerType getType() {
        return type;
    }

    public Peer setType(PeerType type) {
        this.type = type;
        return this;
    }

    public long getPort() {
        return port;
    }

    public Peer setPort(long port) {
        this.port = port;
        return this;
    }

    public void disconnect() {
        // send a disconnect (in case)
        // disconnect the zmq sockets
    }
}
