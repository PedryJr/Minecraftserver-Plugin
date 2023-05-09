package spleef.ManualUpdate;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.ManagerMethods.GeneralMethods.ScoreBoardMethods;
import spleef.spluff;

import java.sql.*;
import java.util.logging.Level;

import static spleef.spluff.*;

public class ManualUpdate extends BukkitRunnable {

    spluff plugin;
    ScoreBoardMethods scoreBoardMethods;
    int leaderboardUpdate;
    int specUpdate;
    int parkourUpdate;

    public ManualUpdate(spluff plugin){

        this.plugin = plugin;

        scoreBoardMethods = new ScoreBoardMethods(plugin);

        leaderboardUpdate = 0;
        specUpdate = 0;
        parkourUpdate = 0;

    }

    @Override
    public void run() {

        leaderboardUpdate++;
        specUpdate++;
        parkourUpdate++;

        if(leaderboardUpdate > 20){

            try {

                UpdateLeaderboard();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            leaderboardUpdate = 0;

        }

        if(specUpdate > 20){

            controllSpectators();

            specUpdate = 0;

        }

        if(parkourUpdate > 20){

            try {
                UpdateParkourLeaderBoard(plugin);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            parkourUpdate = 0;

        }

        SpleefUpdate();








    }



    void controllSpectators(){

        for(SpleefBlueprint arena : spleefArenaList){

            for(Player player : arena.spectators){


                if(arena.loc.distance(player.getLocation()) > 150){

                    player.teleport(arena.s1);

                }

            }

        }

    }

    void SpleefUpdate() {

        for(SpleefBlueprint spleefBlueprint : spleefArenaList){

            if(!spleefBlueprint.active.isEmpty()){

                scoreBoardMethods.ApplySpleefBoard(spleefBlueprint.active, spleefBlueprint, true);
                scoreBoardMethods.ApplySpleefBoard(spleefBlueprint.spectators, spleefBlueprint, false);

            }

        }

    }


    public void UpdateParkourLeaderBoard(spluff plugin) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/parkourleaderboard?useSSL=false";

        TextHologramLine head;

        for(ParkourBlueprint arena : parkourArenaList){
            HolographicDisplaysAPI api;

            api = HolographicDisplaysAPI.get(plugin);

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("select * from maps order by time ASC");

            Hologram lb;

            if(useHolographicDisplays){

                try{

                    Class.forName("com.mysql.cj.jdbc.Driver");

                }catch (ClassNotFoundException ignored){

                }

                try{



                    lb = arena.hologram;

                    head = lb.getLines().appendText(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&bLeadeboard&c: " + arena.icon.getItemMeta().getDisplayName() + "!"));

                    lb.getLines().clear();
                    lb.getLines().appendText(head.getText());

                    int i = 1;
                    while(result.next()){

                        if(result.getString("mapname").equals(arena.name)){

                            lb.getLines().appendText(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&b"
                                    + i
                                    + "&c. &f&l"
                                    + result.getString("playername")
                                    + " &c|&f&l"
                                    + result.getString("time")
                                    + " &c| &7"
                                    + result.getString("date")));

                            if(i >= 10){

                                break;

                            }
                            i++;

                        }

                    }

                    try {

                        result.close();
                        connection.close();

                    } catch (SQLException ignored) {


                    }


                }catch (SQLException ignored) {


                }

            }

        }

    }

    void UpdateLeaderboard() throws SQLException {


        for(SpleefBlueprint arena : spleefArenaList){

            if(arena.active.isEmpty()){

                if(arena.beginMinispleef != null){

                    arena.beginMinispleef.cancel();

                }
                if(arena.beginDecay != null){

                    arena.beginDecay.cancel();

                }

            }

        }


        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery("select * from players order by money DESC");

        lb.getLines().clear();
        lb.getLines().appendText(head.getText());

        int i = 1;
        while(result.next()){

            line = lb.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&b" + i + "&c. &f&l" + result.getString(2) + " &c| &f&l" + result.getString(4) + "&c$"));

            if(i >= 10){

                break;

            }
            i++;

        }

        ResultSet result1 = statement.executeQuery("select * from players order by xp DESC");

        lb2.getLines().clear();
        lb2.getLines().appendText(head2.getText());

        int j = 1;
        while(result1.next()){

            line2 = lb2.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&b" + j + "&c. &f&l" + result1.getString(2) + " &c| &f&l" + result1.getString(5) + "&cxp"));

            if(j >= 10){

                break;

            }
            j++;

        }


    }

}
