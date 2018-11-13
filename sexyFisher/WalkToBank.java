package scripts.sexyFisher;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.API.Node;

import java.util.function.BooleanSupplier;

public class WalkToBank extends Node {

//    WalkToBank(ABCUtil a){
//        abc = a;
//    }

    @Override
    public void execute() {
        System.out.println("Walking to Bank");
        WebWalking.walkToBank(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Interfaces.get(595, 37) != null;//if we accidentaly open the world map
            }
        }, 500);
            if(Interfaces.get(595, 37) != null)
                Interfaces.get(595, 37).click("Close");
    }

    @Override
    public boolean validate() {
        return (Inventory.isFull() && !Banking.isInBank() || Inventory.getCount("Fly fishing rod") == 0
                || Inventory.getCount("Feather") < 27);
    }
}
