package org.dorkmaster.scanner.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FileUtil {

    public static Collection<File> scan(final File file) {
        if (file.exists() && file.canRead()) {
            if (file.isDirectory()) {
                return Arrays.stream(file.list()).map(a -> new File(file, a)).collect(Collectors.toList());
            }
        }
        return null;
    }
}
