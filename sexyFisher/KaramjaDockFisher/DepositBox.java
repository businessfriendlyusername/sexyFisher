package scripts.sexyFisher.KaramjaDockFisher;


import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.API.Fishing;
import scripts.API.Node;

import java.util.function.BooleanSupplier;

public class DepositBox extends Node {
    private String tool;

    public DepositBox(Fishing.TOOLS tool){
        if(tool == Fishing.TOOLS.HARPOON)
            this.tool = "Harpoon";
        if(tool == Fishing.TOOLS.LOBSTER)
            this.tool = "Lobster pot";
    }

    private boolean isDepositBoxOpen(){
        return Interfaces.get(192, 1, 11) != null;
    }

    private boolean openDepositBox(){
        RSObject[] depositBox = Objects.findNearest(10, "Bank deposit box");
        if(depositBox. length == 0)
            return false;
        depositBox[0].click("Deposit Bank deposit box");
        Timing.waitCondition(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Interfaces.get(192, 1, 11) != null;
            }
        }, General.random(3000, 5000));
        return Interfaces.get(192, 1, 11) != null;
    }

    private boolean depositAllExceptCoinsAndTool(){
        RSItem[] inventory = Inventory.find("Raw lobster");
        int index;
        if(inventory.length != 0)
            index = inventory[0].getIndex();
        else {
            inventory = Inventory.find("Raw swordfish");
            if(inventory.length != 0)
                index = inventory[0].getIndex();
            else
                return false;
        }
        RSInterface deposit = Interfaces.get(192, 2, index);
        if(deposit == null)
            return false;
        else{
            deposit.click("Deposit-All");
        }
        return true;
    }

    private void closeDepositBox(){
        RSInterface closeButton = Interfaces.get(192, 1, 11);
        if(closeButton == null)
            return;
        closeButton.click("Close");
        Timing.waitCondition(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Interfaces.get(192, 1, 11) == null;
            }
        }, General.random(500, 2000));
    }


    @Override
    public void execute() {
        if(!isDepositBoxOpen()){
            openDepositBox();
            if(depositAllExceptCoinsAndTool())
                closeDepositBox();
        }
    }

    @Override
    public boolean validate() {
        return Player.getPosition().distanceTo(KaramjaDockFisher.depositTile) < 10 && Inventory.isFull();
    }
}
