package com.asintoto.basic.commands;

import com.asintoto.basic.Basic;
import com.asintoto.colorlib.ColorLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class BasicCommand implements CommandExecutor, TabCompleter {

    protected final List<String> NO_COMPLETE = Collections.emptyList();
    protected final List<String> PLAYER_LIST = null;
    protected String label;

    protected CommandSender sender;
    protected String[] args;

    private String usage;
    private String permission;

    public BasicCommand(String label) {
        this.label = label;
        this.usage = ColorLib.setColors("&cUsage: /" + label + " <params...>");
        this.permission = Basic.getPlugin().getName().toLowerCase() + ".command." + label;
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        this.sender = sender;
        this.args = args;

        onCommand();
        return true;
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        this.sender = sender;
        this.args = args;

        return onTabComplete();
    }

    public void onCommand() {
    }

    public List<String> onTabComplete() {
        return NO_COMPLETE;
    }

    protected void printUsage() {
        sender.sendMessage(usage);
    }

    protected String getUsage() {
        return usage;
    }

    protected void setUsage(String usage) {
        this.usage = ColorLib.setColors(usage);
    }

    protected boolean isPlayer() {
        return sender instanceof Player;
    }

    protected Player getPlayer() {
        if(isPlayer()) {
            return (Player) sender;
        }
        return null;
    }

    protected void sendMessage(Player p, String msg) {
        p.sendMessage(ColorLib.setColors(msg));
    }

    protected void sendMessage(CommandSender p, String msg) {
        p.sendMessage(ColorLib.setColors(msg));
    }

    protected void sendMessage(String msg) {
        sendMessage(sender, msg);
    }

    public String getLabel() {
        return label;
    }

    protected boolean hasPermission(CommandSender p) {
        return p.hasPermission(permission);
    }

    protected boolean hasPermission(Player p) {
        return p.hasPermission(permission);
    }

    protected boolean hasPermission() {
        return sender.hasPermission(permission);
    }

    protected String getPermission() {
        return this.permission;
    }

    protected void setPermission(String permission) {
        this.permission = permission;
    }

    protected void printNoPermission() {
        String msg = "&c&lYou do not have the permission to perform this command &r&8(" + permission + ")";
        sendMessage(msg);
    }
}
