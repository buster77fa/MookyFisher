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
public class TraverseToBank extends Task<ClientContext> {

    private Tile[] toBank;
    private TilePath to_Bank;
    private Paint paint;

    public TraverseToBank(ClientContext ctx, Paint paint, Tile[] toBank) {
        super(ctx);
        this.toBank = toBank;
        this.paint = paint;
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28
                && toBank[toBank.length - 1].distanceTo(ctx.players.local().tile()) > 6;
    }

    @Override
    public void execute() {
        paint.setStatus("Traversing to bank");
        to_Bank = ctx.movement.newTilePath(toBank);
        Condition.wait(() -> {
            to_Bank.traverse();
            return toBank[toBank.length - 1].distanceTo(ctx.players.local().tile()) < 6 && !ctx.players.local().inMotion();
        }, Random.nextInt(1400, 2200), 30);
        System.out.println("Arrived at the bank");
    }
}
