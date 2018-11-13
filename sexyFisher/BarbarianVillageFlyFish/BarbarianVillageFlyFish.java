package scripts.sexyFisher.BarbarianVillageFlyFish;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import scripts.API.Node;
import scripts.sexyFisher.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public class BarbarianVillageFlyFish{

    private static LinkedList<Node> nodes = new LinkedList<Node>();

    private BooleanSupplier stopCondition;

    public BarbarianVillageFlyFish(BooleanSupplier stopCondition){
        Collections.addAll(nodes, new WalkToFish(Tools.tools.FLY), new Fish(Tools.tools.FLY, -1),
                new WalkToBank(), new Bank(Tools.tools.FLY));
    }

    public BarbarianVillageFlyFish(){
        Collections.addAll(nodes, new WalkToFish(Tools.tools.FLY), new Fish(Tools.tools.FLY, -1),
                new WalkToBank(), new Bank(Tools.tools.FLY));
    }

    public void run(){
        while(Inventory.getCount("Feather") != 0) {
            for (final Node node : nodes) {
                if (node.validate()) {
                    General.sleep(300, 700);
                    node.execute();
                }

            }
        }
    }
}
