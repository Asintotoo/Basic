package com.asintoto.basic.regions;

import com.asintoto.basic.Basic;
import com.asintoto.basic.interfaces.BasicSavable;
import com.asintoto.basic.utils.DataManager;
import com.asintoto.basic.utils.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegionManager extends DataManager implements BasicSavable {
    private ArrayList<Region> regionList;

    public RegionManager(String fileName) {
        super(fileName);
        regionList = new ArrayList<>();
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
    /*public void save() {
        List<Map<String, Object>> serializedRegion = new ArrayList<>();

        for(Region r : regionList) {
            serializedRegion.add(r.serialize());
        }

        getConfig().set("Regions", serializedRegion);
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void save() {

        //setConfig(YamlManager.createNewDataFile(getFileName()));

        if(regionList.isEmpty()) {
            return;
        }

        regenerateFile();

        setConfig(YamlManager.createYamlConfiguration(getFile()));

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
        }

        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    /*public void load() {

        try {
            if(!getFile().exists()) {
                getFile().createNewFile();
            }

            getConfig().load(getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        regionList.clear();

        if(getConfig().isSet("Regions")) {
            for(Map<?, ?> rawRegion : getConfig().getMapList("Regions")) {
                addRegion(Region.deserialize((Map<String, Object>) rawRegion));
            }
        }
    }*/
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

        for(String name : getConfig().getConfigurationSection("Regions").getKeys(false)) {
            Double x = getConfig().getDouble("Regions." + name + ".first-location.x");
            Double y = getConfig().getDouble("Regions." + name + ".first-location.y");
            Double z = getConfig().getDouble("Regions." + name + ".first-location.z");
            Double pitch = getConfig().getDouble("Regions." + name + ".first-location.pitch");
            Double yaw = getConfig().getDouble("Regions." + name + ".first-location.yaw");
            World w = Bukkit.getWorld(getConfig().getString("Regions." + name + ".first-location.world"));

            Location loc1 = new Location(w, x, y, z, yaw.floatValue(), pitch.floatValue());

            x = getConfig().getDouble("Regions." + name + ".second-location.x");
            y = getConfig().getDouble("Regions." + name + ".second-location.y");
            z = getConfig().getDouble("Regions." + name + ".second-location.z");
            pitch = getConfig().getDouble("Regions." + name + ".second-location.pitch");
            yaw = getConfig().getDouble("Regions." + name + ".second-location.yaw");
            w = Bukkit.getWorld(getConfig().getString("Regions." + name + ".second-location.world"));

            Location loc2 = new Location(w, x, y, z, yaw.floatValue(), pitch.floatValue());

            addRegion(new Region(name, loc2, loc2));
        }
    }
}
