package spleef.commands.Spleef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.Blueprints.QueBlueprint;

import java.util.Objects;

import static spleef.spluff.*;

public class challangeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            for(SpleefBlueprint arena1 : spleefArenaList) {

                if(arena1.name.equalsIgnoreCase(args[1])){

                    if(!arena1.active.isEmpty()){

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThat arena is in use.."));
                        return false;
                    }

                }

                if(arena1.active.contains(Bukkit.getPlayer(args[0]))){

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThat arena is in use.."));
                    return false;

                }

                for(ParkourBlueprint parkourBlueprint : parkourArenaList){

                    if(parkourBlueprint.active.contains(sender)){

                        ((Player) sender).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are already in parkour! Please leave parkour to duel a player.."));
                        return false;

                    }
                    if(parkourBlueprint.active.contains(Bukkit.getPlayer(args[0]))){
                        ((Player) sender).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bThat player is playing parkour.."));
                        return false;

                    }

                }

                if (arena1.active.contains(sender)) {

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are already in a game.."));
                    return false;

                }
                if (arena1.que.contains(sender)) {

                    arena1.que.remove(sender);

                }
                if (args[0].equals(Objects.requireNonNull(((Player) sender).getPlayer()).getPlayerListName())) {

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYou cannot challange yourself"));
                    return false;

                }
                if(arena1.spectators.contains(sender)){

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are currently spectating someone, please exit spectator mode to play!"));
                    return false;

                }
            }

            for(Player player : Bukkit.getOnlinePlayers()){

                if(args[0].equals(player.getPlayerListName())){


                    for(SpleefBlueprint arena1 : spleefArenaList){

                        if(arena1.name.equalsIgnoreCase(args[1])){

                            ItemMeta acceptMeta = accept.getItemMeta();
                            acceptMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lAccept"));
                            accept.setItemMeta(acceptMeta);

                            ItemMeta denyMeta = deny.getItemMeta();
                            denyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lDeny"));
                            deny.setItemMeta(denyMeta);

                            challange.setItem(3, padding);
                            challange.setItem(5, padding);
                            challange.setItem(9, padding);
                            challange.setItem(10, padding);
                            challange.setItem(11, padding);
                            challange.setItem(12, padding);
                            challange.setItem(13, padding);
                            challange.setItem(14, padding);
                            challange.setItem(15, padding);
                            challange.setItem(16, padding);
                            challange.setItem(17, padding);
                            challange.setItem(19, accept);
                            challange.setItem(20, accept);
                            challange.setItem(21, accept);
                            challange.setItem(23, deny);
                            challange.setItem(24, deny);
                            challange.setItem(25, deny);
                            challange.setItem(27, padding);
                            challange.setItem(28, padding);
                            challange.setItem(29, padding);
                            challange.setItem(30, padding);
                            challange.setItem(31, padding);
                            challange.setItem(32, padding);
                            challange.setItem(33, padding);
                            challange.setItem(34, padding);
                            challange.setItem(35, padding);

                            challange.setItem(4, arena1.icon);

                            QueBlueprint que = new QueBlueprint((Player) sender, Bukkit.getPlayer(args[0]), args[1]);
                            challangeList.add(que);

                            que.challangeTarget.openInventory(challange);

                            que.challanger.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bDuel request sent!"));
                            que.challangeTarget.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + que.challanger.getName() + " &bhas &arequested &ba duel!"));

                            return true;

                        }

                    }

                }

            }

        }
        return false;
    }
}

