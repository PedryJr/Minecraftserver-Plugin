package spleef.commands.Spleef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.SpleefBlueprint;

import java.util.Objects;

import static spleef.spluff.spleefArenaList;

public class playtoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            for(SpleefBlueprint arena : spleefArenaList){

                if(arena.active.contains(sender)){

                    if(!arena.playtoQue.contains(sender)){

                        if(arena.playtoQue.size() == 1){

                            if(arena.winPointsreq == Integer.parseInt(args[0])){

                                arena.winPoints = Integer.parseInt(args[0]);

                                arena.playtoQue.clear();

                                arena.active.get(1).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are now playing to &f&l" + args[0]));
                                arena.active.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are now playing to &f&l" + args[0]));

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

                        }else{

                            arena.winPointsreq = Integer.parseInt(args[0]);

                            arena.playtoQue.add((Player) sender);

                            if(arena.active.get(0).equals(sender)){

                                arena.active.get(1).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent wants to play to " + args[0]));
                                arena.active.get(1).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bAccept with &f&l/playto " + args[0]));

                                arena.active.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou sent your oponent a playto request, waiting for them to accept.."));
                            }else{

                                arena.active.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent wants to play to " + args[0]));
                                arena.active.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bAccept with &f&l/playto " + args[0]));

                                arena.active.get(1).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou sent your oponent a playto request, waiting for them to accept.."));
                            }

                        }

                    }else{

                        arena.playtoQue.remove(sender);
                        ((Player) sender).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou cancelled your &f&l/playto &brequest"));

                    }


                    return true;

                }

            }

                ((Player) sender).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou need to be in a match to use this command!"));
                return false;

        }

        return false;
    }
}
