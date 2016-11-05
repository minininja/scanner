package org.dorkmaster.scanner.agent.service;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionFactory {
    String host;
    int port = 9300;

    public ConnectionFactory setHost(String host) {
        this.host = host;
        return this;
    }

    public ConnectionFactory setPort(int port) {
        this.port = port;
        return this;
    }

    public Client getConnection()  {
        try {
            TransportClient tc = TransportClient.builder().build();
            tc.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            return tc;
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}
