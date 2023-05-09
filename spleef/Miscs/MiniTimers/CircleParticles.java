package spleef.Miscs.MiniTimers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CircleParticles {

    public void createParticleCircles(Location location, int numberOfCircles, Player player) {
        double startRadius = 0.5;
        double radiusIncrement = 0.3;
        final double y = location.getY() + 0.4;
        boolean t = false;
        for (int i = 0; i < numberOfCircles; i++) {

            if(i == numberOfCircles-1){

                t = true;

            }

            final double currentRadius = startRadius + (i * radiusIncrement);
            boolean finalT = t;
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("Spleef.1.12"), () -> {

                for (double angle = 0; angle < 360; angle += 10) {
                    double x = currentRadius * Math.cos(Math.toRadians(angle));
                    double z = currentRadius * Math.sin(Math.toRadians(angle));

                    if(angle == 100 && finalT){
                        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 10, 1);
                    }
                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("Spleef.1.12"), () -> {

                            location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location.getX() + x, y, location.getZ() + z, 0, 0, 0, 0, 0);

                }, (int) Math.round(angle/100));
                }
            }, i * 1);
        }
    }

}