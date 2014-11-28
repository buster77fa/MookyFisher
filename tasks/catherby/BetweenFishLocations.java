package scripts.MookyFisher.tasks.catherby;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.TilePath;
import scripts.MookyFisher.node.Task;

/**
 * @author Mookyman
 */
public class BetweenFishLocations extends Task<ClientContext> {

    private static final Tile[] toNewSpot = new Tile[]{ // Set an array of tiles between the fishing spot and the bank
        new Tile(2857, 3427, 0),
        new Tile(2853, 3426, 0),
        new Tile(2851, 3429, 0),
        new Tile(2848, 3431, 0)
    };
    private final int fishingSpotID = 1519;

    private final TilePath to_Left = ctx.movement.newTilePath(toNewSpot); // Make a path between the fishing spot and the bank from the above array
    private final TilePath to_Right = ctx.movement.newTilePath(toNewSpot).reverse(); // Make a path between the bank and the fishing spot from the above array

    public BetweenFishLocations(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.npcs.select().id(fishingSpotID).isEmpty()
                && ctx.inventory.select().count() < 28;
    }

    @Override
    public void execute() {
        if (toNewSpot[toNewSpot.length - 1].distanceTo(ctx.players.local().tile()) > toNewSpot[0].distanceTo(ctx.players.local().tile())) {
            Condition.wait(() -> {
                to_Left.traverse();
                return toNewSpot[toNewSpot.length - 1].distanceTo(ctx.players.local().tile()) < 5 && !ctx.players.local().inMotion();
            }, Random.nextInt(1400, 2200), 10);
        } else {
            Condition.wait(() -> {
                to_Right.traverse();
                return toNewSpot[0].distanceTo(ctx.players.local().tile()) < 5 && !ctx.players.local().inMotion();
            }, Random.nextInt(1400, 2200), 10);
        }
    }
    
    @Override
    public String toString() {
        return "Traversing between locations";
    }
}
