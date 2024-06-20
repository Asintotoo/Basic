package com.asintoto.basic.regions;

import com.asintoto.basic.interfaces.BasicSavable;
import com.asintoto.basic.utils.DataManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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
    public void save() {
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
    }

    @Override
    public void load() {

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
    }
}
