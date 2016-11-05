package org.dorkmaster.scanner.filescanner.impl.hasher;

import org.apache.commons.codec.digest.DigestUtils;
import org.dorkmaster.scanner.filescanner.Hasher;
import org.dorkmaster.scanner.filescanner.model.FileReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by mjackson on 5/16/16.
 */
public class FullHasherImpl implements Hasher {
    @Override
    public void doHash(FileReference fr) {
        try (FileInputStream in = new FileInputStream(fr.getFile())) {
            fr.setHash("FULL", DigestUtils.md5Hex(in));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
