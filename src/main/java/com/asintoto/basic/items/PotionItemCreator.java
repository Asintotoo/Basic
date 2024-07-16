package com.asintoto.basic.items;

import com.asintoto.basic.structures.Tuple;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class PotionItemCreator {
    private boolean splash;
    private boolean lingering;

    private List<PotionEffect> potionEffects = new ArrayList<>();

    private ItemCreator item;

    public PotionItemCreator(ItemCreator item) {
        this.lingering = false;
        this.splash = false;

        this.item = item;
    }

    public PotionItemCreator setSplash(boolean splash) {
        this.splash = splash;
        return this;
    }

    public PotionItemCreator splash() {
        this.splash = true;
        return this;
    }

    public PotionItemCreator setLingering(boolean lingering) {
        this.lingering = lingering;
        return this;
    }

    public PotionItemCreator lingering() {
        this.lingering = true;
        return this;
    }

    public PotionItemCreator addPotionEffect(PotionEffect effect) {
        potionEffects.add(effect);
        return this;
    }

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public boolean isLingering() {
        return lingering;
    }

    public boolean isSplash() {
        return splash;
    }

    public ItemCreator getItem() {
        return item;
    }

    public ItemStack make() {

        ItemStack finalItem = item.make();

        if(splash) {
            finalItem.setType(Material.SPLASH_POTION);
        } else if(lingering) {
            finalItem.setType(Material.LINGERING_POTION);
        } else {
            finalItem.setType(Material.POTION);
        }

        PotionMeta meta = (PotionMeta) finalItem.getItemMeta();

        if(meta == null) return finalItem;

        if(!potionEffects.isEmpty()) {
            for(PotionEffect e : potionEffects) {

                meta.addCustomEffect(e, true);

            }
        }

        finalItem.setItemMeta(meta);

        return finalItem;
    }

    public void give(Player p) {
        ItemStack finalItem = item.make();
        if(p.getInventory().firstEmpty() > -1) {
            p.getInventory().addItem(finalItem);
        }
    }

    public static PotionItemCreator of(ItemCreator item) {
        return new PotionItemCreator(item);
    }
}
