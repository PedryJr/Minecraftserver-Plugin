package spleef.Miscs.MiniTimers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import spleef.spluff;

import static spleef.spluff.lime;
import static spleef.spluff.yellow;

public class Yellow extends BukkitRunnable {

    Block transform;
    BukkitTask action;
    spluff plugin;

    public Yellow(spluff plugin, Block transform){

        this.plugin = plugin;
        this.transform = transform;

    }

    @Override
    public void run() {

        if(transform.getTypeId() == lime.getTypeId() && transform.getData() == lime.getData()){

            transform.setTypeId(yellow.getTypeId());
            transform.setData(yellow.getData());
            action = new Red(plugin, transform).runTaskLater(plugin, 7);

        }

    }
}
