package scripts.sexyFisher;

import org.tribot.api.General;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import scripts.API.AntiBan;
import scripts.API.Fishing;
import scripts.sexyFisher.BarbarianVillageFlyFish.BarbarianVillageFlyFish;

import java.util.function.BooleanSupplier;

public class sexyFisher extends Script {


    @Override
    public void run() {
        BarbarianVillageFlyFish task = new BarbarianVillageFlyFish();
        task.run();
    }
}
