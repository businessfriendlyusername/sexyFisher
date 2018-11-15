package scripts.sexyFisher;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.API.AntiBan;
import scripts.API.Fishing;
import scripts.API.Node;

import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public class Fish extends Node {

    private RSTile fishingLocation;
    private String fishingOption;
    private LinkedList<String> tools = new LinkedList<String>();
    private int fishingAnimation;
    private String fishingSpotName;


    public Fish(Fishing.TOOLS fishingMethod, Fishing.LOCATION location){

        switch (fishingMethod){
            case FLY:
                fishingSpotName = "Rod Fishing spot";
                fishingAnimation = Fishing.ANIMATION.ROD.asInt();
                fishingOption = "Lure";
                tools.clear();
                tools.add("Feather");
                tools.add("Fly fishing rod");
                break;
            case LOBSTER:
                fishingSpotName = "IDKF";//TODO ~~~~~~~~~~~~~~~~FIND THIS~~~~~~~~~~~~~~~~~
                fishingAnimation = Fishing.ANIMATION.POT.asInt();
                fishingOption = "Cage";
                tools.clear();
                tools.add("Lobster pot");
                break;
            case SMALLNET:
                fishingSpotName = "Fishing spot";
                fishingAnimation = Fishing.ANIMATION.SMALL.asInt();
                fishingOption = "Net";
                tools.clear();
                tools.add("Small fishing net");
                break;
            case BIGNET:
                fishingSpotName = "IDFK";//TODO ~~~~~~~~~~~~~~~~FIND THIS~~~~~~~~~~~~~~~~~
                fishingAnimation = Fishing.ANIMATION.BIG.asInt();
                fishingOption = "Net";
                tools.clear();
                tools.add("Big fishing net");
                break;
            case HARPOON:
                fishingSpotName = "IDFK";//TODO ~~~~~~~~~~~~~~~~FIND THIS~~~~~~~~~~~~~~~~~
                fishingAnimation = Fishing.ANIMATION.HARPOON.asInt();
                fishingOption = "Harpoon";
                tools.clear();
                tools.add("Harpoon");
                break;
            case BAIT:
                fishingSpotName = "Rod Fishing spot";
                fishingAnimation = Fishing.ANIMATION.ROD.asInt();
                fishingOption = "Bait";
                tools.clear();
                tools.add("Fishing bait");
                tools.add("Fishing rod");
                break;
        }
        switch (location){
            case LUMBRIDGE_SWAMP:
                this.fishingLocation = Fishing.LOCATION.LUMBRIDGE_SWAMP.getAsRSTile();
                break;
            case KARAMJA_DOCK:
                this.fishingLocation = Fishing.LOCATION.KARAMJA_DOCK.getAsRSTile();
                break;
            case BARBARIAN_VILLAGE:
                this.fishingLocation = Fishing.LOCATION.BARBARIAN_VILLAGE.getAsRSTile();
                break;
        }

    }

    private boolean isFishing(){
        return Player.getAnimation() == fishingAnimation;
    }

    private boolean fish(){
        final long timeout = System.currentTimeMillis() + General.random(150000, 250000);

        while (isFishing() && System.currentTimeMillis() < timeout && Inventory.getAll().length < 27 ){
            General.sleep(100, 150);
            AntiBan.timedActions();
        }

        while (isFishing() && System.currentTimeMillis() < timeout){
            General.sleep(10, 30);
        }

        AntiBan.sleepReactionTime();

        if(!inventoryCheck())
            return true;

        RSNPC[] fishing_spots = NPCs.findNearest(fishingSpotName);
        if (fishing_spots.length < 1)
            return false;//no fishing spots found, cannot fish

        RSNPC fishingSpot = (RSNPC) AntiBan.selectNextTarget(fishing_spots);
        if (fishingSpot == null)
            return false;

        if (!DynamicClicking.clickRSNPC(fishingSpot, fishingOption)) {//we could not click the fishing spot
            Camera.turnToTile(fishingSpot);
            return false;
        }

        Timing.waitCondition(new BooleanSupplier() {//we just clicked the fishing spot, wait a second while we path to it
            @Override
            public boolean getAsBoolean() {
                General.sleep(100);
                return !isFishing();
            }
        }, General.random(1000, 1200));

        if (Timing.waitCondition(new BooleanSupplier() {
            // Now let's wait until we are fishing.
            @Override
            public boolean getAsBoolean() {
                General.sleep(100);
                return isFishing();
            }
        }, General.random(8000, 9000))) {
            return true;
        }
        return false;//we failed to fish
    }

    private boolean inventoryCheck(){
        if(Inventory.isFull()){
            return false;
        }
        for(String tool : tools){
            if(Inventory.getCount(tool) == 0)
                return false;
        }
        return true;
    }

    @Override
    public void execute() {
        System.out.println("Fishing");
        fish();
    }

    @Override
    public boolean validate() {
        if(Fishing.isAtFish(fishingLocation, fishingSpotName))
            return inventoryCheck();

    return false;
    }
}