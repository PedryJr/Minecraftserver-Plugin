package spleef.ThreadedSQLandHandling;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import spleef.Blueprints.SpleefBlueprint;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import static spleef.spluff.*;
import static spleef.spluff.password;

public class spleefBlockBreakThread extends Thread {

    BlockBreakEvent event;

    public spleefBlockBreakThread(BlockBreakEvent event) {

        this.event = event;

    }

    @Override
    public void run() {


        for (SpleefBlueprint arena : spleefArenaList) {

            if (arena.active.contains(event.getPlayer())) {

                arena.blocks.remove(event.getBlock());

                Connection connection = null;
                ResultSet data = null;

                try {


                    connection = DriverManager.getConnection(url, username, password);

                    Statement statement = connection.createStatement();

                    data = statement.executeQuery("select * from players order by money DESC");

                    while (data.next()) {

                        if (data.getString("UUID").equals(event.getPlayer().getUniqueId().toString())) {

                            int xp = data.getInt("xp");

                            xp++;

                            statement.executeUpdate("update players set xp = '" + xp + "' where UUID = '" + event.getPlayer().getUniqueId() + "'");

                            break;


                        }

                    }

                } catch (SQLException e) {

                    throw new RuntimeException(e);

                } finally {

                    if (connection != null) {

                        try {

                            assert data != null;
                            data.close();
                            connection.close();

                        } catch (SQLException e) {

                            throw new RuntimeException(e);

                        }

                    }

                }

            }

        }

    }


}