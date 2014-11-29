/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts.MookyFisher.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;
import org.powerbot.script.rt4.Skills;
import scripts.MookyFisher.vars.Variables;

/**
 * @author Mookyman
 */
public class Paint extends ClientAccessor implements PaintListener {

    private final Color colorRed = new Color(198, 20, 20, 150);
    private final Font font = new Font("Courier", Font.PLAIN, 12);
    private final long startTime = System.currentTimeMillis();
    private final int startPaintX = 30, startPaintY = 330, textOffset = 8;
    private String status = "";
    private final int startExp = ctx.skills.experience(Skills.FISHING);
    private int fishCount;
    private Npc targetFish;

    public Paint(ClientContext ctx) {
        super(ctx);
    }

    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    @Override
    public void repaint(Graphics g1) {

        String currentLevel = "Current Level: " + ctx.skills.level(10);
        String runTime = "Run Time: " + formatTime((System.currentTimeMillis()) - startTime);
        String timeToLevel = "Time to Level: " + formatTime(timeToLevel(Variables.EXP.XP[ctx.skills.level(Skills.FISHING)]));
        String expGained = "Exp Gained: " + calcExp() + "(" + perHour(calcExp()) + "/h)";
        String expToLevel = "Exp to Level: " + calcExpToLevel();
        String fishCaught = "Fish Caught: " + (fishCount) + "(" + perHour(fishCount) + "/h)";
        String statusStr = "Status: " + status;

        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(antialiasing);
        g.setColor(Color.BLACK);
        g.drawRoundRect(startPaintY - 1, startPaintX - 21, 181, 136, 10, 10);
        g.setColor(colorRed);
        g.fillRoundRect(startPaintY, startPaintX - 20, 180, 135, 10, 10);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("MookyFisher V1.0", startPaintY + textOffset, startPaintX);
        g.drawString(currentLevel, startPaintY + textOffset, startPaintX + 15);
        g.drawString(runTime, startPaintY + textOffset, startPaintX + 30);
        g.drawString(timeToLevel, startPaintY + textOffset, startPaintX + 45);
        g.drawString(expGained, startPaintY + textOffset, startPaintX + 60);
        g.drawString(expToLevel, startPaintY + textOffset, startPaintX + 75);
        g.drawString(fishCaught, startPaintY + textOffset, startPaintX + 90);
        g.drawString(statusStr, startPaintY + textOffset, startPaintX + 105);

        if (getFish() != null) {
            renderFishingSpot(g1, Color.BLUE, getFish());
        }
        if ((((System.currentTimeMillis()) - startTime) / 1000) > 4) {
            renderCursor(g1);
        }

        final Iterator<Npc> iterator1 = ctx.npcs.select().id(Variables.FISH.FISHINGGUILD).within(10).iterator();

        while (iterator1.hasNext()) {
            final Npc current = iterator1.next();
            if (getFish() != null) {
                if (!current.tile().toString().equals(targetFish.tile().toString())) {
                    renderFishingSpot(g1, Color.ORANGE, current);
                }
            }
        }
    }

    public void renderFishingSpot(Graphics g, Color color, Npc targetFish) {
        Point p = targetFish.tile().matrix(ctx).point(0);
        int fx = (int) p.getX();
        int fy = (int) p.getY();
        g.setColor(color);
        g.drawLine(fx - 10, fy + 5, fx - 10, fy - 5);
        g.drawLine(fx - 10, fy - 5, fx + 8, fy + 5);
        g.drawLine(fx + 8, fy + 5, fx + 15, fy);
        g.drawLine(fx + 15, fy, fx + 8, fy - 5);
        g.drawLine(fx + 8, fy - 5, fx - 10, fy + 5);
    }

    public void renderCursor(Graphics g) {
        Point c = ctx.input.getLocation();
        int cx = (int) c.getX();
        int cy = (int) c.getY();
        g.setColor(Color.MAGENTA);
        g.drawLine(cx - 3, cy + 3, cx + 3, cy - 3);
        g.drawLine(cx - 3, cy - 3, cx + 3, cy + 3);
    }

    public String formatTime(long time) {
        String hms = String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                        .toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                        .toMinutes(time)));
        return hms;
    }

    public int perHour(int value) {
        return (int) ((value) * 3600000D / (System.currentTimeMillis() - startTime));
    }

    public long timeToLevel(int value) {
        double toLevel = value - startExp;
        int perHour = perHour(calcExp());
        if (perHour != 0) {
            return (long) (toLevel / perHour * 3600000D);
        }
        return 0;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int calcExp() {
        int currentExp = ctx.skills.experience(Skills.FISHING) - startExp;
        return currentExp;
    }

    public int calcExpToLevel() {
        int expLeft = Variables.EXP.XP[ctx.skills.level(Skills.FISHING)] - ctx.skills.experience(Skills.FISHING);
        return expLeft;
    }

    public void setFish(Npc npc) {
        this.targetFish = npc;
    }

    public Npc getFish() {
        return this.targetFish;
    }

    public void addFishCount() {
        this.fishCount++;
    }

}
