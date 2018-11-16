package scripts.sexyFisher.KaramjaDockFisher;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.API.Node;

import java.util.function.BooleanSupplier;

public class WalkToPortSarimDepositBox extends Node {


    @Override
    public void execute() {
        WebWalking.walkTo(KaramjaDockFisher.depositTile, new BooleanSupplier() {
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
        return Inventory.isFull() && Player.getPosition().distanceTo(KaramjaDockFisher.depositTile) >= 10;
    }
}
