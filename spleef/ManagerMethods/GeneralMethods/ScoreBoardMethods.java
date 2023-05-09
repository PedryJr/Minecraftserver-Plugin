package spleef.ManagerMethods.GeneralMethods;

import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.spluff;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ScoreBoardMethods {

    spluff plugin;
    public ScoreBoardMethods(spluff plugin){

        this.plugin = plugin;

    }

    public void ApplySpleefBoard(ArrayList<Player> players, SpleefBlueprint arena, boolean timer){

        String newSeconds;
        String newMinutes;
        String newHours;

        if(timer){

            if(arena.minutes < 60){

                if(arena.seconds < 60){

                    arena.seconds++;

                }else{

                    arena.seconds = 0;
                    arena.minutes++;
                }

            }else{

                arena.minutes = 0;
                arena.hours++;

            }

        }

        if(arena.seconds < 10){

            newSeconds = "0" + arena.seconds;

        }else{

            newSeconds = String.valueOf(arena.seconds);

        }

        if(arena.minutes < 10){

            newMinutes = "0" + arena.minutes;

        }else{

            newMinutes = String.valueOf(arena.minutes);

        }

        if(arena.hours < 10){

            newHours = "0" + arena.hours;

        }else{

            newHours = String.valueOf(arena.hours);

        }

        ScoreboardManager spleefBoardManager = Bukkit.getScoreboardManager();
        assert spleefBoardManager != null;
        Scoreboard spleefBoard = spleefBoardManager.getNewScoreboard();
        Objective spleefMatch = spleefBoard.registerNewObjective(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

        Score a = spleefMatch.getScore("      ");
        Score b;
        Score c = spleefMatch.getScore("    ");
        Score player1;
        Score player2;
        Score d = spleefMatch.getScore("   ");
        Score e = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bPlaying to&c: &b" + arena.winPoints));
        Score f = spleefMatch.getScore("  ");
        Score g = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bDecaystate&c: &b" + arena.decayState));
        Score h = spleefMatch.getScore(" ");
        Score i = spleefMatch.getScore("");
        Score j = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bTime&c: &b" + newHours + "&f:&b" + newMinutes + "&f:&b" + newSeconds));

        spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
        spleefMatch.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

        a.setScore(11);

        b = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lMap&c: ") + Objects.requireNonNull(arena.icon.getItemMeta()).getDisplayName());
        b.setScore(10);

        c.setScore(9);

        player1 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arena.que.get(0).getPlayerListName() + "&c: " + arena.points1));
        player1.setScore(8);
        player2 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arena.que.get(1).getPlayerListName() + "&c: " + arena.points2));
        player2.setScore(7);

        d.setScore(6);
        e.setScore(5);
        i.setScore(4);
        j.setScore(3);
        f.setScore(2);
        g.setScore(1);
        h.setScore(0);

        for(Player player : players){

            player.setScoreboard(spleefBoard);

        }

    }

    public void ApplyLobbyBoard(ArrayList<Player> players){

        ScoreboardManager lobbyboardmanager = Bukkit.getScoreboardManager();
        assert lobbyboardmanager != null;
        Scoreboard lobbyboard = lobbyboardmanager.getNewScoreboard();
        Objective lobby = lobbyboard.registerNewObjective(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

        Score g = lobby.getScore("     ");
        g.setScore(5);

        Score h = lobby.getScore(ChatColor.translateAlternateColorCodes('&', "           &b&lWelcome&f&l!"));
        h.setScore(4);

        Score i = lobby.getScore("   ");
        i.setScore(3);

        Score j = lobby.getScore(ChatColor.translateAlternateColorCodes('&', "            &e↓&bUpdate&e↓"));
        j.setScore(2);

        Score m = lobby.getScore(ChatColor.translateAlternateColorCodes('&', "     &e&lParkour Revamp v.2"));
        m.setScore(1);

        Score d = lobby.getScore("");
        d.setScore(0);

        lobby.setDisplaySlot(DisplaySlot.SIDEBAR);
        lobby.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

        for(Player player : players){

            player.setScoreboard(lobbyboard);

        }

    }

    public enum ScoreType {
        Spleef, Parkour, Lobby
    }
    public void ApplyBoard(ScoreType type){

        switch (type){

            case Lobby:

                break;

            case Spleef:

                break;

        }

    }


}
