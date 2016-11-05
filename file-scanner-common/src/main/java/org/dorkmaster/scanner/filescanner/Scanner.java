package org.dorkmaster.scanner.filescanner;

/**
 * Created by mjackson on 5/16/16.
 */
public interface Scanner {
    void scan(String path, ScannerCallback callback);
}
