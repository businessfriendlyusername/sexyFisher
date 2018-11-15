package scripts.sexyFisher;

import org.tribot.api.General;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import scripts.API.AntiBan;
import scripts.API.Fishing;
import scripts.sexyFisher.BarbarianVillageFlyFish.BarbarianVillageFlyFish;

import java.util.function.BooleanSupplier;

public class sexyFisher extends Script {


    @Override
    public void run() {
        while(true) {
            System.out.println("Walking to fish");//2924 3178 //karamja dock ~~ 3026 3217 port sarim dock
            WebWalking.walkTo(Fishing.LOCATION.KARAMJA_DOCK.getAsRSTile(), new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return Interfaces.get(595, 37) != null;//webwalking opened the world map
                }
            }, 500);
            if (Interfaces.get(595, 37) != null)
                Interfaces.get(595, 37).click("Close");
        }
    }
}
