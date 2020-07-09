/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.life;

import edu.emp.gl.tp1.tools.EventManagement.Publisher;
import edu.emp.gl.tp1.tools.EventManagement.Listener;
import edu.emp.gl.tp1.ui.AbstractWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author FATEH
 */
public class Clock implements IClock {

    private Timer clock;
    private TimerTask tick = null;
    private Publisher publisher;
    private int frequency;

    public Clock(AbstractWindow window) {
        publisher = new Publisher();
        clock = new Timer();
        createMenus(window);
        frequency = 500;//ms
    }

    @Override
    public void addClockListener(Listener listener) {
        publisher.subscribe(listener);
    }

    @Override
    public void startTicking(int millisecondsBetweenTicks) {
        if (tick != null) {
            tick.cancel();
            tick = null;
        }
        if (millisecondsBetweenTicks > 0) {
            tick = new TimerTask() {
                public void run() {
                    tick();
                }
            };
            clock.scheduleAtFixedRate(tick, 0, millisecondsBetweenTicks);
        }
    }

    @Override
    public void stop() {
        startTicking(0);
    }

    @Override
    public void tick() {
        publisher.publish(null);
    }

    @Override
    public void changeFrequency() {
        stop();
        try {
            frequency = Integer.parseInt(JOptionPane.showInputDialog("Enter the clock frequency"));
        } catch (NumberFormatException e) {
            if (frequency <= 0) {
                frequency = 500;
            }
        } finally {
            if (frequency <= 0) {
                frequency = 500;
            }
            startTicking(frequency);
        }
    }

    private void createMenus(AbstractWindow window) {
        ActionListener handler
                = (ActionEvent e) -> {
                    String toDo = ((JMenuItem) e.getSource()).getName();
                    switch (toDo) {
                        case "Play":
                            startTicking(frequency); // 0.5 second between ticks
                            break;
                        case "Pause":
                            stop();
                            break;
                        case "Change Frequency":
                            changeFrequency();
                            break;
                        case "Exit":
                            System.exit(0); // exit
                    }
                };

        window.addLine("Game", "Play", handler);
        window.addLine("Game", "Pause", handler);
        window.addLine("Game", "Change Frequency", handler);
        window.addLine("Game", "Exit", handler);

    }

}
