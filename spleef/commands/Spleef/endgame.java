package spleef.commands.Spleef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import spleef.Blueprints.SpleefBlueprint;
import spleef.ManagerMethods.GeneralMethods.ScoreBoardMethods;
import spleef.spluff;

import java.util.ArrayList;

import static spleef.ManagerMethods.GeneralMethods.StupidlyLargeMethods.renewArena;
import static spleef.spluff.*;
import static spleef.spluff.compass;

public class endgame implements CommandExecutor {

    spluff plugin;
    ScoreBoardMethods scoreBoardMethods;

    public endgame(spluff plugin){

        this.plugin = plugin;

        scoreBoardMethods = new ScoreBoardMethods(plugin);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            for(SpleefBlueprint arena : spleefArenaList){

                if(arena.active.contains(((Player) sender).getPlayer())){

                    ArrayList<Player> lobbyApplicants = new ArrayList<>();

                    if(arena.endgame.contains(sender)){

                        for(Player player : arena.active){

                            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + sender.getName() + " &bhas &ccancelled &bthe endgame request!"));

                        }
                        for(Player player : arena.spectators){

                            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + sender.getName() + " &bhas &ccancelled &bthe endgame request!"));

                        }

                        arena.endgame.remove(((Player) sender).getPlayer());
                        return true;

                    }else{

                        arena.endgame.add(((Player) sender).getPlayer());

                        if(arena.endgame.size() == 2){

                            for(Player player : arena.active){

                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour game has ended!"));

                                lobbyApplicants.add(player);

                                player.getInventory().clear();
                                player.getInventory().setItem(0, gadgets);
                                player.getInventory().setItem(4, compass);

                                player.setGameMode(GameMode.ADVENTURE);

                                arena.que.get(0).teleport(spawn);
                                lobbyApplicants.add(arena.que.get(0));
                                arena.que.get(1).teleport(spawn);
                                lobbyApplicants.add(arena.que.get(1));

                                arena.resetQue.remove(player);
                                arena.points2 = 0;
                                arena.points1 = 0;

                                if(!arena.spectators.isEmpty()){

                                    for(Player p : arena.spectators){

                                        p.getInventory().clear();
                                        p.getInventory().setItem(0, gadgets);
                                        p.getInventory().setItem(4, compass);
                                        p.setGameMode(GameMode.ADVENTURE);
                                        p.teleport(spawn);
                                        p.setAllowFlight(false);
                                        arena.que.get(0).showPlayer(p);
                                        arena.que.get(1).showPlayer(p);
                                        lobbyApplicants.add(p);

                                    }

                                }

                            }

                            scoreBoardMethods.ApplyLobbyBoard(lobbyApplicants);
                            renewArena(arena);
                            return true;

                        }else{

                            for(Player player : arena.active){

                                if(player.equals(((Player) sender).getPlayer())){

                                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + sender.getName() + " &bhas &arequested &bto end the game!"));

                                }else{

                                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + sender.getName() + " &bhas &arequested &bto end the game! \n&bTo accept, type &f&l/endgame"));

                                }

                            }
                            for(Player player : arena.spectators){

                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + sender.getName() + " &bhas &arequested &bto end the game!"));

                            }

                        }

                    }

                }

            }

        }

        return false;
    }
}
