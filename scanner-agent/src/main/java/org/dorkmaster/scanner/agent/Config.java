package org.dorkmaster.scanner.agent;

public class Config {
    private static final Config instance = new Config();

    public static void init(String esHost, int esPort, String host) {
        instance.esHost = esHost;
        instance.esPort = esPort;
        instance.host = host;
    }

    public static Config getInstance() {
        return instance;
    }

    private String esHost;
    private int esPort = 9300;
    private String host;

    protected Config() {

    }

    public String getEsHost() {
        return esHost;
    }

    public int getEsPort() {
        return esPort;
    }

    public String getHost() {
        return host;
    }
}
