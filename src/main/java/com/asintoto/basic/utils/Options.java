package com.asintoto.basic.utils;

import java.util.ArrayList;

public class Options {
    private boolean saveRegions;
    private boolean saveHolograms;
    private boolean debugMode;
    private boolean registerCommandInFile;
    private String dataFolderName;

    public Options() {
        this.debugMode = false;
        this.saveHolograms = false;
        this.saveRegions = false;
        this.registerCommandInFile = true;

        this.dataFolderName = "data";
    }

    public boolean isRegisterCommandInFile() {
        return registerCommandInFile;
    }

    public void setRegisterCommandInFile(boolean registerCommandInFile) {
        this.registerCommandInFile = registerCommandInFile;
    }

    public void registerCommandInFile(boolean registerCommandInFile) {
        this.registerCommandInFile = registerCommandInFile;
    }

    public Options registerCommandInFile() {
        this.registerCommandInFile = true;
        return this;
    }

    public boolean isSaveRegions() {
        return saveRegions;
    }

    public boolean isSaveHolograms() {
        return saveHolograms;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public Options debug() {
        this.debugMode = true;
        return this;
    }

    public void setSaveRegions(boolean saveRegions) {
        this.saveRegions = saveRegions;
    }

    public Options saveRegions() {
        this.saveRegions = true;
        return this;
    }

    public Options saveHolograms() {
        this.saveHolograms = true;
        return this;
    }

    public void setSaveHolograms(boolean saveHolograms) {
        this.saveHolograms = saveHolograms;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDataFolder() {
        return saveHolograms || saveRegions;
    }

    public String getDataFolderName() {
        return dataFolderName;
    }

    public void setDataFolderName(String dataFolderName) {
        this.dataFolderName = dataFolderName;
    }

    public Options dataFolderName(String dataFolderName) {
        setDataFolderName(dataFolderName);
        return this;
    }

}
