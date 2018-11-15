package scripts.sexyFisher;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.API.BelvisGE.Offer;
import scripts.API.BelvisGE.Offers;
import scripts.API.Fishing;
import scripts.API.Node;

import java.util.ArrayList;

public class Bank extends Node {
    private ArrayList<String> tools = new ArrayList<String>();

    public Bank(Fishing.TOOLS fishingMethod, Fishing.LOCATION location)
    {
        switch (location){
            case KARAMJA_DOCK:
                tools.add("Coins");
                break;
            default:
                break;
        }

        switch(fishingMethod){
            case FLY:
                tools.clear();
                tools.add("Feather");
                tools.add("Fly fishing rod");
            case BAIT:
                tools.clear();
                tools.add("Fishing bait");
                tools.add("Fishing rod");
            case BIGNET:
                tools.clear();
                tools.add("Big fishing net");
            case SMALLNET:
                tools.clear();
                tools.add("Small fishing net");
            case LOBSTER:
                tools.clear();
                tools.add("Lobster pot");
            case HARPOON:
                tools.clear();
                tools.add("Harpoon");
        }
    }


    @Override
    public void execute() {
        System.out.println("Banking");
        if(!Banking.openBank())//we were unable to open the bank
            return;
        if(Banking.isBankScreenOpen()) {
            Banking.depositAllExcept(tools.toArray(new String[0]));//deposit all except our tools
            for(String tool : tools){//iterate through the tools we need to see if we are missing anything
                if(Inventory.getCount(tool) == 0 || //we don't have the tool in our inventory
                        Inventory.getCount(tool) < 27 && (tool.equals("Feathers") || tool.equals("Fishing bait"))){//we don't have the feathers/bait in our inventory

                    RSItem[] missingTool = Banking.find(tool);
                    if(missingTool.length < 1 || missingTool[0].getStack() + Inventory.getCount(tool) < 27 &&
                            (tool.equals("Feathers") || tool.equals("Fishing bait"))){//we don't have the tool in our bank, buy a new one
//TODO add case for coins
                        int quantity = 1;
                        if(tool.equals("Feathers") || tool.equals("Fishing bait"))//if the tool missing is feathers or fishing bait, buy lots
                            quantity = 1000;
                        Offers.addOffer(new Offer(Offer.Type.BUY, quantity, -2, tool));//queue up a new instant buy GE offer for the missing tool


                    }
                }
            }
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
            if((tool.equals("Feather") || tool.equals("Fishing bait")) && Inventory.getCount(tool) < 27)//we don't have enough bait/feathers for a full inventory
                return false;
            if(tool.equals("Coins") && Inventory.getCount(tool) < 60)//test for karamja dock fishing
                return false;
        }
        return true;
    }

    @Override
    public boolean validate() {
        if(Banking.isInBank())
            return inventoryCheck();
        else
            return false;
    }
}
