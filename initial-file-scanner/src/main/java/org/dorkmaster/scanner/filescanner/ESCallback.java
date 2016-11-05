package org.dorkmaster.scanner.filescanner;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.dorkmaster.scanner.filescanner.model.FileReference;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.util.UUID;

/**
 * Created by mjackson on 5/17/16.
 */
public class ESCallback implements ScannerCallback {
    private Client client;
    private ESHost[] hosts;
    private String index;
    private String document;

    public ESCallback(ESHost[] hosts, String index, String document, boolean removeIndex) {
        this.hosts = hosts;
        this.index = index;
        this.document = document;

        client = getClient();
        if (removeIndex) {
            removeIndex();
        }
        initIndex();
    }

    private void initIndex() {
        if (!client.admin().indices().exists(new IndicesExistsRequest().indices(new String[]{index})).actionGet().isExists()) {
            client.admin().indices().create(new CreateIndexRequest().index(index)).actionGet();
        }
    }

    private void removeIndex() {
        client.admin().indices().delete(new DeleteIndexRequest().indices(index)).actionGet();
    }

    private Client getClient() {
        boolean one = false;
        TransportClient tc = TransportClient.builder().build();
        for (ESHost host : hosts) {
            try {
                tc.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host.getHostname()), host.getPort()));
                one = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return one ? tc : null;
    }

    public void store(FileReference fileReference) {
        if (StringUtils.isBlank(fileReference.getId())) {
            fileReference.setId(UUID.randomUUID().toString());
            IndexRequest request = new IndexRequest();
            request.index(index);
            request.type(document);
            request.id(fileReference.getId());
            request.source(new Gson().toJson(fileReference));
            client.index(request).actionGet();
        } else {
            // update
            UpdateRequest request = new UpdateRequest();
            request.index(index);
            request.type(document);
            request.id(fileReference.getId());
            request.doc(new Gson().toJson(fileReference));
            client.update(request).actionGet();
        }
    }
}
