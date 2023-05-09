package spleef.Miscs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import spleef.Blueprints.FfaBlueprint;

import java.util.ArrayList;

public class Countdown extends BukkitRunnable {

    ArrayList<Player> players;
    FfaBlueprint arena;

    public Countdown(ArrayList<Player> players, FfaBlueprint arena){

        this.players = players;
        this.arena = arena;

        arena.allowBreak = false;

    }
    int i = 3;

    @Override
    public void run() {

        if(i >= 0) {

            for(Player player : players){

                if(i == 0){

                    arena.allowBreak = true;

                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&b&lGO"), "", 5, 10, 5);

                    cancel();

                }else{

                    arena.allowBreak = false;

                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&b&l" + i), ChatColor.translateAlternateColorCodes('&', "&f&lStarting in.."), 5, 10, 5);

                }

            }

        }

        if(i == 0){

            arena.allowBreak = true;

            cancel();

        }

        i--;

    }

}
