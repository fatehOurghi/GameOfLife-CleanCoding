/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.ui;

import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author FATEH
 */
public interface AbstractWindow {

    void addLine(String menuName, String name, ActionListener handler);

    void establish(JFrame frame);
    
}
