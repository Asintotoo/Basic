package com.asintoto.basic.holograms;

import com.asintoto.basic.interfaces.BasicSavable;
import com.asintoto.basic.regions.Region;
import com.asintoto.basic.utils.DataManager;
import com.asintoto.basic.utils.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramManager extends DataManager implements BasicSavable {
    private Map<Hologram, Integer> hologramList;
    private int currentId;

    public HologramManager(String fileName) {
        super(fileName);
        hologramList = new HashMap<>();
        currentId = 0;
    }

    public Map<Hologram, Integer> getHologramList() {
        return hologramList;
    }

    public void addHologram(Hologram holo) {
        hologramList.put(holo, currentId);
        currentId++;
    }

    public void addAndSpawn(Hologram holo) {
        addHologram(holo);
        holo.spawn();
    }

    public void removeHologram(Hologram holo) {
        if(hologramList.containsKey(holo)) {
            holo.remove();
            hologramList.remove(holo);
        }
    }

    public int getHologramId(Hologram holo) {
        if(!hologramList.containsKey(holo)) {
            addHologram(holo);
            return getHologramId(holo);
        }
        return hologramList.get(holo);
    }

    public void removeHologram(int id) {
        Hologram holo = null;
        for(Map.Entry e : hologramList.entrySet()) {
            if((int) e.getValue() == id) {
                holo = (Hologram) e.getKey();
                break;
            }
        }

        if(holo != null) {
            removeHologram(holo);
        }
    }

    public void removeAll() {
        for(Hologram holo : hologramList.keySet()) {
            removeHologram(holo);
        }

        currentId = 0;
    }

    @Override
    /*public void save() {
        List<Map<String, Object>> serializedHologram = new ArrayList<>();

        for(Hologram h : hologramList.keySet()) {
            serializedHologram.add(h.serialize());
        }

        getConfig().set("Holograms", serializedHologram);
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void save() {

        if(hologramList.isEmpty()) {
            return;
        }

        regenerateFile();

        setConfig(YamlManager.createYamlConfiguration(getFile()));

        for(Hologram h : hologramList.keySet()) {
            int id = getHologramId(h);
            getConfig().set("Holograms." + id + ".location.x", h.getLocation().getX());
            getConfig().set("Holograms." + id + ".location.y", h.getLocation().getY());
            getConfig().set("Holograms." + id + ".location.z", h.getLocation().getZ());
            getConfig().set("Holograms." + id + ".location.pitch", h.getLocation().getPitch());
            getConfig().set("Holograms." + id + ".location.yaw", h.getLocation().getYaw());
            getConfig().set("Holograms." + id + ".location.world", h.getLocation().getWorld().getName());

            getConfig().set("Holograms." + id + ".lines", h.getLines());
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

        hologramList.clear();

        if(getConfig().isSet("Holograms")) {
            for(Map<?, ?> rawHologram : getConfig().getMapList("Holograms")) {
                addAndSpawn(Hologram.deserialize((Map<String, Object>) rawHologram));
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

        if(!getConfig().isSet("Holograms")) {
            return;
        }

        hologramList.clear();

        for(String id : getConfig().getConfigurationSection("Holograms").getKeys(false)) {
            Double x = getConfig().getDouble("Holograms." + id + ".location.x");
            Double y = getConfig().getDouble("Holograms." + id + ".location.y");
            Double z = getConfig().getDouble("Holograms." + id + ".location.z");
            Double pitch = getConfig().getDouble("Holograms." + id + ".location.pitch");
            Double yaw = getConfig().getDouble("Holograms." + id + ".location.yaw");
            World w = Bukkit.getWorld(getConfig().getString("Holograms." + id + ".location.world"));
            List<String> lines = getConfig().getStringList("Holograms." + id + ".lines");

            if(w == null) {
                continue;
            }

            Location loc = new Location(w, x, y, z, yaw.floatValue(), pitch.floatValue());

            addHologram(new Hologram(loc, lines.toArray(new String[0])));
        }
    }




}
