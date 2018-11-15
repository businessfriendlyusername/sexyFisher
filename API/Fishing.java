package scripts.API;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

public class Fishing {
//~~~~~~~~~~~~~~~~~~Fishing Tools~~~~~~~~~~~~~~~~~~~~~
    public enum TOOLS {
        FLY,
        BAIT,
        SMALLNET,
        BIGNET,
        HARPOON,
        LOBSTER
    }
//~~~~~~~~~~~~~~~~Fishing Animations~~~~~~~~~~~~~~~~~
    public enum ANIMATION{//TODO fill out animation vals

        SMALL(621),
        BIG(0),
        ROD(623),
        POT(0),
        HARPOON(0);

        private int animationAsInt;

        public int asInt(){
            return animationAsInt;
        }

        ANIMATION(int num){
            this.animationAsInt = num;
    }
}
//~~~~~~~~~~~~~~~~~Fishing Locations~~~~~~~~~~~~~~~~~
    public enum LOCATION{
        LUMBRIDGE_SWAMP(new RSTile(3241, 3149, 0)),
        KARAMJA_DOCK(new RSTile(2924, 3178, 0)),
        BARBARIAN_VILLAGE(new RSTile(3109, 3433, 0));

        private RSTile location;

        public RSTile getAsRSTile(){
            return location;
        }

        LOCATION(RSTile tile){
            location = tile;
        }
}

    public static boolean isAtFish(RSTile location, String fishingSpotName){
        if(Player.getPosition().distanceTo(location) > 100)
            return false;
        RSNPC[] fishingSpots = NPCs.findNearest(fishingSpotName);
        if(fishingSpots.length < 1)
            return false;
        else
            return true;
    }
}
