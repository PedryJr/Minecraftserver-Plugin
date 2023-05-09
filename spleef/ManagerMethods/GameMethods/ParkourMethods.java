package spleef.ManagerMethods.GameMethods;

import spleef.Blueprints.ParkourBlueprint;
import spleef.ManagerMethods.GeneralMethods.ScoreBoardMethods;
import spleef.ManagerMethods.GeneralMethods.StupidlyLargeMethods;
import spleef.spluff;

public class ParkourMethods {

    ScoreBoardMethods scoreBoardMethods;

    spluff plugin;

    public ParkourMethods(spluff plugin) {

        scoreBoardMethods = new ScoreBoardMethods(plugin);

        this.plugin = plugin;

    }

}
