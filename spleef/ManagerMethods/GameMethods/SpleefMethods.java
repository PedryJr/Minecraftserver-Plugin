package spleef.ManagerMethods.GameMethods;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.ChunkSection;
import net.minecraft.server.v1_12_R1.IBlockData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import spleef.Blueprints.FfaBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.ManagerMethods.GeneralMethods.ScoreBoardMethods;
import spleef.Miscs.*;
import spleef.Miscs.MiniTimers.Gamestart.CountDown;
import spleef.spluff;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static spleef.ManagerMethods.GeneralMethods.StupidlyLargeMethods.renewArena;
import static spleef.spluff.*;

public class SpleefMethods {

    static spluff plugin;
    static ScoreBoardMethods scoreBoardMethods;
    public SpleefMethods(spluff plugin){

        this.plugin = plugin;
        scoreBoardMethods = new ScoreBoardMethods(plugin);

    }

    public static void setBlockInNativeWorld(World world, int x, int y, int z, int blockId, byte data, boolean applyPhysics) {

        net.minecraft.server.v1_12_R1.World nmsWorld = ((CraftWorld) world).getHandle();
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = net.minecraft.server.v1_12_R1.Block.getByCombinedId(blockId + (data << 12));
        nmsWorld.setTypeAndData(bp, ibd, applyPhysics ? 3 : 2);

    }

    public static void fastBlockPlacer(World world, int x, int y, int z, int blockId, byte data, boolean flag) {
        net.minecraft.server.v1_12_R1.World nmsWorld = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_12_R1.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
        IBlockData ibd = net.minecraft.server.v1_12_R1.Block.getByCombinedId(blockId + (data << 12));

        ChunkSection cs = nmsChunk.getSections()[y >> 4];
        if (cs == net.minecraft.server.v1_12_R1.Chunk.a) {
            cs = new ChunkSection(y >> 4 << 4, flag);
            nmsChunk.getSections()[y >> 4] = cs;
        }
        cs.setType(x & 15, y & 15, z & 15, ibd);
    }

