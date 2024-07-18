package com.asintoto.basic.players;

import com.asintoto.basic.enums.BasicChar;
import com.asintoto.colorlib.ColorLib;
import com.asintoto.basic.Basic;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.UUID;

public class BasicPlayer {
    private Player player;

    private static String banKickMessage = ColorLib.setColors("&c" + BasicChar.CROSS + "You are banned from this server. Reason {reason}");

    public BasicPlayer(Player player) {
        this.player = player;
    }


    /**
     * Send a message to the player.
     * Formatted with ColorLib
     *
     * @param msg
     */
    public void sendMessage(String msg) {
        player.sendMessage(ColorLib.setColors(msg));
    }


    /**
     * Respawn the player
     *
     */
    public void respawn() {
        new BukkitRunnable() {

            @Override
            public void run() {
                player.spigot().respawn();
            }

        }.runTaskLater(Basic.getPlugin(), 1L);
    }


    /**
     *  Get the IP Address of the player
     *
     * @return
     */
    public String getIP() {
        if (player.getAddress() == null) {
            return "N/A";
        }
        return player.getAddress().getAddress().getHostAddress();
    }


    /**
     * Kill the player
     *
     */
    public void kill() {
        player.setHealth(0.0);
    }


    /**
     *  Method to run a command as the player.
     *  Make sure to type the command without /.
     *  Example: executeCommand(give @p diamond 1)
     *
     * @param command
     */
    public void executeCommand(String command) {
        Bukkit.dispatchCommand(player, command);
    }


    /**
     * Restore the health of the player
     *
     */
    public void heal() {
        player.setHealth(player.getMaxHealth());
    }


    /**
     * Restore the hunger level and the saturation of the player
     *
     */
    public void feed() {
        player.setFoodLevel(20);
        player.setSaturation(20.0f);
    }


    /**
     *
     * Ban the player.
     * The duration must be in seconds
     *
     * @param reason
     * @param duration
     * @param ip
     * @param source
     */
    public void ban(String reason, long duration, boolean ip, String source) {

        Date banExpire = duration <= -1 ? null : new Date(System.currentTimeMillis() + duration * 1000);

        BanList.Type bantype = ip ? BanList.Type.IP : BanList.Type.PROFILE;

        player.getServer().getBanList(bantype).addBan(ip ? getIP() : player.getName(), reason, banExpire, ColorLib.setColors(source));
        player.kickPlayer(banKickMessage.replace("{reason}", reason));
    }

    /**
     * Perma-Ban a player
     *
     * @param reason
     */
    public void ban(String reason) {
        ban(reason, -1, false, null);
    }


    /**
     * Perma-IP-Ban a player
     *
     * @param reason
     */
    public void banIP(String reason) {
        ban(reason, -1, true, null);
    }

    /**
     * Get the player's UUID
     *
     * @return
     */
    public UUID getUUID() {
        return player.getUniqueId();
    }


    /**
     * Get the player's UUID to string
     *
     * @return
     */
    public String getUUIDtoString() {
        return getUUID().toString();
    }


    /**
     * Get the player
     *
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }


    /**
     * Get the player
     *
     * @return
     */
    public Player player() {
        return this.player;
    }


    /**
     * Convert a player to a BasicPlayer
     *
     * @param p
     * @return
     */
    public static BasicPlayer from(Player p) {
        return new BasicPlayer(p);
    }

    public static String getBanKickMessage() {
        return banKickMessage;
    }

    public static void setBanKickMessage(String banKickMessage) {
        // Use {reason} to insert the ban reason
        BasicPlayer.banKickMessage = ColorLib.setColors(banKickMessage);
    }
}
