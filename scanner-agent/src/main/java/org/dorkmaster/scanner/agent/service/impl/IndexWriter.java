package org.dorkmaster.scanner.agent.service.impl;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.dorkmaster.scanner.agent.model.Item;
import org.dorkmaster.scanner.agent.service.ConnectionFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.UUID;

public class IndexWriter implements ItemWriter<Item> {
    public static final String DOC = "file";
    private ConnectionFactory connectionFactory;

    public IndexWriter setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        return this;
    }

    @Override
    public void write(List<? extends Item> list) throws Exception {
        Client client = null;
        try {
            client = connectionFactory.getConnection();
            for (Item item: list) {
                if (StringUtils.isBlank(item.getFileRef().getId())) {
                    item.getFileRef().setId(UUID.randomUUID().toString());
                    IndexRequest request = new IndexRequest();
                    request.index(item.getHost());
                    request.type(DOC);
                    request.id(item.getFileRef().getId());
                    request.source(new Gson().toJson(item.getFileRef()));
                    client.index(request).actionGet();
                } else {
                    // update
                    UpdateRequest request = new UpdateRequest();
                    request.index(item.getHost());
                    request.type(DOC);
                    request.id(item.getFileRef().getId());
                    request.doc(new Gson().toJson(item.getFileRef()));
                    client.update(request).actionGet();
                }
            }
        }
        finally {
            if (client != null) {
                client.close();
            }
        }
    }
}
