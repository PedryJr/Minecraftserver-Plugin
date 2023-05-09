package spleef.Blueprints;

import org.bukkit.entity.Player;

public class QueBlueprint {

    public Player challanger;
    public Player challangeTarget;
    public String arenaName;

    public QueBlueprint(Player challanger, Player challangeTarget, String arenaName){

        this.challanger = challanger;
        this.challangeTarget = challangeTarget;
        this.arenaName = arenaName;

    }

}
