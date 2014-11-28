/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts.MookyFisher.tasks;

import java.awt.Point;
import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Item;
import scripts.MookyFisher.node.Task;
import scripts.MookyFisher.utils.Paint;
import scripts.MookyFisher.vars.Variables;

/**
 * @author Mookyman
 */
public class Drop extends Task<ClientContext> {

    private int[] fishIDs;
    private Paint paint;

    public Drop(ClientContext ctx, Paint paint, int[] fishIDs) {
        super(ctx);
        this.fishIDs = fishIDs;
        this.paint = paint;
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28;
    }

    @Override
    public void execute() {
        Point invo = new Point(Random.nextInt(630, 660), Random.nextInt(170, 200));
        if (!ctx.game.tab(Game.Tab.INVENTORY)) {
            ctx.input.click(invo, true);

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.game.tab(Game.Tab.INVENTORY);
                }
            });
        }
        do {
            for (Item i : ctx.inventory.select().select(Variables.FILTER.RANDOMITEMS)) { // For each task
                i.interact("Drop");
            }
            Condition.sleep(Random.nextInt(500, 1400));
        } while (ctx.inventory.select().id(fishIDs).count() > 0);
    }
}
