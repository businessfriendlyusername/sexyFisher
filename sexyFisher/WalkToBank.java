package scripts.sexyFisher;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.API.Fishing;
import scripts.API.Node;

import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public class WalkToBank extends Node {

    LinkedList<String> tools = new LinkedList<String>();

    public WalkToBank(Fishing.TOOLS fishingMethod, Fishing.LOCATION location){

        switch (location){
            case KARAMJA_DOCK:
                tools.add("Coins");
                break;
            default:
                break;
        }

        switch (fishingMethod){
            case FLY:
                tools.add("Feather");
                tools.add("Fly fishing rod");
                break;
            case LOBSTER:
                tools.add("Lobster pot");
                break;
            case SMALLNET:
                tools.add("Small fishing net");
                break;
            case BIGNET:
                tools.add("Big fishing net");
                break;
            case HARPOON:
                tools.add("Harpoon");
                break;
            case BAIT:
                tools.add("Fishing bait");
                tools.add("Fishing rod");
                break;
        }
    }

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

    private boolean inventoryCheck(){
        if(Inventory.isFull()){
            return false;
        }
        for(String tool : tools){
            if(Inventory.getCount(tool) == 0){
                System.out.println(tool);
                return false;
            }
            if((tool.equals("Feather") || tool.equals("Fishing bait")) && Inventory.getCount(tool) < 27)//we don't have enough bait/feathers for a full inventory
                return false;
            if(tool.equals("Coins") && Inventory.getCount(tool) < 60)//test for karamja dock fishing
                return false;
        }
        return true;
    }

    @Override
    public boolean validate() {
        return !inventoryCheck();
    }
}
