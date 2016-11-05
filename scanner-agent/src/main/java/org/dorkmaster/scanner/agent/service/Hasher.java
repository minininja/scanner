package org.dorkmaster.scanner.agent.service;

import org.dorkmaster.scanner.agent.model.FileRef;

import java.io.IOException;

public interface Hasher {
     void hash(FileRef fileRef) throws IOException;
}
