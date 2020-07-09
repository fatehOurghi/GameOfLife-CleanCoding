/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.tools.Positioning;

import edu.emp.gl.gol.model.IPosition;
import java.io.Serializable;

/**
 *
 * @author FATEH
 */
public class Position2D implements IPosition, Serializable{
   private int x;
   private int y;
   
   public Position2D(int x, int y) {
     this.x = x;
     this.y = y;
   }
 
   @Override
   public int getX() {
     return this.x;
   }
 
   @Override
   public int getY() {
     return this.y;
   }
 
   @Override
   public int getZ() {
     return 0;
   }
 
    
}
