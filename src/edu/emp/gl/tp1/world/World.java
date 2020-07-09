/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.emp.gl.tp1.world;

import edu.emp.gl.tp1.tools.EventManagement.Publisher;
import edu.emp.gl.tp1.tools.Positioning.Position2D;
import edu.emp.gl.tp1.tools.EventManagement.Listener;
import edu.emp.gl.gol.model.*;
import edu.emp.gl.tp1.life.IClock;
import edu.emp.gl.tp1.tools.io.IOHandler;
import edu.emp.gl.tp1.tools.io.Memento;
import edu.emp.gl.tp1.tools.io.PatchIOHandler;
import edu.emp.gl.tp1.tools.io.Storable;
import edu.emp.gl.tp1.ui.AbstractWindow;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author FATEH Concrete world that listen to the clock publisher
 */
public class World extends JPanel implements IWorld, Listener { // 

    private int gridWidth, gridHeight;
    private final IClock clock;
    private ICell[][] cells;
    private Properties worldProperties;

    public World(IClock clock, AbstractWindow window) {
        createMenus(window);
        this.clock = clock;
        clock.addClockListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / Cell.DEFAULT_SPACE;
                int y = e.getY() / Cell.DEFAULT_SPACE;
                if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight) {
                    ((Cell) cells[x][y]).onClick();
                    repaint();
                }

            }
        });
    }

    private void createMenus(AbstractWindow window) {
        ActionListener handler
                = (ActionEvent e) -> {
                    String toDo = ((JMenuItem) e.getSource()).getName();
                    switch (toDo) {
                        case "Load patch":
                            loadPatch();
                            break; // load patch
                        case "Store patch":
                            storePatch();
                            break; // store patch
                        case "Load world":
                            loadWorld();
                            break; // store patch
                        case "Reinitialize":
                            reinitialize();
                            break;
                    }
                };

        window.addLine("Patch", "Load patch", handler);
        window.addLine("Patch", "Store patch", handler);
        window.addLine("World", "Load world", handler);
        window.addLine("World", "Reinitialize", handler);
    }

    private void reinitialize() {
        //stop then reinit
        clock.stop();
        initializeWorld(worldProperties);
        this.repaint();
    }

    public void loadPatch() {
        clock.stop();
        IOHandler handler = new PatchIOHandler();
        reinitialize();
        restore(handler.load());
        repaint();
    }

    public void storePatch() {
        clock.stop();
        IOHandler handler = new PatchIOHandler();
        handler.store(save());
    }

    public void loadWorld() {
        System.out.println("load world");
    }

    @Override
    public void update(Publisher publisher, Object msg) {
        //update the world here
        if (isPopulated()) {
            this.repaint();
            evolveWorld();
        } else {
            clock.stop();
        }

    }

    /**
     *
     * @param p: a Properties object that contains the width and height of the
     * world cells
     */
    @Override
    public void initializeWorld(Properties p) {
        worldProperties = p;
        if (p == null) {
            this.gridWidth = 120;
            this.gridHeight = 90;
        } else {
            this.gridWidth = Integer.parseInt(p.getProperty("WIDTH", "120"));
            this.gridHeight = Integer.parseInt(p.getProperty("HEIGHT", "90"));
        }
        cells = new ICell[gridWidth][gridHeight];
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                cells[i][j] = new Cell(this, new Position2D(i, j), ICell.State.DEAD);
            }
        }
        setPreferredSize(new Dimension(gridWidth * Cell.DEFAULT_SPACE, gridHeight * Cell.DEFAULT_SPACE));
    }

    /**
     *
     * @return ArrayList of all cells of the cells
     */
    @Override
    public List<ICell> getAllCells() {
        List<ICell> list = new ArrayList<>();
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                list.add(cells[i][j]);
            }
        }
        return list;
    }

    /**
     *
     * @param pos: the position of the requested cell
     * @return a cell object
     */
    @Override
    public ICell getCell(IPosition pos) {
        if (pos == null) {
            return null;
        }
        if (pos.getX() < 0 || pos.getX() >= this.gridWidth
                || pos.getY() < 0 || pos.getY() >= this.gridHeight) {
            return null;
        }
        return cells[pos.getX()][pos.getY()];
    }

    @Override
    public void InsertPatch(List<ICell> cells, IPosition relativePosition) {
        for (ICell cell : cells) {

            Position2D pos = new Position2D(cell.getPosition().getX() + relativePosition.getX(), cell.getPosition().getY() + relativePosition.getY());

            if (pos.getX() < 0 || pos.getX() >= this.gridWidth || pos
                    .getY() < 0 || pos.getY() >= this.gridHeight) {
                continue;
            }

            this.cells[pos.getX()][pos.getY()] = new Cell(this, pos, cell.getState());
        }
    }

    @Override
    public List<ICell> getNeigbors(ICell cell) {
        IPosition pos = cell.getPosition();
        List<ICell> neighbors = new ArrayList<>();

        for (int i = Math.max(0, pos.getX() - 1); i <= Math.min(this.gridWidth - 1, pos.getX() + 1); i++) {
            for (int j = Math.max(0, pos.getY() - 1); j <= Math.min(this.gridHeight - 1, pos.getY() + 1); j++) {
                if (pos.getX() != i || pos.getY() != j) {
                    neighbors.add(cells[i][j]);
                }
            }
        }

        return neighbors;
    }

    @Override
    public void evolveWorld() {
        beforeEvolve();
        doEvolve();
    }

    @Override
    public boolean isPopulated() {
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                if (cells[i][j].getState() == ICell.State.ALIVE) {
                    return true;
                }
            }
        }
        return false;
    }

    private void beforeEvolve() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                cells[i][j].beforeEvolve();
            }
        }
    }

    private void doEvolve() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                cells[i][j].evolve();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                ((Cell) cells[i][j]).render(g, 1);
            }
        }
    }

    public Storable save() {
        List<IPosition> liveCells = new ArrayList<>();
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                if (cells[i][j].getState() == ICell.State.ALIVE) {
                    liveCells.add(cells[i][j].getPosition());
                }
            }
        }
        return new Memento(liveCells);
    }

    public void restore(Storable memento) {
        if (memento != null) {
            List<IPosition> positions = (List<IPosition>) (memento.getSnapshot());
            List<ICell> cells = new ArrayList<>();
            Iterator<IPosition> it = positions.iterator();
            while (it.hasNext()) {
                IPosition pos = it.next();
                cells.add(new Cell(this, pos, ICell.State.ALIVE));
            }
            InsertPatch(cells, new Position2D(0, 0));
        }
    }

}