    public static void playDeathSound(SpleefBlueprint arena){

        arena.active.get(0).playSound(arena.active.get(0).getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
        arena.active.get(1).playSound(arena.active.get(0).getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);

    }

    public static void regenerateArena(SpleefBlueprint arena){

        Block block = Bukkit.getWorld("world").getBlockAt(0, 256, 0);
        block.setType(Material.SNOW_BLOCK);

        byte data = block.getData();

        int z = 0;
        int x = 0;

        arena.blocks.clear();

        while(arena.sizex >= x){

            while(arena.sizez >= z){

                block = Bukkit.getWorld("world").getBlockAt(arena.locationx + x, arena.locationy, arena.locationz + z);
                arena.blocks.add(block);
                if(block.getType().equals(Material.AIR)){

                    setBlockInNativeWorld(Bukkit.getWorld("world"), arena.locationx + x, arena.locationy, arena.locationz + z, 80, data, false);
                    BukkitRunnable task = new ParticleRegenerate(block.getLocation());
                    task.runTaskLater(plugin, 3);
                } else if(block.getType().equals(red.getType())){

                    setBlockInNativeWorld(Bukkit.getWorld("world"), arena.locationx + x, arena.locationy, arena.locationz + z, 80, data, false);
                    BukkitRunnable task = new ParticleRegenerate(block.getLocation());
                    task.runTaskLater(plugin, 3);

                } else if(block.getType().equals(yellow.getType())){

                    setBlockInNativeWorld(Bukkit.getWorld("world"), arena.locationx + x, arena.locationy, arena.locationz + z, 80, data, false);
                    BukkitRunnable task = new ParticleRegenerate(block.getLocation());
                    task.runTaskLater(plugin, 3);

                } else if(block.getType().equals(lime.getType())){

                    setBlockInNativeWorld(Bukkit.getWorld("world"), arena.locationx + x, arena.locationy, arena.locationz + z, 80, data, false);
                    BukkitRunnable task = new ParticleRegenerate(block.getLocation());
                    task.runTaskLater(plugin, 3);

                }
                z++;

            }

            z = 0;
            x++;

        }

    }

    public static void regenerateArena(FfaBlueprint arena){

        int z = 0;
        int x = 0;

        while(arena.sizex >= x){

            while(arena.sizez >= z){

                Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(x + arena.locationx, arena.locationy, z + arena.locationz).setType(Material.SNOW_BLOCK);
                z++;

            }

            z = 0;
            x++;

        }

    }

    /** Reason for removal **/

    public static void respawnArena(SpleefBlueprint arena){

        regenerateArena(arena);

        arena.que.get(0).closeInventory();
        arena.que.get(0).getInventory().clear();
        arena.que.get(0).getInventory().setItem(0, shovel);
        arena.que.get(0).setGameMode(GameMode.SURVIVAL);
        arena.que.get(0).teleport(arena.s1);

        arena.que.get(1).closeInventory();
        arena.que.get(1).getInventory().clear();
        arena.que.get(1).getInventory().setItem(0, shovel);
        arena.que.get(1).setGameMode(GameMode.SURVIVAL);
        arena.que.get(1).teleport(arena.s2);


    }

    public static void matchPoint(SpleefBlueprint arena, Player victor, Player loser, int winnerScore, int loserScore) throws SQLException {

        scoreBoardMethods.ApplySpleefBoard(arena.que, arena, false);


        respawnArena(arena);

        if(winnerScore == arena.winPoints){

            ArrayList<Player> lobbyApplicants = new ArrayList<>();

            for(Player player : arena.spectators){

                player.teleport(spawn);
                player.getInventory().clear();
                player.getInventory().setItem(0, guide);
                player.getInventory().setItem(4, compass);
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.setGameMode(GameMode.ADVENTURE);
                player.setAllowFlight(false);
                victor.showPlayer(player);
                loser.showPlayer(player);
                lobbyApplicants.add(player);

            }

            if(arena.beginDecay != null){
                arena.beginDecay.cancel();
                if(arena.ms.beginDecay != null){
                    arena.ms.beginDecay.cancel();
                }
            }
            if(arena.beginMinispleef != null){
                arena.beginMinispleef.cancel();
                if(arena.ms.beginMinispleef != null){
                    arena.ms.beginMinispleef.cancel();
                }
            }

            renewArena(arena);

            lobbyApplicants.add(victor);
            lobbyApplicants.add(loser);

            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', victor.getPlayerListName() + " &ahas beaten " + loser.getPlayerListName() + " &awith a score of &f" + winnerScore + "&f/" + loserScore) + " !");

            victor.teleport(spawn);
            victor.getInventory().clear();
            victor.getInventory().setItem(0, gadgets);
            victor.getInventory().setItem(4, compass);
            victor.removePotionEffect(PotionEffectType.NIGHT_VISION);
            victor.setGameMode(GameMode.ADVENTURE);

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet getWinner = statement.executeQuery("select * from players");

            while(getWinner.next()){

                if(getWinner.getString("UUID").equals(victor.getUniqueId().toString())){

                    int wins = getWinner.getInt("wins");

                    wins++;

                    statement.executeUpdate("update players set wins = '" + wins + "' where UUID = '" + victor.getUniqueId() + "'");

                    break;

                }

            }

            scoreBoardMethods.ApplyLobbyBoard(lobbyApplicants);

            for(Player player : arena.spectators){

                player.setAllowFlight(false);
                player.teleport(spawn);
                player.setGameMode(GameMode.ADVENTURE);

            }

            loser.teleport(spawn);
            loser.getInventory().clear();
            loser.getInventory().setItem(4, compass);
            loser.getInventory().setItem(0, gadgets);
            loser.removePotionEffect(PotionEffectType.NIGHT_VISION);
            loser.setGameMode(GameMode.ADVENTURE);

            loser.playSound(loser.getLocation(), Sound.ENTITY_ITEM_BREAK, 10, 1);
            victor.playSound(victor.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);

            int money = 0;

            ResultSet lresult = statement.executeQuery("select * from players order by money DESC");

            while(lresult.next()){

                if(lresult.getString("UUID").equals(loser.getUniqueId().toString())){

                    money = Integer.parseInt(lresult.getString("money"));

                    if(money >= 4){

                        money--;
                        money--;
                        money--;
                        money--;

                        loser.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou lost &f&l4&c$ &bfor losing&f&l!"));

                        statement.executeUpdate("update players set money = '" + money + "' where UUID = '" + loser.getUniqueId() + "'");

                    }else{

                        victor.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent does not have enough money&f&l!"));
                        loser.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou dont have enough money left, your oponent did not get a reward&f&l!"));

                    }
                    break;

                }

            }

            ResultSet wresult = statement.executeQuery("select * from players order by money DESC");

            while(wresult.next()){

                if(wresult.getString("UUID").equals(victor.getUniqueId().toString())){

                    if(money >= 4){

                        money = Integer.parseInt(wresult.getString("money"));

                        money++;
                        money++;
                        money++;
                        money++;
                        money++;

                        victor.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou won &f&l5&c$ &bfor winning&f&l!"));

                        statement.executeUpdate("update players set money = '" + money + "' where UUID = '" + victor.getUniqueId() + "'");

                    }
                    break;

                }

            }

        }else{

            arena.active.get(0).playSound(arena.active.get(0).getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
            arena.active.get(1).playSound(arena.active.get(0).getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);

            if(!arena.minispleef){

                arena.Countdown = true;
                BukkitRunnable task = new CountDown(arena);
                task.runTaskTimer(plugin, 0, 20);

            }

            for(Player player : arena.spectators){

                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + victor.getName() + " &bhas scored a point!"));

            }

            victor.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + victor.getName() + " &bhas scored a point!"));
            loser.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + victor.getName() + " &bhas scored a point!"));

        }

    }
    public void startMatch(SpleefBlueprint arena){

        arena.beginDecay = new BeginDecay(plugin, arena).runTaskTimer(plugin, 0, 20);
        arena.beginMinispleef = new BeginMinispleef(plugin, arena).runTaskTimer(plugin, 0, 20);


        arena.que.get(0).addPotionEffect( new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 5));
        arena.active.add(arena.que.get(0));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gmenu reset all " + arena.active.get(0).getName());

        arena.que.get(1).addPotionEffect( new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 5));
        arena.active.add(arena.que.get(1));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gmenu reset all " + arena.active.get(1).getName());

        BukkitRunnable task = new CountDown(arena);
        task.runTaskTimer(plugin, 0, 20);

        respawnArena(arena);

    }

    public static void resetArena(SpleefBlueprint arena, Player resetter){

        if(arena.resetQue.contains(resetter)){

            for(Player player : arena.active){

                if(!arena.resetQue.contains(player)){

                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + resetter.getName() + " &bcancelled the &creset &brequest!"));

                }

            }

            arena.resetQue.remove(resetter);

            resetter.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + resetter.getName() + " &bcancelled the &creset &brequest!"));

            for(Player player : arena.spectators){

                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + resetter.getName() + " &bcancelled the &creset &brequest!"));

            }

        }else{

            arena.resetQue.add(resetter);

            if(arena.resetQue.size() == 2) {

                for(Player player : arena.resetQue){

                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + resetter.getName() + " &bhas &aaccepted &bthe reset request"));

                }

                for(Player player : arena.spectators){

                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + resetter.getName() + " &bhas &aaccepted &bthe reset request"));

                }

                if(arena.minispleef){

                    respawnArena(arena.ms);

                }else{

                    respawnArena(arena);

                }

                arena.resetQue.clear();

            }else{

                resetter.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + resetter.getName() + " &bwants to &ereset &bthe field!"));

                for(Player player : arena.active){

                    if(!arena.resetQue.contains(player)){

                        player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + resetter.getName() + " &bwants to &ereset &bthe field!"));

                    }

                }
                for(Player player : arena.spectators){

                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&f&l" + resetter.getName() + " &bwants to &ereset &bthe field!"));

                }

            }

        }

    }

    public void HidePlayersFrom(SpleefBlueprint arena){

        for(Player player : arena.spectators){

            arena.active.get(0).hidePlayer(player);
            arena.active.get(1).hidePlayer(player);

        }

    }


}
