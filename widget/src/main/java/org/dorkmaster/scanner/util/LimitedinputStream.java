package org.dorkmaster.scanner.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LimitedinputStream extends FileInputStream {
    long counter = 0;
    long max = -1;

    public LimitedinputStream(File file, long max) throws FileNotFoundException {
        super(file);
        this.max = max;
    }

    @Override
    public int read() throws IOException {
        counter++;
        if (counter < max) {
            return super.read();
        }
        else {
            throw new EOFException();
        }
    }
}
