package spleef.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.SpleefBlueprint;
import spleef.ManagerMethods.GeneralMethods.ScoreBoardMethods;
import spleef.ManagerMethods.GameMethods.SpleefMethods;
import spleef.spluff;

import java.util.ArrayList;

import static spleef.spluff.*;
import static spleef.spluff.compass;

public class spectateCommand implements CommandExecutor {

    ScoreBoardMethods scoreBoardMethods;
    SpleefMethods spleefMethods;

    spluff plugin;
    public spectateCommand(spluff plugin){

        scoreBoardMethods = new ScoreBoardMethods(plugin);

        spleefMethods = new SpleefMethods(plugin);

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(sender instanceof Player){


            Player player = ((Player) sender).getPlayer();
            Player target = null;

            // TODO: Check if the player is using command arguments, if no arguments are used set target to null, since no arguments means target player doesn't exist!
            if(args.length == 1){

                target = Bukkit.getPlayer(args[0]);

            }

            // TODO: Check if player is in-game, then deny if they are in-game and output a message..
            for(SpleefBlueprint arena : spleefArenaList){

                if(arena.active.contains(player)){

                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou can't spectate while in-game!"));
                    return false;

                }

            }

            for(SpleefBlueprint arena : spleefArenaList){

                if(arena.spectators.contains(player)){
                    player.setAllowFlight(false);
                    arena.active.get(0).showPlayer(player);
                    arena.active.get(1).showPlayer(player);
                    arena.spectators.remove(player);
                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are no longer spectating!"));
                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bTo spectate someone again, use the command \n&f&l/spectate (Player)&b."));

                    ArrayList<Player> players = new ArrayList<>();
                    players.add(player);
                    scoreBoardMethods.ApplyLobbyBoard(players);
                    player.getInventory().clear();
                    player.getInventory().setItem(0, guide);
                    player.getInventory().setItem(4, compass);
                    player.teleport(spawn);
                    player.setGameMode(GameMode.ADVENTURE);
                    return true;

                }

            }

            for(SpleefBlueprint arena : spleefArenaList){

                if(arena.active.contains(target)){

                    assert target != null;

                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are now spectating &f&l" + target.getName() + "&b! \nUse &f&l/spectate &bto stop spectating!"));

                    ArrayList<Player> players = new ArrayList<>();
                    players.add(player);
                    scoreBoardMethods.ApplySpleefBoard(players, arena, false);

                    player.teleport(target);
                    arena.spectators.add(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.setAllowFlight(true);
                    player.getInventory().clear();
                    spleefMethods.HidePlayersFrom(arena);
                    return true;


                }else if(target == null){

                    if(arena.spectators.contains(player)){


                        ArrayList<Player> players = new ArrayList<>();
                        players.add(player);
                        scoreBoardMethods.ApplyLobbyBoard(players);

                        player.getInventory().clear();
                        player.getInventory().setItem(0, guide);
                        player.getInventory().setItem(4, compass);
                        player.teleport(spawn);
                        player.setGameMode(GameMode.ADVENTURE);
                        return true;

                    }

                }

            }

            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bTo spectate someone, use the command \n&f&l/spectate (Player)&b."));

        }
        return false;
    }
}
