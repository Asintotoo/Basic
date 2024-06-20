package com.asintoto.basic.holograms;

import com.asintoto.basic.interfaces.BasicSavable;
import com.asintoto.basic.utils.BasicKeys;
import com.asintoto.basic.utils.DataManager;
import com.asintoto.basic.utils.Debug;
import com.asintoto.basic.utils.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

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

    @Override
    public void terminate() {
        hologramList.clear();
        currentId = 0;
        usedIds.clear();
    }

    public Map<Hologram, Integer> getHologramList() {
        return hologramList;
    }

    public void addHologram(Hologram holo) {
        if (isUsedId(currentId)) {
            currentId++;
            addHologram(holo);
            return;
        }

        hologramList.put(holo, currentId);
        usedIds.add(currentId);
        currentId++;
    }

    private void putHologram(Hologram holo, int id) {

        if (isUsedId(id)) {
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
        if (hologramList.containsKey(holo)) {
            holo.remove();
            usedIds.remove(getHologramId(holo));
            hologramList.remove(holo);
        }
    }

    public void removeHologram(Hologram holo, int id) {
        holo.remove(id);
        usedIds.remove(id);
        hologramList.remove(holo);

    }

    public int getHologramId(Hologram holo) {
        if (!hologramList.containsKey(holo)) {
            addHologram(holo);
            return getHologramId(holo);
        }
        return hologramList.get(holo);
    }

    public void removeHologram(int id) {
        Hologram holo = null;
        for (Map.Entry e : hologramList.entrySet()) {
            if ((int) e.getValue() == id) {
                holo = (Hologram) e.getKey();
                break;
            }
        }

        if (holo != null) {
            removeHologram(holo);
        }
    }

    public void removeAll() {

        Map<Hologram,Integer> copy = hologramList;

        for (Hologram holo : copy.keySet()) {
            removeHologram(holo, getHologramId(holo));
        }

        currentId = 0;
    }

    private Hologram getHologramAtRadius(Location loc, int radius) {
        for (Entity e : loc.getWorld().getNearbyEntities(loc, radius, radius, radius)) {
            if (e.getPersistentDataContainer().has(BasicKeys.BASIC_HOLOGRAM, PersistentDataType.INTEGER)
                    && (e instanceof ArmorStand)) {
                int id = e.getPersistentDataContainer().get(BasicKeys.BASIC_HOLOGRAM, PersistentDataType.INTEGER);
                for (Hologram h : hologramList.keySet()) {
                    if (getHologramId(h) == id) {
                        return h;
                    }
                }
            }
        }

        return null;
    }

    public Hologram getNearestHologram(Location loc, int radius) {
        for (int i = 1; i <= radius; i++) {
            Hologram h = getHologramAtRadius(loc, i);
            if (h != null) return h;
        }

        return null;
    }

    public Hologram getNearestHologram(Player p, int radius) {
        return getNearestHologram(p.getLocation(), radius);
    }

    @Override
    public void save() {

        regenerateFile();

        setConfig(YamlManager.createYamlConfiguration(getFile()));

        if (hologramList.isEmpty()) {
            return;
        }

        Debug.log("Starting saving " + hologramList.size() + " holograms...");

        for (Hologram h : hologramList.keySet()) {
            int id = getHologramId(h);
            getConfig().set("Holograms." + id + ".location.x", h.getLocation().getX());
            getConfig().set("Holograms." + id + ".location.y", h.getLocation().getY());
            getConfig().set("Holograms." + id + ".location.z", h.getLocation().getZ());
            getConfig().set("Holograms." + id + ".location.pitch", h.getLocation().getPitch());
            getConfig().set("Holograms." + id + ".location.yaw", h.getLocation().getYaw());
            getConfig().set("Holograms." + id + ".location.world", h.getLocation().getWorld().getName());

            getConfig().set("Holograms." + id + ".lines", h.getLines());

            Debug.log("Hologram " + id + " saved!");
        }

        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {

        if (getConfig() == null) {
            if (fileExists()) {
                setConfig(YamlConfiguration.loadConfiguration(getFile()));
            } else {
                return;
            }
        }

        if (!getConfig().isSet("Holograms")) {
            return;
        }

        Debug.log("Starting loading holograms...");

        hologramList.clear();

        Debug.log("Is the hologram list empty? " + hologramList.isEmpty());

        for (String id : getConfig().getConfigurationSection("Holograms").getKeys(false)) {
            Double x = getConfig().getDouble("Holograms." + id + ".location.x");
            Double y = getConfig().getDouble("Holograms." + id + ".location.y");
            Double z = getConfig().getDouble("Holograms." + id + ".location.z");
            Double pitch = getConfig().getDouble("Holograms." + id + ".location.pitch");
            Double yaw = getConfig().getDouble("Holograms." + id + ".location.yaw");
            World w = Bukkit.getWorld(getConfig().getString("Holograms." + id + ".location.world"));
            List<String> lines = getConfig().getStringList("Holograms." + id + ".lines");

            if (w == null) {
                continue;
            }

            Location loc = new Location(w, x, y, z, yaw.floatValue(), pitch.floatValue());

            putHologram(new Hologram(true, loc, lines.toArray(new String[0])), Integer.parseInt(id));

            Debug.log("Hologram " + id + " loaded!");
        }

        currentId = Collections.max(usedIds) + 1;
    }


}
