package spleef.Miscs.MiniTimers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import spleef.spluff;

import static spleef.spluff.lime;
import static spleef.spluff.yellow;

public class Lime extends BukkitRunnable {

    Block transform;
    BukkitTask action;
    spluff plugin;

    public Lime(spluff plugin, Block transform){

        this.plugin = plugin;
        this.transform = transform;

    }

    @Override
    public void run() {

        if(transform.getType().equals(Material.SNOW_BLOCK)){

            transform.setTypeId(lime.getTypeId());
            transform.setData(lime.getData());
            action = new Yellow(plugin, transform).runTaskLater(plugin, 3);

        }

    }
}
