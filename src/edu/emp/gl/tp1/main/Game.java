/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.main;

import edu.emp.gl.gol.model.IWorld;
import edu.emp.gl.tp1.life.Clock;
import edu.emp.gl.tp1.life.IClock;
import edu.emp.gl.tp1.world.World;
import edu.emp.gl.tp1.ui.Window;
import java.awt.Container;
import java.util.Properties;
import javax.swing.JFrame;

/**
 *
 * @author FATEH
 */
public class Game extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();

    }

    private Game() {
        super("Game Of Life");

        Window win = new Window();
        win.establish(this);

        //use lookup here
        IClock clock = new Clock(win);
        IWorld world = new World(clock, win);

        Properties p = new Properties();
        p.setProperty(IWorld.WIDTH, "80");
        p.setProperty(IWorld.HEIGHT, "80");
        world.initializeWorld(p);


        setContentPane((Container)world);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

}
