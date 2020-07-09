/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.tools.EventManagement;

import edu.emp.gl.tp1.tools.EventManagement.Listener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author FATEH
 */
public class Publisher {
    // we can use volatile here to allow multi-thread access to listeners
    private Collection listeners;
    
    public Publisher(){
        listeners = new HashSet<Listener>();
    }
    
    synchronized public void subscribe(Listener listener){
        listeners.add(listener);
        
    }
    
    synchronized public void unsubscribe(Listener listener){
        listeners.remove(listener);
    }

    public void publish(Object msg) {
        for(Iterator listener = listeners.iterator(); listener.hasNext();){
            ((Listener)listener.next()).update(this, msg);
            
        }
    }
}
