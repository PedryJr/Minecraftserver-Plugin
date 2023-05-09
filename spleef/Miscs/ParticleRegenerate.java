package spleef.Miscs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleRegenerate extends BukkitRunnable {

    Location loc;

    public ParticleRegenerate(Location loc){

        this.loc = loc;

    }

    @Override
    public void run() {

        Bukkit.getWorld("world").spawnParticle(Particle.FIREWORKS_SPARK, loc.add(0.5, 0.5, 0.5), 2);

    }
}
