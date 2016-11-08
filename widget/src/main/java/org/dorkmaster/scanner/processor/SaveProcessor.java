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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class SaveProcessor extends AbstractProcessor {
    private Client client = null;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String toIndexName(String name) {
        return name.toLowerCase();
    }

    public SaveProcessor(CmdProcessor cmdProcessor) throws UnknownHostException {
        super(cmdProcessor, SaveCmd.class);

        client = getClient(
                Settings.instance().value("es.hosts").asStrings(",", new String[]{"localhost"}),
                Settings.instance().value("es.ports").asInts(",", new Integer[]{9300})
        );

        logger.info("Connected to {}", client.admin().cluster().health(new ClusterHealthRequest()).actionGet().getClusterName());
    }

    @Override
    public void doCmd(Command cmd) {
        SaveCmd saveCmd = (SaveCmd) cmd;
        try {
            initIndex(client, hostname());
            store(client, saveCmd);
        } catch (Exception e) {
            logger.warn("***** unable to saveCmd *****");
            logger.warn("{} {}", new Object[]{saveCmd.getFile().getId(), saveCmd.getFile().getFile().getAbsolutePath()}, e);
            logger.warn("***** unable to saveCmd *****");
        }
    }

    private void initIndex(Client client, String indexName) {
        if (!client.admin().indices().exists(new IndicesExistsRequest(toIndexName(indexName))).actionGet().isExists()) {
            client.admin().indices().create(new CreateIndexRequest().index(toIndexName(indexName))).actionGet();
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
            return Settings.instance().value("host").asString(InetAddress.getLocalHost().getHostName()).toLowerCase();
        } catch (UnknownHostException e) {
            // fallback
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
