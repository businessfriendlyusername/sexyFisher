package scripts.sexyFisher;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import scripts.API.Node;

import java.util.ArrayList;
import java.util.LinkedList;

public class Bank extends Node {

    public Bank(Tools.tools fishingMethod)
    {
//        abc = antiban;

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

    ArrayList<String> tools = new ArrayList<String>();

    @Override
    public void execute() {
        System.out.println("Banking");
        if(!Banking.openBank())//we were unable to open the bank
            return;
        if(Banking.isBankScreenOpen())
            Banking.depositAllExcept("Fly fishing rod", "Feather");
    }

    @Override
    public boolean validate() {
        if(Banking.isInBank())
            if(Inventory.isFull())
                return true;//inventory is full
//            for(String tool : tools){
//                if(Inventory.getCount(tool) == 0)
//                    return true;//we are missing a tool
//            }
        return false;
    }
}
