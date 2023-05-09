package spleef.Miscs.MiniTimers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.ParkourBlueprint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static spleef.spluff.timeResult;
import static spleef.spluff.timeResultInSeconds;

public class Clock extends BukkitRunnable {

    Player player;
    ParkourBlueprint arena;

    int seconds;
    int minutes;
    int hours;
    public String readTime;

    public Clock(Player player, ParkourBlueprint arena){

        this.player = player;
        this.arena = arena;

    }

    @Override
    public void run() {


        ApplyParkourBoard(player, arena, true);

    }

    public void ApplyParkourBoard(Player player, ParkourBlueprint arena, boolean timer){

        String newSeconds;
        String newMinutes;
        String newHours;

        if(timer){

            if(this.minutes < 60){

                if(this.seconds < 60){

                    this.seconds++;

                }else{

                    this.seconds = 0;
                    this.minutes++;
                }

            }else{

                this.minutes = 0;
                this.hours++;

            }

        }


        if(this.seconds < 10){

            newSeconds = "0" + this.seconds;

        }else{

            newSeconds = String.valueOf(this.seconds);

        }

        if(this.minutes < 10){

            newMinutes = "0" + this.minutes;

        }else{

            newMinutes = String.valueOf(this.minutes);

        }

        if(this.hours < 10){

            newHours = "0" + this.hours;

        }else{

            newHours = String.valueOf(this.hours);

        }


        ScoreboardManager parkourBoardManager = Bukkit.getScoreboardManager();
        assert parkourBoardManager != null;
        Scoreboard parkourBoard = parkourBoardManager.getNewScoreboard();
        Objective parkourMatch = parkourBoard.registerNewObjective(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

        Score a = parkourMatch.getScore("     ");
        Score b;
        Score c = parkourMatch.getScore("   ");
        Score d = parkourMatch.getScore("  ");
        Score e = parkourMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bTime&c: &b" + newHours + "&f:&b" + newMinutes + "&f:&b" + newSeconds));

        if(arena.active.contains(player)){

            readTime = ChatColor.translateAlternateColorCodes('&',"&c-  &bTime&c: &b&n" + newHours + "&f&n:&b&n" + newMinutes + "&f&n:&b&n" + newSeconds);
            timeResult.replace(player, readTime);
            timeResultInSeconds.replace(player, seconds + (minutes * 60) + (hours * 60 * 60));

        }

        Score f = parkourMatch.getScore(" ");
        Score h = parkourMatch.getScore("");

        parkourMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
        parkourMatch.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

        a.setScore(9);

        b = parkourMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lMap&c: ") + Objects.requireNonNull(arena.icon.getItemMeta()).getDisplayName());
        b.setScore(8);

        c.setScore(7);

        d.setScore(4);
        e.setScore(3);
        f.setScore(2);
        h.setScore(0);

        player.setScoreboard(parkourBoard);

    }

}
