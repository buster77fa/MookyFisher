/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts.MookyFisher.utils;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.powerbot.script.rt4.ClientContext;
import scripts.MookyFisher.tasks.catherby.BetweenFishLocations;
import scripts.MookyFisher.node.Task;
import scripts.MookyFisher.tasks.Bank;
import scripts.MookyFisher.tasks.Drop;
import scripts.MookyFisher.tasks.Fish;
import scripts.MookyFisher.tasks.TraverseFromBank;
import scripts.MookyFisher.tasks.TraverseToBank;
import scripts.MookyFisher.vars.Variables;

/**
 * @author Mookyman
 */
public class GUI extends JFrame {

    List<Task> taskList;
    ClientContext ctx;
    Paint paint;

    public GUI(ClientContext ctx, List<Task> taskList, Paint paint) {
        this.ctx = ctx;
        this.taskList = taskList;
        this.paint = paint;
    }

    public JFrame frameSetup() {
        JFrame frame = new JFrame("MookyFisher v1.0");
        String[] locations = {"Draynor - Shrimp/Anchovies", "Barbarian Village - Trout/Salmon", "Seers Village - Trout/Salmon", "Catherby - Lobsters", "Fishing Guild - Lobsters"}; // Storing locations for combobox
        
       
        frame.getContentPane();
        frame.pack();
        frame.setSize(350, 85);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());

        JLabel instructions = new JLabel("Please start at desired fishing location with correct equipement");
        JComboBox spot = new JComboBox(locations); // List locations
        JCheckBox powerfish = new JCheckBox();
        JLabel labelPowerfish = new JLabel("Powerfish?");
        JButton start = new JButton("Start"); // Start button

