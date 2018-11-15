package scripts.sexyFisher.BarbarianVillageFlyFish;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import scripts.API.Fishing;
import scripts.API.Node;
import scripts.sexyFisher.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public class BarbarianVillageFlyFish{

    private static LinkedList<Node> nodes = new LinkedList<Node>();

    private BooleanSupplier stopCondition;

    public BarbarianVillageFlyFish(BooleanSupplier stopCondition){
        Collections.addAll(nodes, new WalkToFish(Fishing.TOOLS.FLY, Fishing.LOCATION.BARBARIAN_VILLAGE), new Fish(Fishing.TOOLS.FLY, Fishing.LOCATION.BARBARIAN_VILLAGE),
                new WalkToBank(Fishing.TOOLS.FLY, Fishing.LOCATION.BARBARIAN_VILLAGE), new Bank(Fishing.TOOLS.FLY, Fishing.LOCATION.BARBARIAN_VILLAGE));
        this.stopCondition = stopCondition;//we will stop fishing when some condition is met
    }

    public BarbarianVillageFlyFish(){
        Collections.addAll(nodes, new WalkToFish(Fishing.TOOLS.FLY, Fishing.LOCATION.BARBARIAN_VILLAGE), new Fish(Fishing.TOOLS.FLY, Fishing.LOCATION.BARBARIAN_VILLAGE),
                new WalkToBank(Fishing.TOOLS.FLY, Fishing.LOCATION.BARBARIAN_VILLAGE), new Bank(Fishing.TOOLS.FLY, Fishing.LOCATION.BARBARIAN_VILLAGE));
        this.stopCondition = new BooleanSupplier() {//we will never stop fishing
            @Override
            public boolean getAsBoolean() {
                return true;
            }
        };
    }

    public void run(){
        while(stopCondition.getAsBoolean()) {
            for (final Node node : nodes) {
                if (node.validate()) {
                    General.sleep(300, 700);
                    node.execute();
                }

            }
        }
    }
}
