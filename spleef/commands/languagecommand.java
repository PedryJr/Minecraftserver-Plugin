package spleef.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.*;

import static spleef.spluff.*;

public class languagecommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        Connection connection;
        Statement statement;

        try {

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

        if (sender instanceof Player) {

            if(strings.length == 0){

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n&bChange your message language! \n&bto start, use &f&l/language list"));

                return true;

            }else{

                String input = strings[0].toLowerCase();

                switch(input){

                    case "list":

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n&bHere is a list of languages! \n&b---------------------\n" +
                                "&c- &f&lEnglish\n" +
                                "&c- &f&lRussian\n" +
                                "&c- &f&lUkrainian\n" +
                                "&c- &f&lSpanish\n" +
                                "&c- &f&lNorwegian\n" +
                                "&c- &f&lSwedish\n" +
                                "&c- &f&lDanish\n&b---------------------\n" +
                                "&bUse &f&l/language (listed language) &bto set your language!"));

                        break;


                    case "english":

                        try {

                            statement.executeUpdate("update players set lang = 'en' where UUID = '" + Bukkit.getPlayer(sender.getName()).getUniqueId() + "'");

                        } catch (SQLException e) {

                            throw new RuntimeException(e);

                        }

                        break;


                    case "russian":

                        try {

                            statement.executeUpdate("update players set lang = 'ru' where UUID = '" + Bukkit.getPlayer(sender.getName()).getUniqueId() + "'");

                        } catch (SQLException e) {

                            throw new RuntimeException(e);

                        }

                        break;


                    case "ukrainian":

                        try {
                            statement.executeUpdate("update players set lang = 'uk' where UUID = '" + Bukkit.getPlayer(sender.getName()).getUniqueId() + "'");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        break;


                    case "danish":

                        try {
                            statement.executeUpdate("update players set lang = 'da' where UUID = '" + Bukkit.getPlayer(sender.getName()).getUniqueId() + "'");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        break;


                    case "swedish":

                        try {
                            statement.executeUpdate("update players set lang = 'sv' where UUID = '" + Bukkit.getPlayer(sender.getName()).getUniqueId() + "'");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        break;


                    case "norwegian":

                        try {
                            statement.executeUpdate("update players set lang = 'no' where UUID = '" + Bukkit.getPlayer(sender.getName()).getUniqueId() + "'");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        break;


                    case "spanish":

                        try {
                            statement.executeUpdate("update players set lang = 'es' where UUID = '" + Bukkit.getPlayer(sender.getName()).getUniqueId() + "'");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        break;
                }

            }

        }

        return false;
    }
}
