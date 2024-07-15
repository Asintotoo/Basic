package com.asintoto.basic.commands;

import com.asintoto.basic.Basic;
import com.asintoto.basic.interfaces.RegisterCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;


public abstract class BasicCommand implements CommandExecutor, TabCompleter {

    protected List<String> NO_COMPLETE = Collections.emptyList();
    protected List<String> PLAYER_LIST = null;
    protected String label;

    public BasicCommand(String label) {
        this.label = label;
        
        if(this.getClass().isAnnotationPresent(RegisterCommand.class)) {
            Basic.getPlugin().getCommand(label).setExecutor(this);
        }
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        onCommand(sender, args);
        return true;
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return onTabComplete(sender, args);
    }

    public void onCommand(CommandSender sender, String[] args) {

    }

    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return NO_COMPLETE;
    }
}
