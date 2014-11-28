/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts.MookyFisher.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.TilePath;
import scripts.MookyFisher.node.Task;
import scripts.MookyFisher.utils.Paint;

/**
 * @author Mookyman
 */
public class TraverseFromBank extends Task<ClientContext> {

    private Tile[] toBank;
    private TilePath from_Bank;
    private int equipementCount;
    private Paint paint;

    public TraverseFromBank(ClientContext ctx, Paint paint, Tile[] toBank, int equipementCount) {
        super(ctx);
        this.toBank = toBank;
        this.equipementCount = equipementCount;
        this.paint = paint;
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == equipementCount
                && toBank[toBank.length - 1].distanceTo(ctx.players.local().tile()) < 6;
    }

    @Override
    public void execute() {
        paint.setStatus("Traversing from bank");
        from_Bank = ctx.movement.newTilePath(toBank).reverse();
        Condition.wait(() -> {
            from_Bank.traverse();
            return toBank[0].distanceTo(ctx.players.local().tile()) < 6 && !ctx.players.local().inMotion();
        }, Random.nextInt(1400, 2200), 30);
        System.out.println("Arrived at the fishing location");
    }
}
