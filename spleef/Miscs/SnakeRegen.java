package spleef.Miscs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import spleef.Blueprints.SpleefBlueprint;
import spleef.spluff;

import java.util.ArrayList;

import static spleef.ManagerMethods.GameMethods.SpleefMethods.setBlockInNativeWorld;
import static spleef.spluff.*;

public class SnakeRegen extends BukkitRunnable {

    spluff plugin;
    ArrayList<Block> snake;
    SpleefBlueprint arena;


    public SnakeRegen(ArrayList<Block> snake, SpleefBlueprint arena, spluff plugin){

        this.snake = snake;
        this.arena = arena;
        this.plugin = plugin;

    }

    @Override
    public void run() {

        for(int i = -1; i < 13; i++){

            if(snake.isEmpty()){

                Block block = Bukkit.getWorld("world").getBlockAt(0, 256, 0);
                block.setType(Material.SNOW_BLOCK);

                byte data = block.getData();

                int z = 0;
                int x = 0;

                arena.blocks.clear();

                i=13;
                while(arena.sizex >= x){

                    while(arena.sizez >= z){

                        block = Bukkit.getWorld("world").getBlockAt(arena.locationx + x, arena.locationy, arena.locationz + z);

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
                        arena.blocks.add(block);
                        z++;

                    }

                    z = 0;
                    x++;

                }
                this.cancel();

            }

            if(i == -1){

                if(!new Location(Bukkit.getWorld("world"),  arena.s1.getX(), arena.s1.getY()-1, arena.s1.getZ()).getBlock().getType().equals(Material.SNOW_BLOCK)){

                    Location loc1 = new Location(Bukkit.getWorld("world"),  arena.s1.getX(), arena.s1.getY()-1, arena.s1.getZ());
                    loc1.getBlock().setType(Material.SNOW_BLOCK);
                    arena.blocks.add(loc1.getBlock());

                }

                if(!new Location(Bukkit.getWorld("world"),  arena.s2.getX(), arena.s2.getY()-1, arena.s2.getZ()).getBlock().getType().equals(Material.SNOW_BLOCK)){

                    Location loc2 = new Location(Bukkit.getWorld("world"),  arena.s2.getX(), arena.s2.getY()-1, arena.s2.getZ());
                    loc2.getBlock().setType(Material.SNOW_BLOCK);
                    arena.blocks.add(loc2.getBlock());

                }

            }else{

                if(snake.get(0).getType().equals(Material.SNOW_BLOCK)){

                    snake.remove(0);

                }else{

                    BukkitRunnable task = new ParticleRegenerate(snake.get(0).getLocation());
                    task.runTaskLater(plugin, 1);
                    arena.blocks.add(snake.get(0));
                    snake.get(0).setType(Material.SNOW_BLOCK);
                    snake.remove(0);

                }

            }

        }

    }
}
