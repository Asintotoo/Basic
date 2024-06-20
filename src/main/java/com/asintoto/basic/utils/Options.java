package com.asintoto.basic.utils;

import java.util.ArrayList;

public class Options {
    private boolean saveRegions;
    private boolean saveHolograms;
    private boolean debugMode;
    private String dataFolderName;

    public Options() {
        this.debugMode = false;
        this.saveHolograms = false;
        this.saveRegions = false;
        this.dataFolderName = "data";
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

    public void setSaveRegions(boolean saveRegions) {
        this.saveRegions = saveRegions;
    }

    public void saveRegions() {
        this.saveRegions = true;
    }

    public void saveHolograms() {
        this.saveHolograms = true;
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

}
