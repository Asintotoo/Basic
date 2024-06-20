package com.asintoto.basic.utils;

import com.asintoto.basic.Basic;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DataManager {
    private String fileName;
    private YamlConfiguration config;
    private File file;

    public DataManager(String fileName) {
        this.fileName = fileName;

        this.file = new File(Basic.getPlugin().getDataFolder() + "/" + Basic.getOptions().getDataFolderName() + "/" + fileName);;
        this.config = null;
    }

    public String getFileName() {
        return fileName;
    }

    public YamlConfiguration getConfig() {
        if(config == null) {
            config = YamlManager.createYamlConfiguration(file);
        }

        return config;
    }

    public File getFile() {
        return file;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean fileExists() {
        return file.exists();
    }
}
