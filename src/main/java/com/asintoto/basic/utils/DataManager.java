package com.asintoto.basic.utils;

import com.asintoto.basic.Basic;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class DataManager {
    private String fileName;
    private YamlConfiguration config;
    private File file;

    public DataManager(String fileName) {
        this.fileName = fileName;

        this.file = new File(Basic.getPlugin().getDataFolder() + "/" + Basic.getOptions().getDataFolderName() + "/" + fileName);
        this.config = null;
    }

    public String getFileName() {
        return fileName;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

    public File getFile() {
        return file;
    }

    public boolean fileExists() {
        return getFile().exists();
    }

    public void regenerateFile() {
        if(file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void terminate();
}
