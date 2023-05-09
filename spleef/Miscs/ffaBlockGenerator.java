package spleef.Miscs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ffaBlockGenerator extends BukkitRunnable {

    Block blockToRegenerate;
    public ffaBlockGenerator(Block blockToRegenerate){

        this.blockToRegenerate = blockToRegenerate;

    }

    @Override
    public void run() {

        blockToRegenerate.setType(Material.SNOW_BLOCK);

        Objects.requireNonNull(Bukkit.getWorld("world")).spawnParticle(Particle.CRIT_MAGIC, blockToRegenerate.getLocation().add(0, 0.5, 0), 2);

    }

}
