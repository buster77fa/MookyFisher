/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts.MookyFisher.vars;

import org.powerbot.script.Area;
import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.Item;

/**
 * @author Mookyman
 */
public class Variables {

    public static class ITEM {

        public static final int[] LOBSTERS = {377},
                TROUTSALMON = {335, 331},
                SHRIMPANCHOVIES = {317, 321},
                EQUIPEMENT = {301, // Lobster pot
                    314, // Feather
                    309, // Fly Fishing Rod
                    303}; // Fishing net
    }

    public static class OBJECT {

        public static final int[] BANKBOOTH = {11748, 11744};
    }

    public static class COUNT {

        public static final int CAGE = 1,
                LURE = 2,
                NET = 1,
                HARPOON = 1,
                BAIT = 2;
    }

    public static class FISH {

        public static final int BARBVILLAGE = 1526,
                CATHERBY = 1519,
                FISHINGGUILD = 1510,
                DRAYNOR = 1525,
                SEERS = 1513;
    }

    public static class FISHINGSTYLE {

        public static final String CAGE = "Cage",
                LURE = "Lure",
                NET = "Net",
                HARPOON = "Harpoon",
                BAIT = "Bait";
    }

    public static class LOCATION {

        public static final Area GUILDDOCK = new Area(
                new Tile(2595, 3419, 0),
                new Tile(2606, 3427, 0)
        );
    }

    public static class BANK {

        public static final Bank.Amount ALL = org.powerbot.script.rt4.Bank.Amount.ALL;
    }

    public static class BOUNDS {

        public static final int[] FISH = {-28, 28, -8, 0, -28, 28};
    }

    public static class FILTER {

        public static final Filter<Item> RANDOMITEMS = new Filter<Item>() {
            @Override
            public boolean accept(Item i) {
                int invoItemID = i.id();

                for (int equipementID : ITEM.EQUIPEMENT) {
                    if (equipementID == invoItemID) {
                        return false;
                    }
                }
                return true;
            }
        };

        public static final Filter<Item> EQUIPEMENT = new Filter<Item>() {
            @Override
            public boolean accept(Item i) {
                int invoItemID = i.id();
                for (int equipementID : ITEM.EQUIPEMENT) {
                    if (equipementID == invoItemID) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static class TILEPATHS {

        public static final Tile[] DRAYNOR = new Tile[]{
            new Tile(3086, 3233, 0), // A tile closest to fishing 
            new Tile(3087, 3239, 0), //      ↑
            new Tile(3087, 3247, 0), //      ↓
            new Tile(3092, 3245, 0) // A tile inside the bank
        };

        public static final Tile[] EDGEVILLE = new Tile[]{
            new Tile(3107, 3433, 0), // A tile closest to fishing
            new Tile(3104, 3435, 0),
            new Tile(3099, 3435, 0), // Closer to the fishing spot than the bank
            new Tile(3095, 3438, 0), //      ↑
            new Tile(3092, 3444, 0), //      |
            new Tile(3091, 3448, 0), //      |
            new Tile(3089, 3452, 0), //      |
            new Tile(3088, 3458, 0), //      |
            new Tile(3087, 3462, 0), //      |
            new Tile(3081, 3466, 0), //      |
            new Tile(3080, 3470, 0), //      |
            new Tile(3080, 3476, 0), //      |
            new Tile(3080, 3482, 0), //      ↓
            new Tile(3085, 3485, 0), // Closer to the bank than the fishing spot
            new Tile(3089, 3489, 0),
            new Tile(3093, 3491, 0),// A tile inside the bank
        };

        public static final Tile[] SEERS = new Tile[]{
            new Tile(2724, 3530, 0), // A tile closest to fishing 
            new Tile(2735, 3521, 0), //      ↑
            new Tile(2740, 3509, 0), //      |
            new Tile(2737, 3496, 0), //      ↓
            new Tile(2727, 3493, 0) // A tile inside the bank
        };

        public static final Tile[] CATHERBY = new Tile[]{
            new Tile(2857, 3427, 0), // A tile closest to fishing
            new Tile(2853, 3426, 0),
            new Tile(2851, 3429, 0), // Closer to the fishing spot than the bank
            new Tile(2848, 3431, 0), //      ↑
            new Tile(2844, 3431, 0), //      |
            new Tile(2839, 3433, 0), //      |
            new Tile(2833, 3436, 0), //      |
            new Tile(2828, 3436, 0), //      |
            new Tile(2822, 3436, 0), //      ↓
            new Tile(2818, 3436, 0), // Closer to the bank than the fishing spot
            new Tile(2813, 3436, 0),
            new Tile(2809, 3439, 0) // A tile inside the bank
        };

        public static final Tile[] FISHINGGUILD = new Tile[]{
            new Tile(2600, 3421, 0), // A tile closest to fishing 
            new Tile(2595, 3417, 0),
            new Tile(2586, 3419, 0), // A tile inside the bank
        };
    }

    public static class EXP {

        public static final int[] XP = {0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107,
            2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
            16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983,
            75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742,
            302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
            1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
            3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
            11805606, 13034431, 14391160, 15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069,
            31777943, 35085654, 38737661, 42769801, 47221641, 52136869, 57563718, 63555443, 70170840, 77474828,
            85539082, 94442737, 104273167};
    }

}
