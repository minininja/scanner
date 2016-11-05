package org.dorkmaster.scanner.agent.service.impl;

import org.dorkmaster.scanner.agent.model.FileRef;
import org.dorkmaster.scanner.agent.model.Item;
import org.springframework.batch.item.ItemProcessor;

public class HashWorker implements ItemProcessor<Item, Item> {
    @Override
    public Item process(Item item) throws Exception {
        item.setFileRef(new FileRef(item.getFile()));
        item.getHasher().hash(item.getFileRef());
        return item;
    }
}
