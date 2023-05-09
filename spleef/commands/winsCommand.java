package spleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import spleef.spluff;

import java.sql.*;
import java.util.Objects;

import static spleef.spluff.*;

public class winsCommand implements CommandExecutor {

    spluff plugin;

    public winsCommand(spluff plugin){

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Connection connection;
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Statement statement;
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                ResultSet data = statement.executeQuery("select * from players");

                while(data.next()){

                    if(data.getString("UUID").equals(Objects.requireNonNull(((Player) sender).getPlayer()).getUniqueId().toString())){

                        ((Player) sender).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou have &f&l" + data.getInt("wins") + " &bwins&f&l!"));

                        break;

                    }

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        return true;

    }
}
