package org.dorkmaster.scanner.processor;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.dorkmaster.scanner.CmdProcessor;
import org.dorkmaster.scanner.command.Command;
import org.dorkmaster.scanner.command.HashCmd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HashProcessor extends AbstractProcessor {
    public HashProcessor(CmdProcessor cmdProcessor) {
        super(cmdProcessor, HashCmd.class);
    }

    @Override
    public void doCmd(Command cmd) {
        HashCmd dh = (HashCmd) cmd;
        FileInputStream in = null;

        try {
            byte[] buf = new byte[dh.getHash().getSize()];
            in = new FileInputStream(dh.getFile());
            in.read(buf);
            dh.getHash().setHash(DigestUtils.md5Hex(buf));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

}
