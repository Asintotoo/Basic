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

    protected List<String> NO_COMPLETE = Collections.emptyList();
    protected List<String> PLAYER_LIST = null;
    protected String label;

    protected CommandSender sender;
    protected String[] args;

    private String usage;

    public BasicCommand(String label) {
        this.label = label;
        this.usage = ColorLib.setColors("&cUsage: /" + label + " <params...>");
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
}
