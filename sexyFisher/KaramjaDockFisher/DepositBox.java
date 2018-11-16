package scripts.sexyFisher.KaramjaDockFisher;


import obf.G;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
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
        if(!depositBox[0].click("Deposit Bank deposit box")) {
            Camera.turnToTile(depositBox[0]);
            return false;
        }
        Timing.waitCondition(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Interfaces.get(192, 1, 11) != null;
            }
        }, General.random(3000, 5000));
        return Interfaces.get(192, 1, 11) != null;

    }

    private void depositAllExceptCoinsAndTool(){
        RSItem[] inventory = Inventory.find("Raw lobster");
        int index;
        if(inventory.length != 0) {
            index = inventory[0].getIndex();
            RSInterface deposit = Interfaces.get(192, 2, index);
            if (deposit == null)
                return;
            else {
                deposit.click("Deposit-All");
            }
            General.sleep(400, 900);
        }


        inventory = Inventory.find("Raw swordfish");
        if(inventory.length != 0) {
            index = inventory[0].getIndex();
            RSInterface deposit = Interfaces.get(192, 2, index);
            if (deposit == null)
                return;
            else {
                deposit.click("Deposit-All");
            }
            General.sleep(400, 900);
        }


        inventory = Inventory.find("Raw tuna");
        if(inventory.length != 0) {
            index = inventory[0].getIndex();
            RSInterface deposit = Interfaces.get(192, 2, index);
            if (deposit == null)
                return;
            else {
                deposit.click("Deposit-All");
            }
            General.sleep(400, 900);
        }
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
            General.sleep(400, 1200);
            depositAllExceptCoinsAndTool();
            General.sleep(200, 700);
            closeDepositBox();
            General.sleep(800, 1600);
        }
    }

    @Override
    public boolean validate() {
        return Player.getPosition().distanceTo(KaramjaDockFisher.depositTile) < 10 && Inventory.isFull();
    }
}
