package scripts.sexyFisher.KaramjaDockFisher;

import org.tribot.api.General;
import org.tribot.api2007.types.RSTile;
import scripts.API.Fishing;
import scripts.API.Node;
import scripts.sexyFisher.Fish;
import scripts.sexyFisher.WalkToBank;
import scripts.sexyFisher.WalkToFish;

import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public class KaramjaDockFisher {
    public static final RSTile depositTile = new RSTile(3044, 3236, 0);


    private LinkedList<Node> nodes = new LinkedList<Node>();

    private BooleanSupplier stopCondition;

    public KaramjaDockFisher(Fishing.TOOLS tool,  BooleanSupplier stopCondition){

        Collections.addAll(nodes, new WalkToFish(tool, Fishing.LOCATION.KARAMJA_DOCK), new Fish(tool, Fishing.LOCATION.KARAMJA_DOCK),
                new WalkToBank(tool, Fishing.LOCATION.KARAMJA_DOCK), new WalkToPortSarimDepositBox(), new DepositBox(tool));
        this.stopCondition = stopCondition;//we will stop fishing when some condition is met
    }

    public KaramjaDockFisher(Fishing.TOOLS tool){

        Collections.addAll(nodes, new WalkToFish(tool, Fishing.LOCATION.KARAMJA_DOCK), new Fish(tool, Fishing.LOCATION.KARAMJA_DOCK),
                new WalkToBank(tool, Fishing.LOCATION.KARAMJA_DOCK), new WalkToPortSarimDepositBox(), new DepositBox(tool));
        this.stopCondition = new BooleanSupplier() {//we will never stop
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
