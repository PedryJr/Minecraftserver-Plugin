package spleef.Events.Parkour;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.QueBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.ManagerMethods.GameMethods.ParkourMethods;
import spleef.ManagerMethods.GeneralMethods.ScoreBoardMethods;
import spleef.Miscs.MiniTimers.CircleParticles;
import spleef.Miscs.MiniTimers.Clock;
import spleef.spluff;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static spleef.spluff.*;

public class ParkourEvents {
    spluff plugin;
    ParkourMethods parkourMethods;
    ScoreBoardMethods scoreBoardMethods;
    public ParkourEvents(spluff plugin){

        this.plugin = plugin;

        parkourMethods = new ParkourMethods(plugin);
        scoreBoardMethods = new ScoreBoardMethods(plugin);

    }

    public void PreventInGamePVP(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){

            for(ParkourBlueprint parkourBlueprint : parkourArenaList){

                if(parkourBlueprint.active.contains( (Player) event.getEntity())){

                    event.setCancelled(true);

                }

            }

        }

    }

    public void finish(PlayerMoveEvent event){

        for(ParkourBlueprint parkourBlueprint : parkourArenaList){

            if(parkourBlueprint.active.contains(event.getPlayer())){

                if(event.getPlayer().getLocation().distance(parkourBlueprint.finish) < 1){

                    parkourBlueprint.active.remove(event.getPlayer());

                    event.getPlayer().getInventory().clear();
                    event.getPlayer().getInventory().setItem(0, gadgets);
                    event.getPlayer().getInventory().setItem(4, compass);
                    event.getPlayer().teleport(spawn);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&f&l"
                            + event.getPlayer().getName()
                            + " &bhas finished the map "
                            + parkourBlueprint.icon.getItemMeta().getDisplayName()
                            + "\n" + timeResult.get(event.getPlayer())));
                    parkourBlueprint.checkpoints.remove(event.getPlayer());

                    clocks.get(event.getPlayer()).cancel();
                    clocks.remove(event.getPlayer());

                    ArrayList<Player> lobbyApplicants = new ArrayList<>();
                    lobbyApplicants.add(event.getPlayer());
                    scoreBoardMethods.ApplyLobbyBoard(lobbyApplicants);

                    String url = "jdbc:mysql://localhost:3306/parkourleaderboard?useSSL=false";

                    Connection connection = null;
                    Statement statement = null;
                    ResultSet result = null;
                    ResultSet data = null;

                    try {

                        connection = DriverManager.getConnection(url, username, password);
                        statement = connection.createStatement();
                        result = statement.executeQuery("select * from maps");

                        boolean isNew = true;

                        while(result.next()){

                            if(result.getString("mapname").equals(parkourBlueprint.name) && result.getString("playername").equals(event.getPlayer().getPlayerListName())){

                                isNew = false;

                            }

                        }

                        if(isNew){

                            Date date = new Date();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                            String formattedDate = dateFormat.format(date);

                            statement.executeUpdate("insert into maps VALUES('"
                                    + parkourBlueprint.name
                                    + "', '"
                                    + event.getPlayer().getPlayerListName()
                                    + "', '"
                                    + timeResult.get(event.getPlayer())
                                    + "', '"
                                    + formattedDate
                                    + "', "
                                    + timeResultInSeconds.get(event.getPlayer())
                                    + ")");

                        } else {

                            data = statement.executeQuery("select * from maps");

                            while(data.next()){

                                if(data.getString("playername").equals(event.getPlayer().getPlayerListName()) && data.getString("mapname").equals(parkourBlueprint.name)){

                                    if(timeResultInSeconds.get(event.getPlayer()) < Integer.parseInt(data.getString("seconds"))){

                                        Date date = new Date();

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                                        String formattedDate = dateFormat.format(date);

                                        statement.executeUpdate("UPDATE maps set time = '"
                                                + timeResult.get(event.getPlayer())
                                                + "', date = '"
                                                + formattedDate
                                                + "', seconds = '" + timeResultInSeconds.get(event.getPlayer())
                                                + "' where playername = '"
                                                + event.getPlayer().getPlayerListName()
                                                + "' AND mapname = '"
                                                + parkourBlueprint.name
                                                + "'");

                                    }

                                }

                            }

                        }

                        timeResult.remove(event.getPlayer());
                        timeResultInSeconds.remove(event.getPlayer());

                        return;

                    }catch (SQLException ignored) {

                    }finally {

                        if(result != null){

                            try {

                                result.close();

                            } catch (SQLException e) {

                                throw new RuntimeException(e);

                            }

                        }

                        if(data != null){

                            try {

                                data.close();

                            } catch (SQLException e) {

                                throw new RuntimeException(e);

                            }

                        }

                        if(statement != null){

                            try {

                                statement.close();

                            } catch (SQLException e) {

                                throw new RuntimeException(e);

                            }

                        }

                        if(connection != null){

                            try {

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

    public void Cancel(){



    }

    public void InitiateGame(InventoryClickEvent event){

        if(Objects.equals(event.getCurrentItem(), playParkour)){

            event.getWhoClicked().closeInventory();
            event.getWhoClicked().openInventory(parkourMenu);
            event.setCancelled(true);

            return;

        }

        for (ParkourBlueprint parkourBlueprint : parkourArenaList){

            assert parkourBlueprint.icon != null;

            if(!event.getCurrentItem().equals(parkourBlueprint.icon)){

                continue;

            }

            for(QueBlueprint list : challangeList){

                if (list.challanger.equals(event.getWhoClicked()) || list.challangeTarget.equals(event.getWhoClicked())) {

                    list.challanger.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + list.challanger.getPlayer().getName() + " &bhas &ccancelled &bthe duel!"));
                    list.challangeTarget.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + list.challanger.getPlayer().getName() + " &bhas &ccancelled &bthe duel!"));

                    Player p1 = list.challanger;
                    Player p2 = list.challangeTarget;

                    challangeList.remove(list);

                    p1.closeInventory();
                    p2.closeInventory();
                    break;

                }

            }

            for(SpleefBlueprint spleefBlueprint : spleefArenaList){

                if(spleefBlueprint.que.contains( (Player) event.getWhoClicked())){

                    ((Player) event.getWhoClicked()).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou have been removed from spleef!"));
                    ((Player) event.getWhoClicked()).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are playing parkour!"));
                    event.getWhoClicked().teleport(parkourBlueprint.loc);
                    event.getWhoClicked().getInventory().clear();
                    event.getWhoClicked().getInventory().setItem(0, restartPK);
                    event.getWhoClicked().getInventory().setItem(4, checkpoint);
                    event.getWhoClicked().getInventory().setItem(8, exitQue);
                    BukkitTask parkourTimer = new Clock((Player) event.getWhoClicked(), parkourBlueprint).runTaskTimer(plugin, 0, 20);
                    clocks.put((Player) event.getWhoClicked(), parkourTimer);
                    timeResult.put((Player) event.getWhoClicked(), "");
                    timeResultInSeconds.put((Player) event.getWhoClicked(), 0);
                    spleefBlueprint.que.remove( (Player) event.getWhoClicked());
                    parkourBlueprint.active.add(((Player) event.getWhoClicked()));
                    parkourBlueprint.checkpoints.put( (Player) event.getWhoClicked(), parkourBlueprint.loc);

                    return;

                }

            }

            ((Player) event.getWhoClicked()).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are playing parkour!"));
            event.getWhoClicked().teleport(parkourBlueprint.loc);
            event.getWhoClicked().getInventory().clear();
            event.getWhoClicked().getInventory().setItem(0, restartPK);
            event.getWhoClicked().getInventory().setItem(4, checkpoint);
            event.getWhoClicked().getInventory().setItem(8, exitQue);
            BukkitTask parkourTimer = new Clock((Player) event.getWhoClicked(), parkourBlueprint).runTaskTimer(plugin, 0, 20);
            clocks.put((Player) event.getWhoClicked(), parkourTimer);
            timeResult.put((Player) event.getWhoClicked(), "");
            timeResultInSeconds.put((Player) event.getWhoClicked(), 0);
            parkourBlueprint.active.add(((Player) event.getWhoClicked()));
            parkourBlueprint.checkpoints.put( (Player) event.getWhoClicked(), parkourBlueprint.loc);

        }

    }

    public void LeaveQue(PlayerInteractEvent event){

        if(!(event.getItem() == null)){

            if(event.getAction().equals(Action.LEFT_CLICK_AIR) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.RIGHT_CLICK_AIR) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && Objects.requireNonNull(event.getItem()).equals(exitQue)){

                for(ParkourBlueprint parkourBlueprint : parkourArenaList){

                    if(parkourBlueprint.active.contains(event.getPlayer())){

                        parkourBlueprint.active.remove(event.getPlayer());

                        event.getPlayer().getInventory().clear();
                        event.getPlayer().getInventory().setItem(0, gadgets);
                        event.getPlayer().getInventory().setItem(4, compass);
                        event.getPlayer().teleport(spawn);
                        parkourBlueprint.checkpoints.remove(event.getPlayer());

                        clocks.get(event.getPlayer()).cancel();
                        clocks.remove(event.getPlayer());
                        timeResult.remove(event.getPlayer());
                        timeResultInSeconds.remove(event.getPlayer());

                        ArrayList<Player> lobbyApplicants = new ArrayList<>();
                        lobbyApplicants.add(event.getPlayer());
                        scoreBoardMethods.ApplyLobbyBoard(lobbyApplicants);

                    }

                }

            }

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void Checkpoint(PlayerInteractEvent event){

        for(ParkourBlueprint arena : parkourArenaList){

            if(arena.active.contains(event.getPlayer())){

                if(event.getAction().equals(Action.PHYSICAL)){

                    if(event.getClickedBlock().getType().equals(Material.GOLD_PLATE)){

                        if(arena.active.contains(event.getPlayer())){

                            if(arena.checkpoints.get(event.getPlayer()).distance(event.getPlayer().getLocation()) > 2){

                                arena.checkpoints.replace(event.getPlayer(), event.getPlayer().getLocation());
                                event.getPlayer().sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou activated a &f&lCheckpoint&b!"));

                                CircleParticles circleParticles = new CircleParticles();
                                circleParticles.createParticleCircles(event.getClickedBlock().getLocation().add(0.5, 0, 0.5), 4, event.getPlayer());

                            }

                        }

                    }

                } else {

                    if(event.getMaterial().equals(Material.FEATHER)){

                        event.getPlayer().teleport(arena.checkpoints.get(event.getPlayer()));

                        event.setCancelled(true);

                    } else if(event.getMaterial().equals(Material.ARROW)) {

                        Player player = event.getPlayer();

                        player.teleport(arena.loc);
                        arena.checkpoints.replace(player, arena.loc);
                        clocks.get(player).cancel();
                        clocks.remove(player);
                        timeResult.remove(event.getPlayer());
                        timeResultInSeconds.remove(event.getPlayer());
                        BukkitTask parkourTimer = new Clock(player, arena).runTaskTimer(plugin, 0, 20);
                        clocks.put(player, parkourTimer);
                        timeResult.put(player, "");
                        timeResultInSeconds.put(player, 0);

                        event.setCancelled(true);


                    }

                }

            }

        }

    }

    public void SuspendFromGame(PlayerQuitEvent event){

        Player player = event.getPlayer();

        for(ParkourBlueprint parkourBlueprint : parkourArenaList){

            if(parkourBlueprint.active.contains(player)){

                parkourBlueprint.checkpoints.remove(player);
                clocks.get(player).cancel();
                clocks.remove(player);
                timeResult.remove(event.getPlayer());
                timeResultInSeconds.remove(event.getPlayer());

            }

        }

    }


}
