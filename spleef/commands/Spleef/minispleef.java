package spleef.commands.Spleef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.intent.Intent;
import spleef.Blueprints.SpleefBlueprint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import static spleef.spluff.spleefArenaList;

public class minispleef implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            try{

                if(args[0] != null){

                    int i = Integer.parseInt(args[0]);

                    for(SpleefBlueprint arena : spleefArenaList){

                        if(arena.active.contains(((Player) sender).getPlayer())){

                            if(arena.miniSpleefTimeRequest.containsKey(((Player) sender).getPlayer())){

                                arena.miniSpleefTimeRequest.replace(((Player) sender).getPlayer(), i);
                                ((Player) sender).getPlayer().sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou &echanged &byour time request for &f&lminispleef&b!"));

                                if(arena.active.get(0).equals(((Player) sender).getPlayer())){

                                    arena.active.get(1).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent wants to &echange &bwhen &f&lminispleef &bstarts to &f&l" + args[0] + " &bminutes!"));
                                    arena.active.get(1).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bType &f&l/minispleef &bto &aaccept &bthe request!"));

                                }else{

                                    arena.active.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent wants to &echange &bwhen &f&lminispleef &bstarts to &f&l" + args[0] + " &bminutes!"));
                                    arena.active.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bType &f&l/minispleef &bto &aaccept &bthe request!"));

                                }

                            }else{

                                arena.miniSpleefTimeRequest.put(((Player) sender).getPlayer(), i);
                                ((Player) sender).getPlayer().sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou sent a time &arequest &bfor &f&lminispleef&b!"));

                                if(arena.active.get(0).equals(((Player) sender).getPlayer())){

                                    arena.active.get(1).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent wants to &echange &bwhen &f&lminispleef &bstarts to &f&l" + args[0] + " &bminutes!"));
                                    arena.active.get(1).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bType &f&l/minispleef &bto &aaccept &bthe request!"));

                                }else{

                                    arena.active.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent wants to &echange &bwhen &f&lminispleef &bstarts to &f&l" + args[0] + " &bminutes!"));
                                    arena.active.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bType &f&l/minispleef &bto &aaccept &bthe request!"));

                                }

                            }

                        }

                    }

                }

            }catch (NumberFormatException e){

                Bukkit.getLogger().log(Level.SEVERE, e.getMessage());

            }catch (ArrayIndexOutOfBoundsException e){

                for(SpleefBlueprint arena : spleefArenaList){

                    if(arena.active.contains(sender)){

                        arena.miniSpleefTimeRequest.remove(((Player) sender).getPlayer());

                        for(Player player : arena.active){

                            if(arena.miniSpleefTimeRequest.containsKey(player)){

                                arena.ministart = arena.miniSpleefTimeRequest.get(player) * 60;
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent has &aaccepted &bthe &f&lminispleef &btime!"));

                                ((Player) sender).getPlayer().sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou have &accepted &bthe request to change the &f&lminispleef &btime!"));
                                arena.miniSpleefTimeRequest = new HashMap<>();

                            }

                        }

                    }

                }

            }

        }

        return false;
    }
}
