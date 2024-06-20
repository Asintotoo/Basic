package com.asintoto.basic.utils;

import com.asintoto.basic.Basic;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlManager {
    public static YamlConfiguration createYamlConfiguration(String filename) {
        File configFile = new File(Basic.getPlugin().getDataFolder(), filename);
        if(!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static YamlConfiguration createYamlConfiguration(File file) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static YamlConfiguration reloadYamlConfiguration(String filename) {
        File configFile = new File(Basic.getPlugin().getDataFolder(), filename);
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static YamlConfiguration createNewDataFile(String filename) {
        File file = new File(Basic.getPlugin().getDataFolder()
                + "/" + Basic.getOptions().getDataFolderName() + "/" + filename);

        try {
            if(file.exists()) {
                file.delete();
            }

            file.createNewFile();

            return YamlConfiguration.loadConfiguration(file);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
