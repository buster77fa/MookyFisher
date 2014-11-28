package scripts.MookyFisher.tasks;

import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import scripts.MookyFisher.node.Task;
import scripts.MookyFisher.vars.Variables;
import scripts.MookyFisher.utils.Paint;

/**
 * @author Mookyman
 */
public class Bank extends Task<ClientContext> {

    private int equipementCount;
    private Paint paint;

    public Bank(ClientContext ctx, Paint paint, int equipementCount) {
        super(ctx);
        this.equipementCount = equipementCount;
        this.paint = paint;
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28
                && !ctx.objects.select().id(Variables.OBJECT.BANKBOOTH).within(6.0).isEmpty()
                && !ctx.players.local().inMotion();
    }

    @Override
    public void execute() {
        paint.setStatus("Banking");
        GameObject bankBooth = ctx.objects.select().id(Variables.OBJECT.BANKBOOTH).nearest().poll();

        bankBooth.interact("Bank", "Bank booth");

        Condition.wait(() -> ctx.bank.opened());

        long startTime = System.currentTimeMillis();
        while ((ctx.inventory.select().count() != equipementCount) && ((System.currentTimeMillis() - startTime) < 20000)) {
            int itemToBankID = ctx.inventory.select().select(Variables.FILTER.RANDOMITEMS).poll().id();
            ctx.bank.deposit(itemToBankID, Variables.BANK.ALL);
            Condition.wait(() -> ctx.inventory.select().id(itemToBankID).count() == 0);
        }

        ctx.bank.close();
    }

}
