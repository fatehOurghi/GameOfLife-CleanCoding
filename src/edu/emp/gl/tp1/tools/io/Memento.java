/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.tools.io;

import edu.emp.gl.gol.model.IPosition;
import java.util.List;

/**
 *
 * @author FATEH
 */
public class Memento implements Storable{
    private List<IPosition> liveCells;
    
    public Memento(List liveCells){
        this.liveCells = liveCells;
    }
    
    @Override
    public Object getSnapshot(){
        return liveCells;
    }
}
