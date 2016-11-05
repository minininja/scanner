package org.dorkmaster.scanner.agent.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.dorkmaster.scanner.agent.model.FileRef;
import org.dorkmaster.scanner.agent.service.Hasher;

import java.io.FileInputStream;
import java.io.IOException;

public class ByteHasher implements Hasher {
    private int bufSize;
    private String hashKey;

    public ByteHasher(int bufSize) {
        this.bufSize = bufSize;
        hashKey = "hash." + bufSize;
    }

    public String key() {
        return hashKey;
    }

    @Override
    public void hash(FileRef ref) throws IOException {
        byte[] buf = new byte[bufSize];
        try (FileInputStream in = new FileInputStream(ref.getFile())) {
            in.read(buf);
            ref.addHash(hashKey, DigestUtils.md5Hex(in));
        }
            }

}
