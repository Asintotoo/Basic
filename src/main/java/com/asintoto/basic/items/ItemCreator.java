package com.asintoto.basic.items;

import com.asintoto.basic.Basic;
import com.asintoto.basic.enums.BasicChar;
import com.asintoto.basic.utils.Common;
import com.asintoto.colorlib.ColorLib;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemCreator {
    private ItemStack item;
    private static final String lorePrefix = "&7";
    private ItemMeta meta;
    private int amount;
    private int damage;

    private String name;
    private List<String> lore;
    private Map<Enchantment, Integer> enchants;
    private List<ItemFlag> flags;

    private boolean unbreakable;
    private boolean glow;

    private String skullOwner;
    private int modelData;

    public ItemCreator(Material item, int amount, int damage) {
        this.item = new ItemStack(item);
        this.amount = amount;
        this.damage = damage;

        enchants = new HashMap<>();
        lore = new ArrayList<>();
        flags = new ArrayList<>();

        if(this.item.getType() != Material.AIR
                && this.item.getType() != Material.CAVE_AIR
                && this.item.getType() != Material.VOID_AIR) {
            this.meta = this.item.getItemMeta();
        }
    }

    public ItemCreator(Material item) {
        this(item, 1, 0);
    }


    public ItemCreator setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public ItemCreator setMeta(ItemMeta meta) {
        this.meta = meta;

        return this;
    }

    public ItemCreator setAmount(int amount) {
        this.amount = amount;

        return this;
    }

    public ItemCreator amount(int amount) {
        return setAmount(amount);
    }

    public ItemCreator setDamage(int damage) {
        this.damage = damage;

        return this;
    }

    public ItemCreator damage(int damage) {
        return setDamage(damage);
    }

    public ItemCreator setName(String name) {
        this.name = ColorLib.setColors(name);

        return this;
    }

    public ItemCreator name(String name) {
        return setName(name);
    }

    public ItemCreator setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;

        return this;
    }

    public ItemCreator setGlowing(boolean glow) {
        this.glow = glow;

        return this;
    }

    public ItemCreator glow() {
        this.glow = glow;
        return this;
    }

    public ItemCreator setGlowing() {
        this.glow = true;

        return this;
    }

    public ItemCreator setSkullOwner(String skullOwner) {
        this.skullOwner = skullOwner;

        return this;
    }

    public ItemCreator setCustomModelData(int modelData) {
        this.modelData = modelData;

        return this;
    }

    public ItemCreator addEnchant(Enchantment ench, int level) {
        this.enchants.put(ench, level);

        return this;
    }

    public ItemCreator setLore(String... lore) {
        for(String s : lore) {
            String str = ColorLib.setColors(lorePrefix + s);
            this.lore.add(str);
        }

        return this;
    }

    public ItemCreator lore(String... lore) {
        return setLore(lore);
    }

    public ItemStack make() {
        ItemStack finalItem = item.clone();

        if(finalItem.getType() == Material.AIR
                || finalItem.getType() == Material.VOID_AIR
                || finalItem.getType() == Material.CAVE_AIR) {
            return finalItem;
        }

        ItemMeta finalMeta = meta.clone();

        for(Enchantment e : enchants.keySet()) {
            int level = enchants.get(e);

            finalMeta.addEnchant(e, level, true);
        }

        finalMeta.setLore(lore);

        for(ItemFlag flag : flags) {
            finalMeta.addItemFlags(flag);
        }

        finalMeta.setUnbreakable(unbreakable);
        finalMeta.setDisplayName(name);
        finalItem.setAmount(amount);
        finalItem.setDurability((short) (finalItem.getDurability() - (short) damage));
        finalMeta.setCustomModelData(modelData);

        if(finalItem.getType() == Material.PLAYER_HEAD) {
            finalItem.setItemMeta(finalMeta);
            SkullMeta skullMeta = (SkullMeta) finalItem.getItemMeta();
            skullMeta.setOwner(skullOwner);
            finalItem.setItemMeta(skullMeta);
            finalMeta = skullMeta.clone();
        }

        if(enchants.isEmpty() && glow) {
            finalMeta.addEnchant(Enchantment.LURE, 1, true);
            finalMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        finalItem.setItemMeta(finalMeta);

        return finalItem;
    }

    public ItemCreator unbreakable() {
        this.unbreakable = true;
        return this;
    }

    public void give(Player p) {
        ItemStack giveItem = make();
        if(p.getInventory().firstEmpty() != -1) {
            p.getInventory().addItem(giveItem);
        }
    }

    public static ItemCreator of(Material m) {
        return new ItemCreator(m);
    }

    public static ItemCreator ofEgg(EntityType type) {
        return of(makeMonsterEgg(type));
    }

    private static Material makeMonsterEgg(EntityType type) {
        Material created = Material.SHEEP_SPAWN_EGG;

        try {
            String name = type.toString().toUpperCase() + "_SPAWN_EGG";

            if (type.name().equals("MUSHROOM_COW") || type.name().equals("MOOSHROOM"))
                name = "MOOSHROOM_SPAWN_EGG";

            created = Material.valueOf(name);

            if(created == null) {
                return Material.SHEEP_SPAWN_EGG;
            }

        } catch (Throwable e) {
            Common.warning("Cannot parse the given enum type. Item type set to default value");
        }

        return created;
    }
}
