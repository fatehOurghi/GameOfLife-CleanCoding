/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.life;

import edu.emp.gl.tp1.tools.EventManagement.Listener;

/**
 *
 * @author FATEH
 */
public interface IClock {

    void addClockListener(Listener listener);

    void changeFrequency();

    void startTicking(int millisecondsBetweenTicks);

    void stop();

    void tick();
    
}
