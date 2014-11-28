/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts.MookyFisher.tasks;

import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Interactive;
import org.powerbot.script.rt4.Npc;
import scripts.MookyFisher.node.Task;
import scripts.MookyFisher.vars.Variables;
import scripts.MookyFisher.utils.Paint;

/**
 * @author Mookyman
 */
public class Fish extends Task<ClientContext> {

    private int fishingSpotID;
    private int equipementCount;
    private Paint paint;
    private String fishingType;
    private boolean inArea;

    public Fish(ClientContext ctx, Paint paint, int fishingSpotID, int equipementCount, String fishingType, boolean inArea) {
        super(ctx);
        this.paint = paint;
        this.fishingSpotID = fishingSpotID;
        this.equipementCount = equipementCount;
        this.fishingType = fishingType;
        this.inArea = inArea;
    }

    @Override
    public boolean activate() {
        return !ctx.npcs.select().id(fishingSpotID).isEmpty() // Fishing spot exists nearby
                && !ctx.players.local().inCombat() // Not currently in combat
                && ctx.inventory.select().count() < 28 // Invo not full
                && ctx.players.local().animation() == -1 // Not currently fishing
                && ctx.inventory.select().select(Variables.FILTER.EQUIPEMENT).count() == equipementCount;
    }

    @Override
    public void execute() {
        Npc fishLocation;
        if(inArea){
            fishLocation = ctx.npcs.select().id(fishingSpotID).each(Interactive.doSetBounds(Variables.BOUNDS.FISH)).nearest().poll(); // Set fishing spot
        }
        else{
            fishLocation = ctx.npcs.select().id(fishingSpotID).each(Interactive.doSetBounds(Variables.BOUNDS.FISH)).within(Variables.LOCATION.GUILDDOCK).nearest().poll(); // Set fishing spot
        }
        Tile fishTile = fishLocation.tile();
        paint.setFish(fishLocation);

        if (fishTile.distanceTo(ctx.players.local()) > 3) {
            paint.setStatus("Moving to fishing spot");
            ctx.movement.step(fishTile); // Move to location
            ctx.camera.turnTo(fishLocation); // Turn to fishing spot
            Condition.wait(() -> fishTile.distanceTo(ctx.players.local()) < 3, Random.nextInt(1000, 2000));
        }
        paint.setStatus("Fishing");
        fishLocation.interact(fishingType, "Fishing spot"); // Start fishing
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() != -1; // Wait until we start fishing before continuing
            }
        });
    }

}
