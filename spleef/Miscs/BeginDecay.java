package spleef.Miscs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.SpleefBlueprint;
import spleef.Miscs.MiniTimers.BlockDecay;
import spleef.spluff;

import java.util.Objects;

public class BeginDecay extends BukkitRunnable {

    spluff plugin;
    SpleefBlueprint arena;
    int seconds;
    public BukkitTask blockDecay;

    public BeginDecay(spluff plugin, SpleefBlueprint arena){

        this.plugin = plugin;
        this.arena = arena;
        seconds = 0;
    }

    @Override
    public void run() {


        switch (seconds){

            case 600:
                blockDecay = new BlockDecay(plugin, arena).runTaskTimer(plugin, 0, 15);

                arena.decayState = "Low";

                setScoreBoard();
                break;
            case 900:

                if(blockDecay != null){
                    blockDecay.cancel();
                }

                blockDecay = new BlockDecay(plugin, arena).runTaskTimer(plugin, 0, 10);

                arena.decayState = "Medium";

                setScoreBoard();
                break;
            case 1200:

                if(blockDecay != null){
                    blockDecay.cancel();
                }

                blockDecay = new BlockDecay(plugin, arena).runTaskTimer(plugin, 0, 4);

                arena.decayState = "High";
                setScoreBoard();
                break;

        }
        seconds++;

    }
    public void setScoreBoard(){

        ScoreboardManager spleefBoardManager = Bukkit.getScoreboardManager();
        assert spleefBoardManager != null;
        Scoreboard spleefBoard = spleefBoardManager.getNewScoreboard();
        Objective spleefMatch = spleefBoard.registerNewObjective(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

        Score a = spleefMatch.getScore("     ");
        Score b;
        Score c = spleefMatch.getScore("   ");
        Score player1;
        Score player2;
        Score d = spleefMatch.getScore("  ");
        Score e = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bPlaying to&c: &b" + arena.winPoints));
        Score f = spleefMatch.getScore(" ");
        Score g = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bDecaystate&c: &b" + arena.decayState));
        Score h = spleefMatch.getScore("");

        spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
        spleefMatch.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

        a.setScore(9);

        b = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lMap&c: ") + Objects.requireNonNull(arena.icon.getItemMeta()).getDisplayName());
        b.setScore(8);

        c.setScore(7);

        player1 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arena.que.get(0).getPlayerListName() + "&c: " + arena.points1));
        player1.setScore(6);
        player2 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arena.que.get(1).getPlayerListName() + "&c: " + arena.points2));
        player2.setScore(5);

        d.setScore(4);
        e.setScore(3);
        f.setScore(2);
        g.setScore(1);
        h.setScore(0);

        arena.que.get(0).setScoreboard(spleefBoard);
        arena.que.get(1).setScoreboard(spleefBoard);

    }
}
