package spleef.Miscs.MiniTimers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import spleef.Events.Events;

import static spleef.spluff.gadgetdefined;
import static spleef.spluff.gadgets;

public class Gadgets extends BukkitRunnable {

    Player n;

    public Gadgets(Player n){

        this.n = n;

    }
    @Override
    public void run() {

        gadgets = n.getInventory().getItem(0);
        gadgetdefined = true;

    }
}
