package spleef.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.Channel;

public class customRank implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            if(sender.hasPermission("spleef.customrank")){

                if(args.length == 1){

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + sender.getName() + " meta setprefix 8 \"" + args[0] + " &f\"");
                    return true;

                }else{

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bUse &f&l/prefix (The prefix you want) &bto give yourself a prefix!"));

                }

            }

        }

        return false;
    }
}
