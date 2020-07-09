/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.tools.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author FATEH
 */
public class PatchIOHandler implements IOHandler {

    @Override
    public void store(Storable patch) {
        try {
            FileOutputStream out = new FileOutputStream(
                    Files.userSelected(".", ".patch", "Patch File", "Store"));
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(patch);
            oos.close();
            out.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Write Failed!",
                    "The Game of Life", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Storable load() {
        Storable memento = null;
        try {
            FileInputStream in = new FileInputStream(
                    Files.userSelected(".", ".patch", "Patch File", "Load"));
            ObjectInputStream source = new ObjectInputStream(in);
            memento = (Storable) (source.readObject());
            source.close();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Read Failed!",
                    "The Game of Life", JOptionPane.ERROR_MESSAGE);
        } finally {
            return memento;
        }
    }

}
