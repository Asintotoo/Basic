package com.asintoto.basic.holograms;

import com.asintoto.basic.interfaces.BasicSavable;
import com.asintoto.basic.utils.DataManager;

import java.io.IOException;
import java.util.ArrayList;
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
    public void save() {
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

        hologramList.clear();

        if(getConfig().isSet("Holograms")) {
            for(Map<?, ?> rawHologram : getConfig().getMapList("Holograms")) {
                addAndSpawn(Hologram.deserialize((Map<String, Object>) rawHologram));
            }
        }
    }




}
