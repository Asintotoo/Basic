package com.asintoto.basic.regions;

import com.asintoto.basic.interfaces.BasicSavable;
import com.asintoto.basic.utils.DataManager;
import com.asintoto.basic.utils.Debug;
import com.asintoto.basic.utils.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

public class RegionManager extends DataManager implements BasicSavable {
    private ArrayList<Region> regionList;

    public RegionManager(String fileName) {
        super(fileName);
        regionList = new ArrayList<>();
    }

    @Override
    public void terminate() {
        regionList.clear();
    }

    public void addRegion(Region r) {
        regionList.add(r);
    }

    public void removeRegion(Region r) {
        if(regionList.contains(r)) regionList.remove(r);
    }

    public ArrayList<Region> getRegionList() {
        return regionList;
    }


    public ArrayList<Region> getRegionsAtLocation(Location loc) {
        ArrayList<Region> list = new ArrayList<>();

        for(Region r : regionList) {
            if(r.isWithin(loc)) {
                list.add(r);
            }
        }

        if(list.isEmpty()) return null;
        return list;
    }

    public ArrayList<Region> getRegionsAtPlayer(Player p) {
        Location loc = p.getLocation();
        return getRegionsAtLocation(loc);
    }

    @Override
    public void save() {

        regenerateFile();

        setConfig(YamlManager.createYamlConfiguration(getFile()));

        if(regionList.isEmpty()) {
            return;
        }

        Debug.log("Starting saving " + regionList.size() + " regions...");

        for(Region r : regionList) {
            String regName = r.getName();
            getConfig().set("Regions." + regName + ".first-location.x", r.getPrimaryLocation().getX());
            getConfig().set("Regions." + regName + ".first-location.y", r.getPrimaryLocation().getY());
            getConfig().set("Regions." + regName + ".first-location.z", r.getPrimaryLocation().getZ());
            getConfig().set("Regions." + regName + ".first-location.pitch", r.getPrimaryLocation().getPitch());
            getConfig().set("Regions." + regName + ".first-location.yaw", r.getPrimaryLocation().getYaw());
            getConfig().set("Regions." + regName + ".first-location.world", r.getPrimaryLocation().getWorld().getName());

            getConfig().set("Regions." + regName + ".second-location.x", r.getSecondaryLocation().getX());
            getConfig().set("Regions." + regName + ".second-location.y", r.getSecondaryLocation().getY());
            getConfig().set("Regions." + regName + ".second-location.z", r.getSecondaryLocation().getZ());
            getConfig().set("Regions." + regName + ".second-location.pitch", r.getSecondaryLocation().getPitch());
            getConfig().set("Regions." + regName + ".second-location.yaw", r.getSecondaryLocation().getYaw());
            getConfig().set("Regions." + regName + ".second-location.world", r.getSecondaryLocation().getWorld().getName());

            Debug.log("Region "+ regName + " saved!");
        }

        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {

        if(getConfig() == null) {
            if(fileExists()) {
                setConfig(YamlConfiguration.loadConfiguration(getFile()));
            } else {
                return;
            }
        }

        if(!getConfig().isSet("Regions")) {
            return;
        }

        Debug.log("Starting loading regions...");

        regionList.clear();

        Debug.log("Is the region list empty? " + regionList.isEmpty());

        for(String name : getConfig().getConfigurationSection("Regions").getKeys(false)) {
            Double x = getConfig().getDouble("Regions." + name + ".first-location.x");
            Double y = getConfig().getDouble("Regions." + name + ".first-location.y");
            Double z = getConfig().getDouble("Regions." + name + ".first-location.z");
            Double pitch = getConfig().getDouble("Regions." + name + ".first-location.pitch");
            Double yaw = getConfig().getDouble("Regions." + name + ".first-location.yaw");
            World w = Bukkit.getWorld(getConfig().getString("Regions." + name + ".first-location.world"));

            if(w == null) {
                continue;
            }

            Location loc1 = new Location(w, x, y, z, yaw.floatValue(), pitch.floatValue());

            x = getConfig().getDouble("Regions." + name + ".second-location.x");
            y = getConfig().getDouble("Regions." + name + ".second-location.y");
            z = getConfig().getDouble("Regions." + name + ".second-location.z");
            pitch = getConfig().getDouble("Regions." + name + ".second-location.pitch");
            yaw = getConfig().getDouble("Regions." + name + ".second-location.yaw");
            w = Bukkit.getWorld(getConfig().getString("Regions." + name + ".second-location.world"));

            if(w == null) {
                continue;
            }

            Location loc2 = new Location(w, x, y, z, yaw.floatValue(), pitch.floatValue());

            new Region(name, loc1, loc2);

            Debug.log("Region " + name + " loaded!");
        }
    }
}
