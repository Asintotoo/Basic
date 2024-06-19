package com.asintoto.basic.regions;

import com.asintoto.basic.Basic;
import com.asintoto.basic.interfaces.BasicSerializable;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class Region implements BasicSerializable {
    private String name;
    private Location primaryLocation;
    private Location secondaryLocation;

    public Region(String name, Location primaryLocation, Location secondaryLocation) {
        this.name = name;
        this.primaryLocation = primaryLocation;
        this.secondaryLocation = secondaryLocation;

        Basic.getRegionManager().addRegion(this);
    }

    public String getName() {
        return name;
    }

    public Location getSecondaryLocation() {
        return secondaryLocation;
    }

    public Location getPrimaryLocation() {
        return primaryLocation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrimaryLocation(Location primaryLocation) {
        this.primaryLocation = primaryLocation;
    }

    public void setSecondaryLocation(Location secondaryLocation) {
        this.secondaryLocation = secondaryLocation;
    }

    public boolean isWithin(Location loc) {
        final int x = (int) loc.getX();
        final int y = (int) loc.getY();
        final int z = (int) loc.getZ();

        return x >= primaryLocation.getX() && x <= secondaryLocation.getX()
                && y >= primaryLocation.getY() && y <= secondaryLocation.getY()
                && z >= primaryLocation.getZ() && z <= secondaryLocation.getZ();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("Name:", name);
        map.put("Primary Location", primaryLocation);
        map.put("Secondary Location", secondaryLocation);

        return map;
    }

    public static Region deserialize(Map<String, Object> map) {
        String regionName = (String) map.get("Name");
        Location loc1 = (Location) map.get("Primary Location");
        Location loc2 = (Location) map.get("Secondary Location");

        return new Region(regionName, loc1, loc2);
    }
}
