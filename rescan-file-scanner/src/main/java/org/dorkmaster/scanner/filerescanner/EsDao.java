package org.dorkmaster.scanner.filerescanner;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.dorkmaster.scanner.filescanner.ScannerCallback;
import org.dorkmaster.scanner.filescanner.model.FileReference;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mjackson on 5/17/16.
 */
public class EsDao implements ScannerCallback {
    private Client client;
    private String host;
    private int port;
    private String index;

    public EsDao(String host, int port, String index) {
        this.host = host;
        this.port = port;
        this.index = index;

        client = getClient();
    }

    private Client getClient() {
        TransportClient tc = TransportClient.builder().build();
        try {
            tc.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tc;
    }

    @Override
    public void store(FileReference fileReference) {
        Client client = null;
        try {
            client = getClient();
            if (StringUtils.isBlank(fileReference.getId())) {
                fileReference.setId(UUID.randomUUID().toString());
                IndexRequest request = new IndexRequest();
                request.index(index);
                request.type("file");
                request.id(fileReference.getId());
                request.source(new Gson().toJson(fileReference));
                client.index(request).actionGet();
            } else {
                // update
                UpdateRequest request = new UpdateRequest();
                request.index(index);
                request.type("file");
                request.id(fileReference.getId());
                request.doc(new Gson().toJson(fileReference));
                client.update(request).actionGet();
            }
        }
        finally {
            if (client != null) {
                client.close();
            }
        }
    }

    public List<FileReference> findByHash(String index, String searchHash, String hash) {
        SearchRequestBuilder request = getClient().prepareSearch(index)
                .setTypes("file")
                .setQuery(QueryBuilders.termQuery(searchHash, hash))
                .setFrom(0).setSize(10000)
                .setExplain(true);

        SearchResponse response = request.execute().actionGet();

        List<FileReference> results = new ArrayList<FileReference>(response.getHits().getHits().length);
        Gson g = new Gson();
        for (SearchHit hit : response.getHits().hits()) {
            results.add(g.fromJson(new String(hit.source()), FileReference.class));
        }
        return results;
    }
}
