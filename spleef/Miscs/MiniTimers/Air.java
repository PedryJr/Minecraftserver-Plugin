package spleef.Miscs.MiniTimers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class Air extends BukkitRunnable {

    Block transform;

    public Air(Block transform){

        this.transform = transform;

    }

    @Override
    public void run() {

        transform.setType(Material.AIR);

    }
}