        start.addActionListener(new ActionListener() { // Listen to start button for clicks

            public void actionPerformed(ActionEvent e) { // When clicked run this code
                //Execute when button is pressed
                if (spot.getSelectedIndex() == 0) { // If Draynor selected
                    taskList.removeAll(taskList); // Remove current tasks
                    if (powerfish.isSelected()) {
                        taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.DRAYNOR, Variables.COUNT.NET, Variables.FISHINGSTYLE.NET, false), new Drop((ClientContext) ctx, paint, Variables.ITEM.SHRIMPANCHOVIES))); // Add Draynor tasks to list
                    } else {
                        taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.DRAYNOR, Variables.COUNT.NET, Variables.FISHINGSTYLE.NET, false), new Bank((ClientContext) ctx, paint, Variables.COUNT.NET), new TraverseToBank((ClientContext) ctx, paint, Variables.TILEPATHS.DRAYNOR), new TraverseFromBank((ClientContext) ctx, paint, Variables.TILEPATHS.DRAYNOR, Variables.COUNT.NET))); // Add Draynor tasks to list
                    }

                } else if (spot.getSelectedIndex() == 1) { // If Barbvillage selected
                    if (ctx.skills.level(10) < 20) { // Check to see if player has less than 40 fishing
                        taskList.removeAll(taskList); // Remove current tasks
                        JOptionPane.showMessageDialog(frame, "Level 20 fishing is needed to catch trout"); // Display error
                        return; // Exit this method
                    } else { // If the play DOES have 40 fishing
                        taskList.removeAll(taskList); // Remove current tasks
                        if (powerfish.isSelected()) {
                            taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.BARBVILLAGE, Variables.COUNT.LURE, Variables.FISHINGSTYLE.LURE, false), new Drop((ClientContext) ctx, paint, Variables.ITEM.TROUTSALMON))); // Add BarbVillage tasks to list
                        } else {
                            taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.BARBVILLAGE, Variables.COUNT.LURE, Variables.FISHINGSTYLE.LURE, false), new Bank((ClientContext) ctx, paint, Variables.COUNT.LURE), new TraverseToBank((ClientContext) ctx, paint, Variables.TILEPATHS.EDGEVILLE), new TraverseFromBank((ClientContext) ctx, paint, Variables.TILEPATHS.EDGEVILLE, Variables.COUNT.LURE))); // Add BarbVillage tasks to list
                        }
                    }

                } else if (spot.getSelectedIndex() == 2) { // If Seers selected
                    if (ctx.skills.level(10) < 20) { // Check to see if player has less than 40 fishing
                        taskList.removeAll(taskList); // Remove current tasks
                        JOptionPane.showMessageDialog(frame, "Level 20 fishing is needed to catch trout"); // Display error
                        return; // Exit this method
                    } else { // If the play DOES have 40 fishing
                        taskList.removeAll(taskList); // Remove current tasks
                        if (powerfish.isSelected()) {
                            taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.SEERS, Variables.COUNT.LURE, Variables.FISHINGSTYLE.LURE, false), new Drop((ClientContext) ctx, paint, Variables.ITEM.TROUTSALMON))); // Add BarbVillage tasks to list
                        } else {
                            taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.SEERS, Variables.COUNT.LURE, Variables.FISHINGSTYLE.LURE, false), new Bank((ClientContext) ctx, paint, Variables.COUNT.LURE), new TraverseToBank((ClientContext) ctx, paint, Variables.TILEPATHS.SEERS), new TraverseFromBank((ClientContext) ctx, paint, Variables.TILEPATHS.SEERS, Variables.COUNT.LURE))); // Add BarbVillage tasks to list
                        }
                    }
                } else if (spot.getSelectedIndex() == 3) { // If Catherby selected
                    if (ctx.skills.level(10) < 40) { // Check to see if player has less than 40 fishing
                        taskList.removeAll(taskList); // Remove current tasks
                        JOptionPane.showMessageDialog(frame, "Level 40 fishing is needed to catch lobsters"); // Display error
                        return; // Exit this method
                    } else { // If the play DOES have 40 fishing
                        taskList.removeAll(taskList); // Remove current tasks
                        if (powerfish.isSelected()) {
                            taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.CATHERBY, Variables.COUNT.CAGE, Variables.FISHINGSTYLE.CAGE, false), new Drop((ClientContext) ctx, paint, Variables.ITEM.LOBSTERS), new BetweenFishLocations((ClientContext) ctx))); // Add Catherby tasks to list
                        } else {
                            taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.CATHERBY, Variables.COUNT.CAGE, Variables.FISHINGSTYLE.CAGE, false), new Bank((ClientContext) ctx, paint, Variables.COUNT.CAGE), new TraverseToBank((ClientContext) ctx, paint, Variables.TILEPATHS.CATHERBY), new TraverseFromBank((ClientContext) ctx, paint, Variables.TILEPATHS.CATHERBY, Variables.COUNT.CAGE), new BetweenFishLocations((ClientContext) ctx))); // Add Catherby tasks to list
                        }

                    }
                } else if (spot.getSelectedIndex() == 4) { // If Fishing guild selected
                    if (ctx.skills.level(10) < 40) { // Check to see if player has less than 40 fishing
                        taskList.removeAll(taskList); // Remove current tasks
                        JOptionPane.showMessageDialog(frame, "Level 40 fishing is needed to catch lobsters"); // Display error
                        return; // Exit this method
                    } else { // If the play DOES have 40 fishing
                        taskList.removeAll(taskList); // Remove current tasks
                        if (powerfish.isSelected()) {
                            JOptionPane.showMessageDialog(null, "Don't drop lobsters here, the bank is so close!.");
                            powerfish.setSelected(false);
                            return;
                        } else {
                            taskList.addAll(Arrays.asList(new Fish((ClientContext) ctx, paint, Variables.FISH.FISHINGGUILD, Variables.COUNT.CAGE, Variables.FISHINGSTYLE.CAGE, true), new Bank((ClientContext) ctx, paint, Variables.COUNT.CAGE), new TraverseToBank((ClientContext) ctx, paint, Variables.TILEPATHS.FISHINGGUILD), new TraverseFromBank((ClientContext) ctx, paint, Variables.TILEPATHS.FISHINGGUILD, Variables.COUNT.CAGE))); // Add Catherby tasks to list
                        }

                    }
                }
                frame.setVisible(false); // Hide frame is starting new taks.
            }
        });
         frame.add(instructions);
        frame.add(spot); // Add locations combobox to GUI
        frame.add(powerfish);
        frame.add(labelPowerfish);
        frame.add(start); // Add start button to GUI
        return frame;
    }

}
