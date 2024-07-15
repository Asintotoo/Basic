package com.asintoto.basic;

import com.asintoto.basic.commands.BasicCommand;
import com.asintoto.basic.holograms.HologramManager;
import com.asintoto.basic.listeners.InventoryClickListener;
import com.asintoto.basic.listeners.InventoryCloseListener;
import com.asintoto.basic.menu.MenuManager;
import com.asintoto.basic.regions.RegionManager;
import com.asintoto.basic.utils.Debug;
import com.asintoto.basic.utils.Options;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

        Debug.log("Initializing Basic's components..");

        Basic.plugin = plugin;
        Basic.isActive = true;

        MenuManager.init();
        Debug.log("Menu Manager successfully initialized!");

        File folder = new File(getPlugin().getDataFolder().getAbsolutePath());
        if(!folder.exists()) {
            folder.mkdir();
        }

        Debug.log("Plugin folder created!");

        Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), getPlugin());

        Debug.log("System events registered!");

        if(options.isDataFolder()) {
            File dataFolder = new File(plugin.getDataFolder() + "/" + options.getDataFolderName());
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            Debug.log("Data Folder created!");
        }

        regionManager = new RegionManager("regions.yml");
        hologramManager = new HologramManager("holograms.yml");

        Debug.log("Data Managers instantiated!");


        if(options.isSaveRegions()) {
            if(regionManager.fileExists()) {
                regionManager.load();
            }

            Debug.log("Regions Loaded!");
        }

        if(options.isSaveHolograms()) {
            if(hologramManager.fileExists()) {
                hologramManager.load();
            }

            Debug.log("Holograms Loaded!");
        }

        Debug.log("Finished initiation process!");
    }

    /**
     * The method to terminate Basic.
     * Used to save resources.
     */
    public static void terminate() {

        Debug.log("Terminating Basic's components..");

        Basic.isActive = false;
        MenuManager.closeAllMenus();

        if(options.isSaveRegions()) {
            regionManager.save();

            Debug.log("Regions saved!");
        }

        if(options.isSaveHolograms()) {
            hologramManager.save();

            Debug.log("Holograms saved!");
        }

        regionManager.terminate();
        hologramManager.terminate();

        Debug.log("Process successfully terminated!");
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
     * Run this method to access Basic's options.
     * Make sure to set the options before using Basic.init().
     *
     * @return
     */
    public static Options options() {
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

    public static <T extends BasicCommand> void registerCommand(String cmd, T commandClass) {
        if(options().isRegisterCommandInFile()) {
            registerDynamicCommand(cmd, commandClass.getDescription(), commandClass.getPermission(),
                    commandClass.getAliases(), commandClass);
        }

        plugin.getCommand(cmd).setExecutor(commandClass);
        plugin.getCommand(cmd).setTabCompleter(commandClass);
    }

    public static <T extends CommandExecutor> void registerCommand(String cmd, T commandClass) {
        if(options().isRegisterCommandInFile()) {
            registerDynamicCommand(cmd, "", "",
                    Collections.emptyList(), commandClass);
        }

        plugin.getCommand(cmd).setExecutor(commandClass);
    }

    public static <T extends CommandExecutor> void registerCommand(String cmd, T commandClass, String description, String permission, List<String> aliases) {
        if(options().isRegisterCommandInFile()) {
            registerDynamicCommand(cmd, description, permission,
                    aliases, commandClass);
        }

        plugin.getCommand(cmd).setExecutor(commandClass);
    }

    public static <T extends TabCompleter> void registerTabCompleter(String cmd, T commandClass) {
        plugin.getCommand(cmd).setTabCompleter(commandClass);
    }

    public static <T extends BasicCommand> void registerCommand(T commandClass) {
        String label = commandClass.getLabel();
        registerCommand(label, commandClass);
    }

    public static <T extends Listener> void registerListener(T listenerClass) {
        registerListener(listenerClass, plugin);
    }

    public static <T extends Listener> void registerListener(T listenerClass, JavaPlugin pl) {
        plugin.getServer().getPluginManager().registerEvents(listenerClass, pl);
    }

    public static void registerDynamicCommand(String name, String description, String permission, List<String> aliases, CommandExecutor executor) {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            Command command = new BukkitCommand(name) {
                @Override
                public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                    if (executor != null) {
                        return executor.onCommand(sender, this, commandLabel, args);
                    }
                    return false;
                }
            };
            command.setDescription(description);
            command.setPermission(permission);
            command.setAliases(aliases);
            command.setLabel(name);

            commandMap.register(command.getLabel(), command);

            if(!command.isRegistered()) {
                Debug.log("Command /" + command.getLabel() + " wasn't registered properly!");
            }

            plugin.getCommand(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
