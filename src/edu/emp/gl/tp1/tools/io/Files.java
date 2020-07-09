/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.tools.io;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author FATEH
 */
public class Files {

    public static File userSelected(final String startHere,
            final String extension,
            final String description,
            final String selectButtonText)
            throws FileNotFoundException {
        FileFilter filter
                = new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory()
                        || (extension != null
                        && f.getName().endsWith(extension));
            }

            public String getDescription() {
                return description;
            }
        };

        JFileChooser chooser = new JFileChooser(startHere);
        chooser.setFileFilter(filter);

        int result = chooser.showDialog(null, selectButtonText);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }

        throw new FileNotFoundException("No file selected by user");
    }

    static class Test {

        public static void main(String[] args) {
            try {
                File f = Files.userSelected(".", ".pgol", "Patch File", "Select Patch file");
                FileInputStream fis = new FileInputStream(f);
                System.out.println(fis.read() + " " + fis.read());
                System.out.println("Selected " + f.getName());
            } catch (FileNotFoundException e) {
                System.out.println("No file selected");
            } catch (IOException ex) {
                Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0); // Required to stop AWT thread & shut down.
        }
    }
}
