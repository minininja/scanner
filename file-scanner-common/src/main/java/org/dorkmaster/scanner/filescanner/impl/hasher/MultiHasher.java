package org.dorkmaster.scanner.filescanner.impl.hasher;

import org.dorkmaster.scanner.filescanner.Hasher;
import org.dorkmaster.scanner.filescanner.model.FileReference;

/**
 * Created by mjackson on 5/16/16.
 */
public class MultiHasher implements Hasher {
    private Hasher[] hashers;

    public MultiHasher(Hasher[] hashers) {
        this.hashers = hashers;
    }

    @Override
    public void doHash(FileReference fr) {
        for (Hasher h : hashers) {
            h.doHash(fr);
        }
    }
}
