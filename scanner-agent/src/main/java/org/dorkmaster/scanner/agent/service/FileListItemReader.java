package org.dorkmaster.scanner.agent.service;

import org.dorkmaster.scanner.agent.model.Item;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class FileListItemReader implements ItemReader<Item> {

    @Override
    public Item read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
