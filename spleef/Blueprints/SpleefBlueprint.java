package spleef.Blueprints;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.HashMap;

public class SpleefBlueprint {

    public ArrayList<Block> snake1;

    public int locationx;
    public int locationy;
    public int locationz;
    public int sizex;
    public int sizez;
    public int points1;
    public int points2;
    public int minireset;
    public int ministart;
    public int miniinterval;
    public int winPoints;
    public int winPointsreq;
    public int seconds;
    public int minutes;
    public int hours;
    public boolean minispleef;
    public boolean Countdown;
    public boolean pause;
    public BukkitTask beginDecay;
    public BukkitTask beginMinispleef;
    public Location loc;
    public Location s1;
    public Location s2;
    public String name;
    public ItemStack icon;

    public ArrayList<Player> que = new ArrayList<>();
    public ArrayList<Player> active = new ArrayList<>();
    public ArrayList<Player> teamG = new ArrayList<>();
    public ArrayList<Player> teamR = new ArrayList<>();
    public int mode;

    public ArrayList<Player> spectators = new ArrayList<>();
    public ArrayList<Player> resetQue = new ArrayList<>();
    public ArrayList<Player> playtoQue = new ArrayList<>();
    public ArrayList<Player> endgame = new ArrayList<>();
    public ArrayList<Block> blocks = new ArrayList<>();

    public HashMap<Player, Integer> miniSpleefTimeRequest = new HashMap<>();
    public HashMap<Player, Integer> miniSpleefIntervalRequest = new HashMap<>();
    public ScoreboardManager spleefBoardManager = Bukkit.getScoreboardManager();
    public Scoreboard spleefBoard;
    public String decayState;
    public SpleefBlueprint ms;

    {
        assert spleefBoardManager != null;
        spleefBoard = spleefBoardManager.getNewScoreboard();
    }

    public Objective spleefMatch = spleefBoard.registerNewObjective(ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

    public SpleefBlueprint(int locationx, int locationy, int locationz, int sizex, int sizez, String name, ItemStack icon){

        mode = 0;

        pause = false;

        minireset = 10;
        ministart = 1500;
        miniinterval = 120;

        snake1 = new ArrayList<>();

        seconds = 0;
        minutes = 0;
        hours = 0;

        this.winPoints = 7;

        this.locationx = locationx;
        this.locationy = locationy;
        this.locationz = locationz;

        loc = new Location(Bukkit.getWorld("world"), locationx, locationy, locationz);
        s1 = new Location(Bukkit.getWorld("world"), locationx + 11.5, locationy + 1, locationz + 1);
        s2 = new Location(Bukkit.getWorld("world"), locationx + 11.5, locationy + 1, locationz + 29);
        s2.setYaw(180);

        this.sizex = sizex;
        this.sizez = sizez;

        this.name = name;
        this.icon = icon;

        points1 = 0;
        points2 = 0;

        spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
        spleefMatch.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

        decayState = "Off";
        Countdown = true;

    }

}