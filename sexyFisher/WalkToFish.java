package scripts.sexyFisher;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.API.Fishing;
import scripts.API.Node;
import scripts.sexyFisher.Tools;

import javax.print.DocFlavor;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public class WalkToFish extends Node {

    WalkToFish(Tools.tools fishingMethod){
//        abc = antiban;

        switch (fishingMethod){
            case FLY:
                tools.clear();
                tools.add("Feather");
                tools.add("Fly fishing rod");
                break;
            case LOBSTER:
                tools.clear();
                tools.add("Lobster pot");
                break;
            case SMALLNET:
                tools.clear();
                tools.add("Small fishing net");
                break;
            case BIGNET:
                tools.clear();
                tools.add("Big fishing net");
                break;
            case HARPOON:
                tools.clear();
                tools.add("Harpoon");
                break;
            case BAIT:
                tools.clear();
                tools.add("Fishing bait");
                tools.add("Fishing rod");
                break;
        }
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
        }
        return true;
    }

    LinkedList<String> tools = new LinkedList<String>();

    @Override
    public void execute() {
        System.out.println("Walking to fish");
        WebWalking.walkTo(new RSTile(3109, 3433), new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Interfaces.get(595, 37) != null;//webwalking opened the world map
            }
        }, 500);
        if(Interfaces.get(595, 37) != null)
            Interfaces.get(595, 37).click("Close");
    }

    @Override
    public boolean validate() {
        if(!Fishing.isAtFish()){
            if(Inventory.isFull())
                return false;
            if(inventoryCheck())
                return true;
        }
        return false;
    }
}