package org.dorkmaster.scanner.command;

public class SaveCmd implements Command {
    private FileCmd file;

    public FileCmd getFile() {
        return file;
    }

    public SaveCmd setFile(FileCmd file) {
        this.file = file;
        return this;
    }

    @Override
    public String name() {
        return "save";
    }
}
