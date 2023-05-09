package spleef.Miscs.MiniTimers.Gamestart;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import spleef.Blueprints.SpleefBlueprint;

public class CountDown extends BukkitRunnable {

    int count = 3;
    Player p1;
    Player p2;

    SpleefBlueprint arena;

    public CountDown(SpleefBlueprint arena){

        this.arena = arena;

        p1 = arena.active.get(0);
        p2 = arena.active.get(1);

    }

    @Override
    public void run() {

        switch(count){

            case 3:
                Countdown();
                break;
            case 2:
                Countdown();
                break;
            case 1:
                Countdown();
                break;
            case 0:
                Countdown();
                arena.Countdown = false;
                this.cancel();
                break;

        }

        count--;

    }

    void Countdown(){

        if(count != 0){

            p1.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&b&l" + count), 5, 10, 5);
            p2.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&b&l" + count), 5, 10, 5);

        }else{

            p1.sendTitle(ChatColor.translateAlternateColorCodes('&', "&f&lGo!"), "", 5, 10, 5);
            p2.sendTitle(ChatColor.translateAlternateColorCodes('&', "&f&lGo!"), "", 5, 10, 5);

        }

    }

}
