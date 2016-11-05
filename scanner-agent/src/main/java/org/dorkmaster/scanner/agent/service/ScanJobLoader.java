package org.dorkmaster.scanner.agent.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.JobLoader;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ScanJobLoader implements JobLoader {
    public List<File> scan(String path) {
        return scan(new File(path));
    }

    public List<File> scan(File file) {
        List<File> result = new LinkedList<File>();
        if (file.canRead()) {
            if (file.isFile()) {
                result.add(file);
            }
            else if (file.isDirectory()) {
                result.addAll(scan(file));
            }
        }
        return result;
    }

    @Override
    public Collection<Job> load(ApplicationContextFactory applicationContextFactory) throws DuplicateJobException {
        return null;
    }

    @Override
    public Collection<Job> reload(ApplicationContextFactory applicationContextFactory) {
        return null;
    }

    @Override
    public void clear() {

    }
}
