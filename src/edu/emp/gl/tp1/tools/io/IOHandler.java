/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.tools.io;

/**
 *
 * @author FATEH
 */
public interface IOHandler {
    public void store(Storable out);
    public Storable load();
}
