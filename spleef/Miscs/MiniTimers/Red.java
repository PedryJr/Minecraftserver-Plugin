package spleef.Miscs.MiniTimers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import spleef.spluff;

import static spleef.spluff.*;

public class Red extends BukkitRunnable {

    Block transform;
    spluff plugin;
    BukkitTask action;

    public Red(spluff plugin, Block transform){

        this.plugin = plugin;
        this.transform = transform;

    }

    @Override
    public void run() {

        if(transform.getTypeId() == yellow.getTypeId() && transform.getData() == yellow.getData()){

            transform.setTypeId(red.getTypeId());
            transform.setData(red.getData());
            action = new Air(transform).runTaskLater(plugin, 15);

        }

    }
}
