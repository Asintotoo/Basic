package com.asintoto.basic.holograms;

import com.asintoto.basic.Basic;
import com.asintoto.basic.utils.BasicKeys;
import com.asintoto.basic.interfaces.BasicSerializable;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class Hologram implements BasicSerializable {
    private String[] lines;
    private Location loc;

    public Hologram(Location loc, String... lines) {
        this.lines = lines;
        this.loc = loc;

        Basic.getHologramManager().addHologram(this);
    }

    public String[] getLines() {
        return lines;
    }

    public void setLines(String... lines) {
        this.lines = lines;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public void spawn() {

        Location currentLoc = loc.clone();
        int id = Basic.getHologramManager().getHologramId(this);

        for(String s : lines) {
            ArmorStand stand = loc.getWorld().spawn(currentLoc, ArmorStand.class);

            stand.setVisible(false);
            stand.setGravity(false);
            stand.setInvulnerable(true);

            stand.setCustomName(s);
            stand.setCustomNameVisible(true);

            stand.getPersistentDataContainer().set(BasicKeys.BASIC_HOLOGRAM, PersistentDataType.INTEGER, id);

            currentLoc.subtract(0, 0.25, 0);
        }
    }

    public void remove() {
        int id = Basic.getHologramManager().getHologramId(this);

        for(Entity e : loc.getWorld().getNearbyEntities(loc, 1, lines.length, 1)) {
            if(e.getPersistentDataContainer().has(BasicKeys.BASIC_HOLOGRAM, PersistentDataType.INTEGER)
                    && (e instanceof ArmorStand)) {
                if(e.getPersistentDataContainer().get(BasicKeys.BASIC_HOLOGRAM, PersistentDataType.INTEGER) == id) {
                    e.remove();
                }
            }
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("Location", loc);
        map.put("Lines", lines);

        return map;
    }


    public static Hologram deserialize(Map<String, Object> map) {
        Location loc1 = (Location) map.get("Location");
        String[] lines1 = (String[]) map.get("Lines");

        return new Hologram(loc1, lines1);
    }
}
