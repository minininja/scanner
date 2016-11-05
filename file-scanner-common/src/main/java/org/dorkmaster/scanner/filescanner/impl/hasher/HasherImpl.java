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
public class HasherImpl implements Hasher {
    private Integer level;

    public HasherImpl(Integer  level) {
        this.level = level;
    }

    @Override
    public void doHash(FileReference fr) {
        try (FileInputStream in = new FileInputStream(fr.getFile())) {
            byte[] buf = new byte[level.intValue()];
            in.read(buf);
            fr.setHash(level.toString(), DigestUtils.md5Hex(buf));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HasherImpl)) {
            return false;
        }

        HasherImpl hasher = (HasherImpl) o;

        return level.equals(hasher.level);

    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
}
