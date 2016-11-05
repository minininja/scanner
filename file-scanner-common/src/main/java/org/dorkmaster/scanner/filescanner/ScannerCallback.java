package org.dorkmaster.scanner.filescanner;

import org.dorkmaster.scanner.filescanner.model.FileReference;

/**
 * Created by mjackson on 5/16/16.
 */
public interface ScannerCallback {
    void store(FileReference fileReference);
}
