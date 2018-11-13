package scripts.sexyFisher;

import org.tribot.api.General;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import scripts.API.AntiBan;
import scripts.sexyFisher.BarbarianVillageFlyFish.BarbarianVillageFlyFish;

import java.util.function.BooleanSupplier;

public class sexyFisher extends Script {


    @Override
    public void run() {
        AntiBan.setPrintDebug(true);
        BarbarianVillageFlyFish task = new BarbarianVillageFlyFish();
        task.run();
    }
}
