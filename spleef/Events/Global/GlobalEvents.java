package spleef.Events.Global;

import net.minecraft.server.v1_12_R1.ChatClickable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;
import spleef.ManagerMethods.GeneralMethods.DiscordBot;
import spleef.ManagerMethods.GeneralMethods.ScoreBoardMethods;
import spleef.ManagerMethods.GeneralMethods.StupidlyLargeMethods;
import spleef.spluff;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static spleef.spluff.*;
import static spleef.spluff.spawn;

public class GlobalEvents {


    spluff plugin;

    ScoreBoardMethods scoreBoardMethods;

    public GlobalEvents(spluff plugin){

        this.plugin = plugin;

        scoreBoardMethods = new ScoreBoardMethods(plugin);

    }

    /** Connect this class with game methods **/

    public void InitializePlayer(PlayerJoinEvent event) throws SQLException {

        Player player = event.getPlayer();

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery("select * from players order by money DESC");

        boolean isNew = true;

        while(result.next()){

            if(result.getString("UUID").equals(player.getUniqueId().toString())){

                isNew = false;

            }

        }

        if(isNew){


            int i = statement.executeUpdate("insert into players VALUES('"
                    + player.getUniqueId()
                    + "', '"
                    + player.getPlayerListName()
                    + "', '0', '200', '0'"
                    + ", 'en')");


            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bSomeone new has appeared! Their name is &f&l"
                    + player.getPlayerListName()));

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bIt appears that youre new here?..\n&9&lDiscord: &b&nhttps://discord.gg/WXk8htaJ3u"));
        }

        ArrayList<Player> lobbyApplicants = new ArrayList<>();
        lobbyApplicants.add(player);
        scoreBoardMethods.ApplyLobbyBoard(lobbyApplicants);


        player.getInventory().clear();
        player.getInventory().setItem(0, guide);
        player.getInventory().setItem(4, compass);
        player.teleport(spawn);
        player.setGameMode(GameMode.ADVENTURE);

        discordBot.playerJoin(event.getPlayer().getName());

    }

    public void suspendPlayer(PlayerQuitEvent event){

        discordBot.playerLeave(event.getPlayer().getName());

    }

    public void Chat(AsyncPlayerChatEvent event){

        StupidlyLargeMethods collection = new StupidlyLargeMethods(plugin);

        try {

            collection.chatLevelDisplay(event);

        } catch (Exception e) {

            System.out.println(e.getMessage());

            throw new RuntimeException(e);
        }

    }


    /** Methods for handling damage **/
    public void PreventPVP(EntityDamageEvent event){

        Player player;

        if(event.getEntity() instanceof Player){

            player = (Player) event.getEntity();

            double x = player.getLocation().getX();
            double z = player.getLocation().getZ();

            if(x > -500 && x < 500 && z > -500 && z < 500){

                event.setCancelled(true);

            }

        }

    }

    public void preventFallDamage(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){

            if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){

                event.setCancelled(true);

            }

        }

    }

    public void PreventVoidDeath(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){

            if((EntityDamageEvent.DamageCause.VOID.equals(event.getCause()))){

                ScoreboardManager lobbyboardmanager = Bukkit.getScoreboardManager();
                assert lobbyboardmanager != null;
                Scoreboard lobbyboard = lobbyboardmanager.getNewScoreboard();
                Objective lobby = lobbyboard.registerNewObjective(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");


                Score i = lobby.getScore("   ");
                i.setScore(4);

                Score j = lobby.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lWelcome&f&l!"));
                j.setScore(3);

                Score k = lobby.getScore("  ");
                k.setScore(2);

                Score l = lobby.getScore(ChatColor.translateAlternateColorCodes('&', "  &c&nIn development"));
                l.setScore(1);

                Score m = lobby.getScore("");
                m.setScore(0);

                lobby.setDisplaySlot(DisplaySlot.SIDEBAR);
                lobby.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

                Objects.requireNonNull(((Player) event.getEntity()).getPlayer()).setScoreboard(lobbyboard);
                ((Player) event.getEntity()).getPlayer().getInventory().setItem(0, guide);
                ((Player) event.getEntity()).getPlayer().getInventory().setItem(4, compass);
                ((Player) event.getEntity()).getPlayer().teleport(spawn);

            }

        }

    }

    @EventHandler
    public void PreventFireDeath(EntityDamageEvent event){

        event.setCancelled(true);

        if(event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)){

            event.setCancelled(true);

        }

        if(event.getCause().equals(EntityDamageEvent.DamageCause.HOT_FLOOR)){

            event.setCancelled(true);

        }

        if(event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)){

            event.setCancelled(true);

        }

        if(event.getCause().equals(EntityDamageEvent.DamageCause.MELTING)){

            event.setCancelled(true);

        }

        if(event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)){

            event.setCancelled(true);

        }

    }

    public void PluginsCommand(PlayerCommandPreprocessEvent event){

        String command = event.getMessage();

        if (command.equalsIgnoreCase("/plugins")) {

            if (!event.getPlayer().hasPermission("plugins.command")) {

                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "Plugins (1): &aSnowCentral(CORE)"));
                event.setCancelled(true);

            }

        }

    }


    public void Cancel(InventoryClickEvent event){

        if(Objects.equals(event.getCurrentItem(), padding)){

            event.setCancelled(true);

        }

        if(Objects.equals(event.getCurrentItem(), exitQue)){

            event.setCancelled(true);

        }

    }

    public void Shop(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem().equals(buyDust)){


            player.closeInventory();
            player.openInventory(dustShop);
            event.setCancelled(true);


        }

        if(event.getCurrentItem().equals(comingsoon)){

            player.closeInventory();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThat is coming soon.."));
            event.setCancelled(true);

        }

        int money = 0;

        if(event.getCurrentItem().equals(buy50)){

            Connection connection = null;
            try {



                connection = DriverManager.getConnection(url, username, password);

                Statement statement = connection.createStatement();

                ResultSet result = statement.executeQuery("select * from players order by money DESC");

                while(result.next()){

                    if(result.getString("UUID").equals(player.getUniqueId().toString())){

                        money = Integer.parseInt(result.getString("money"));

                    }

                }

                if(money >= 10){

                    money -= 10;
                    player.closeInventory();
                    event.setCancelled(true);

                    statement.executeUpdate("UPDATE players SET money = '" + money + "' WHERE UUID = '" + player.getUniqueId().toString() + "';");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mysterydust add " + player.getName() + " 50");

                } else {

                    player.closeInventory();
                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou can not afford that!"));
                    event.setCancelled(true);

                }



                connection.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        if(event.getCurrentItem().equals(buy200)){

            Connection connection = null;
            try {



                connection = DriverManager.getConnection(url, username, password);

                Statement statement = connection.createStatement();

                ResultSet result = statement.executeQuery("select * from players order by money DESC");

                while(result.next()){

                    if(result.getString("UUID").equals(player.getUniqueId().toString())){

                        money = Integer.parseInt(result.getString("money"));

                    }

                }

                if(money >= 35){

                    money -= 35;
                    player.closeInventory();
                    event.setCancelled(true);

                    statement.executeUpdate("UPDATE players SET money = '" + money + "' WHERE UUID = '" + player.getUniqueId().toString() + "';");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mysterydust add " + player.getName() + " 200");

                } else {

                    player.closeInventory();
                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou can not afford that!"));
                    event.setCancelled(true);

                }



                connection.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        if(event.getCurrentItem().equals(buy500)){

            Connection connection = null;
            try {



                connection = DriverManager.getConnection(url, username, password);

                Statement statement = connection.createStatement();

                ResultSet result = statement.executeQuery("select * from players order by money DESC");

                while(result.next()){

                    if(result.getString("UUID").equals(player.getUniqueId().toString())){

                        money = Integer.parseInt(result.getString("money"));

                    }

                }

                if(money >= 60){

                    money -= 60;
                    player.closeInventory();
                    event.setCancelled(true);

                    statement.executeUpdate("UPDATE players SET money = '" + money + "' WHERE UUID = '" + player.getUniqueId().toString() + "';");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mysterydust add " + player.getName() + " 500");

                } else {

                    player.closeInventory();
                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou can not afford that!"));
                    event.setCancelled(true);

                }



                connection.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

}
