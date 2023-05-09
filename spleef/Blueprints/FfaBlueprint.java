package spleef.Blueprints;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FfaBlueprint {

    public int locationx;
    public int locationy;
    public int locationz;
    public int sizex;
    public int sizez;
    public Location loc;
    public Location s;
    public String name;
    public ItemStack icon;
    public ArrayList<Player> que = new ArrayList<>();
    public ArrayList<Player> active = new ArrayList<>();
    public ArrayList<Player> spectator = new ArrayList<>();
    public boolean allowBreak = true;

    public FfaBlueprint(int locationx, int locationy, int locationz, int sizex, int sizez, String name, ItemStack icon){

        this.locationx = locationx;
        this.locationy = locationy;
        this.locationz = locationz;

        loc = new Location(Bukkit.getWorld("world"), locationx, locationy, locationz);
        s = loc.add(32, 10 ,32);

        this.sizex = sizex;
        this.sizez = sizez;
        this.name = name;
        this.icon = icon;

    }

}
