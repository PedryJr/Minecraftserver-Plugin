package spleef.Blueprints;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import spleef.spluff;

import java.lang.management.LockInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static spleef.spluff.*;

public class ParkourBlueprint {

    public int locationx;
    public int locationy;
    public int locationz;
    public int seconds;
    public int minutes;
    public int hours;
    public double lbx;
    public double lby;
    public double lbz;
    public Location loc;
    public Location finish;
    public Location lb;

    public Hologram hologram;
    public HashMap<Player, Location> checkpoints;
    public String name;
    public ItemStack icon;
    public ArrayList<Player> que = new ArrayList<>();
    public ArrayList<Player> active = new ArrayList<>();
    public ArrayList<Player> spectators = new ArrayList<>();
    public ScoreboardManager parkourBoardManager = Bukkit.getScoreboardManager();
    public Scoreboard parkouorBoard;

    {
        assert parkourBoardManager != null;
        parkouorBoard = parkourBoardManager.getNewScoreboard();
    }

    public Objective parkouorMatch = parkouorBoard.registerNewObjective(ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

    public ParkourBlueprint(int locationx, int locationy, int locationz,int finishx, int finishy, int finishz, String name, ItemStack icon, int yaw, double xoff, double zoff, double lbx, double lby, double lbz){

        seconds = 0;
        minutes = 0;
        hours = 0;

        this.locationx = locationx;
        this.locationy = locationy;
        this.locationz = locationz;

        loc = new Location(Bukkit.getWorld("world"), locationx + xoff, locationy, locationz + zoff);
        loc.setYaw(yaw);
        finish = new Location(Bukkit.getWorld("world"), finishx, finishy, finishz);

        checkpoints = new HashMap<>();

        this.name = name;
        this.icon = icon;

        this.lbx = lbx;
        this.lby = lby;
        this.lbz = lbz;

        parkouorMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
        parkouorMatch.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

        HolographicDisplaysAPI api = HolographicDisplaysAPI.get(Bukkit.getPluginManager().getPlugin("Spleef.1.12"));

        hologram = api.createHologram(new Location(Bukkit.getWorld("world"), lbx, lby, lbz));

    }


}