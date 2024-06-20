package com.asintoto.basic;

import com.asintoto.basic.holograms.HologramManager;
import com.asintoto.basic.listeners.InventoryClickListener;
import com.asintoto.basic.listeners.InventoryCloseListener;
import com.asintoto.basic.menu.MenuManager;
import com.asintoto.basic.regions.RegionManager;
import com.asintoto.basic.utils.Options;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Basic {

    private static JavaPlugin plugin;

    private static boolean isActive = false;

    private static Options options = new Options();

    private static RegionManager regionManager = null;
    private static HologramManager hologramManager = null;

    /**
     * The method to initiate Basic.
     * Used to load resources and generate needed files and directories.
     *
     * @param plugin
     * @param <T>
     */
    public static <T extends JavaPlugin> void init(T plugin) {
        Basic.plugin = plugin;
        Basic.isActive = true;

        MenuManager.init();

        File folder = new File(getPlugin().getDataFolder().getAbsolutePath());
        if(!folder.exists()) {
            folder.mkdir();
        }

        Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), getPlugin());

        if(options.isDataFolder()) {
            File dataFolder = new File(plugin.getDataFolder() + "/" + options.getDataFolderName());
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }
        }

        regionManager = new RegionManager("regions.yml");
        hologramManager = new HologramManager("holograms.yml");


        if(options.isSaveRegions()) {
            if(regionManager.fileExists()) {
                regionManager.load();
            }
        }

        if(options.isSaveHolograms()) {
            if(hologramManager.fileExists()) {
                hologramManager.load();
            }
        }


    }

    /**
     * The method to terminate Basic.
     * Used to save resources.
     */
    public static void terminate() {
        Basic.isActive = false;
        MenuManager.closeAllMenus();

        if(options.isSaveRegions()) {
            regionManager.save();
        }

        if(options.isSaveHolograms()) {
            hologramManager.save();
        }

        regionManager.terminate();
        hologramManager.terminate();
    }

    /**
     * Run this method to access Basic's options.
     * Make sure to set the options before using Basic.init().
     *
     * @return
     */
    public static Options getOptions() {
        return options;
    }

    /**
     * Return whether Basic is running.
     *
     * @return
     */
    public static boolean isIsActive() {
        return isActive;
    }

    /**
     * Get the JavaPlugin linked with Basic.
     * Useful to get the plugin instance.
     *
     * @return
     */
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Get Basic's Region Manager.
     * Used to access information about Basic-made regions.
     *
     * @return
     */
    public static RegionManager getRegionManager() {
        return regionManager;
    }

    /**
     * Get Basic's Hologram Manager.
     * Used to access information about Basic-made holograms.
     *
     * @return
     */
    public static HologramManager getHologramManager() {
        return hologramManager;
    }

    /**
     * Automatically reload plugin's config file and all the Basic related structures.
     */
    public static void reload() {
        plugin.reloadConfig();

        MenuManager.closeAllMenus();

        if(regionManager.fileExists()) {
            regionManager.load();
        }
        if(hologramManager.fileExists()) {
            hologramManager.removeAll();
            hologramManager.load();
        }
    }
}
