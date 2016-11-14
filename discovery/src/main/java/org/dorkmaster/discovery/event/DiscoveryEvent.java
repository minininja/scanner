package org.dorkmaster.discovery.event;

import java.net.InetAddress;
import java.util.UUID;

public class DiscoveryEvent {
    InetAddress address;
    UUID uuid;
    int port;

    public DiscoveryEvent(InetAddress address, UUID uuid, int port) {
        this.address = address;
        this.uuid = uuid;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPort() {
        return port;
    }
}
