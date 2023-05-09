package spleef.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static spleef.spluff.shopMenu;

public class shop implements CommandExecutor {



    public shop(){


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player player = ((Player) sender).getPlayer();

            player.openInventory(shopMenu);

        }

        return false;
    }
}
