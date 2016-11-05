package org.dorkmaster.scanner.processor;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.Command;
import org.dorkmaster.scanner.command.SaveCmd;
import org.dorkmaster.scanner.util.Settings;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class SaveProcessor extends AbstractProcessor {
    private Client client = null;

    public SaveProcessor(CmdProcessor cmdProcessor) throws UnknownHostException {
        super(cmdProcessor, SaveCmd.class);

        client = getClient(
                Settings.instance().value("es.hosts").asStrings(",", new String[]{"localhost"}),
                Settings.instance().value("es.ports").asInts(",", new Integer[]{9300})
        );

        System.out.println("Connected to " + client.admin().cluster().health(new ClusterHealthRequest()).actionGet().getClusterName());
    }

    @Override
    public void process(Command cmd) {
        SaveCmd saveCmd = (SaveCmd) cmd;
        try {
            initIndex(client, hostname());
            store(client, saveCmd);
        } catch (Exception e) {
            System.out.println("***** unable to saveCmd *****");
            System.out.println(saveCmd.getFile().getId() + " " + saveCmd.getFile().getFile().getAbsolutePath());
            e.printStackTrace();
            System.out.println("***** unable to saveCmd *****");
        }
    }

    private void initIndex(Client client, String indexName) {
        if (!client.admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet().isExists()) {
            client.admin().indices().create(new CreateIndexRequest().index(indexName)).actionGet();
        }
    }

    private void store(Client client, SaveCmd saveCmd) {
        if (StringUtils.isNotBlank(saveCmd.getFile().getId())) {
            // update to existing
            UpdateRequest request = new UpdateRequest();
            request.index(hostname());
            request.type(Settings.instance().value("es.doc.type").asString("file"));
            request.id(saveCmd.getFile().getId());
            request.doc(new Gson().toJson(saveCmd.getFile()));
            client.update(request).actionGet();
        }
        else {
            // new record
            saveCmd.getFile().setId(UUID.randomUUID().toString());
            IndexRequest request = new IndexRequest();
            request.index(hostname());
            request.type(Settings.instance().value("es.doc.type").asString("file"));
            request.id(saveCmd.getFile().getId());
            request.source(new Gson().toJson(saveCmd.getFile()));
            client.index(request).actionGet();
        }

    }

    private String hostname(){
        try {
            return Settings.instance().value("host").asString(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public Client getClient(String[] hosts, Integer[] ports) throws UnknownHostException {
        assert null != hosts;
        assert null != ports;
        assert hosts.length == ports.length;

        TransportClient tc = TransportClient.builder().build();
        for (int i = 0; i < hosts.length; i++) {
            tc.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hosts[i]), ports[i]));
        }
        return tc ;
    }

}
