package com.bramerlabs.molecular.builder_2d.io;

import com.bramerlabs.molecular.builder_2d.molecule_2d.Molecule2D;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    // the x and y position
    private int x, y;

    private Molecule2D molecule;

    /**
     * default constructor
     */
    public Panel(int width, int height, Molecule2D molecule) {
        super();
        this.molecule = molecule;
        this.setSize(width, height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        molecule.paint(g);
    }
}