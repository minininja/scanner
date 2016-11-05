package org.dorkmaster.scanner.filescanner.impl;

import org.dorkmaster.scanner.filescanner.Hasher;
import org.dorkmaster.scanner.filescanner.Scanner;
import org.dorkmaster.scanner.filescanner.ScannerCallback;
import org.dorkmaster.scanner.filescanner.model.FileReference;

import java.io.File;

/**
 * Created by mjackson on 5/16/16.
 */
public class BaseScannerImpl implements Scanner {
    private Hasher hasher;
    private String host;

    public String getHost() {
        return host;
    }

    public BaseScannerImpl setHost(String host) {
        this.host = host;
        return this;
    }

    public BaseScannerImpl(Hasher hasher) {
        this.hasher = hasher;
    }

    public void scan(String path, ScannerCallback callback) {
        scan(new FileReference().setFile(new File(path)), callback);
    }

    public void scan(FileReference fr, ScannerCallback callback) {
        if (fr.getFile().isFile() && fr.getFile().canRead()) {
            hasher.doHash(fr);
            callback.store(fr);
        }
        else if (fr.getFile().canRead()) {
            for (String item : fr.getFile().list()) {
                scan(new FileReference().setFile(new File(fr.getFile(), item)), callback);
            }
        }
    }

}
