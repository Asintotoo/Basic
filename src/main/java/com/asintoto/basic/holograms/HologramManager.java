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
import java.util.*;

public class HologramManager extends DataManager implements BasicSavable {
    private Map<Hologram, Integer> hologramList;
    private int currentId;
    private Set<Integer> usedIds = new HashSet<>();

    public HologramManager(String fileName) {
        super(fileName);
        hologramList = new HashMap<>();
        currentId = 0;
        usedIds = new HashSet<>();
    }

    public Map<Hologram, Integer> getHologramList() {
        return hologramList;
    }

    public void addHologram(Hologram holo) {
        if(isUsedId(currentId)) {
            currentId++;
            addHologram(holo);
            return;
        }

        hologramList.put(holo, currentId);
        usedIds.add(currentId);
        currentId++;
    }

    private void putHologram(Hologram holo, int id) {

        if(isUsedId(id)) {
            return;
        }

        hologramList.put(holo, id);
        usedIds.add(id);
    }

    private boolean isUsedId(int id) {
        return usedIds.contains(id);
    }

    public void addAndSpawn(Hologram holo) {
        addHologram(holo);
        holo.spawn();
    }

    public void removeHologram(Hologram holo) {
        if(hologramList.containsKey(holo)) {
            holo.remove();
            usedIds.remove(getHologramId(holo));
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

    private int getFirstFreeId() {
        int i = 0;
        while(true) {
            for(Hologram h : hologramList.keySet()) {
                if(getHologramId(h) != i) {

                }
            }
        }
    }

    @Override
    public void save() {

        regenerateFile();

        setConfig(YamlManager.createYamlConfiguration(getFile()));

        if(hologramList.isEmpty()) {
            return;
        }

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

            putHologram(new Hologram(loc, lines.toArray(new String[0])), Integer.parseInt(id));
        }

        currentId = Collections.max(usedIds) + 1;
    }




}
