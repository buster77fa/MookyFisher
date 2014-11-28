/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts.MookyFisher;

import scripts.MookyFisher.utils.GUI;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import org.powerbot.script.BotMenuListener;
import org.powerbot.script.Condition;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Npc;
import scripts.MookyFisher.node.Task;
import scripts.MookyFisher.utils.Paint;

/**
 * @author Mookyman
 */
@Script.Manifest(name = "MookyFisher", description = "Fishes in a few locations and banks/powerfishers", properties = "client=4")
public class Fisher extends PollingScript<ClientContext> implements PaintListener, BotMenuListener, MessageListener {

    private List<Task> taskList = new ArrayList<Task>(); // List of tasks to continually activate/execute
    private final long startTime = System.currentTimeMillis(); // Gets time started to calculate exp/hour

    private final int startPaintX = 30, startPaintY = 330;
    public static Npc targetFish;

    private boolean paintGuild = false;
    private Paint paint = new Paint(ctx);
    JFrame frame = new GUI(ctx, taskList, paint).frameSetup(); // Loads the GUI frame to set fishing location

    @Override
    public void start() {
        initCamera(ctx); // Set camera to something I normally play with
        frameLoad(); // Load GUI components
    }

    public void frameLoad() {
        frame.show(); // Show GUI
    }

    @Override

    public void poll() {

        for (Task task : taskList) { // For each task
            cameraChange(ctx);
            if ((paint.formatTime((System.currentTimeMillis()) - startTime).equals("01:00:00"))) {
                System.out.println("Screen shot at: " + paint.formatTime((System.currentTimeMillis()) - startTime));
                screenShot();
                Condition.sleep(1400);
            }
            if (task.activate()) { // Check if we should execute the task
                task.execute(); // Execute the task
            }
        }
    }

    @Override
    public void repaint(Graphics g1) { // Paint to show timer/counter etc.
        paint.repaint(g1);
    }

    public static void initCamera(ClientContext ctx) { // Set camera on script load
        ctx.camera.pitch(Random.nextInt(50, 99)); // Set camera angle
        Condition.sleep(Random.nextInt(500, 1200)); // Wait a random time before doing anything else
    }

    public void cameraChange(ClientContext ctx) { // Change camera randomly
        if (Random.nextInt(0, 10000) > 9997) {
            GameObject randomObject = ctx.objects.select().shuffle().poll();
            ctx.camera.turnTo(randomObject.tile());
//            System.out.println("Turning to " + randomObject.toString() + " at: " + paint.formatTime((System.currentTimeMillis()) - startTime));
        }
    }

    @Override
    public void menuSelected(MenuEvent e) { // Add open GUI option to Edit>Options menu in rsBot
        final JMenu menu = (JMenu) e.getSource();
        final JMenuItem menuOpenGUI = new JMenuItem("Open GUI");
        menuOpenGUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
            }
        });
        menu.add(menuOpenGUI);
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void screenShot() {
        final int width = ctx.game.dimensions().width, height = ctx.game.dimensions().height;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // your paint's repaint(Graphics) method
        repaint(img.createGraphics());
        img = img.getSubimage(startPaintY - 3, startPaintX - 23, 186, 142);
        final File screenshot = new File(getStorageDirectory(), String.valueOf(System.currentTimeMillis()).concat(".png"));
        try {
            ImageIO.write(img, "png", screenshot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messaged(MessageEvent me) {
        String msg = me.text();
        if (msg.startsWith("You catch a")) {
            paint.addFishCount();
        }
    }
}
