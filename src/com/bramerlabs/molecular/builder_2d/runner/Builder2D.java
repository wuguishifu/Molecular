package com.bramerlabs.molecular.builder_2d.runner;

import com.bramerlabs.molecular.builder_2d.io.Frame;
import com.bramerlabs.molecular.builder_2d.io.Panel;
import com.bramerlabs.molecular.builder_2d.io.ptabel.PTable;
import com.bramerlabs.molecular.builder_2d.molecule_2d.Molecule2D;

import java.awt.*;
import java.util.ArrayList;

public class Builder2D {

    private final static int width = 800, height = 600;

    /**
     * main runnable
     * @param args - jvm arguments
     */
    public static void main(String[] args) {
        // initialize the PTable
        PTable.init();
        PTable.repaint();

        Molecule2D molecule = new Molecule2D(new ArrayList<>(), new ArrayList<>());
        Panel panel = new Panel(width, height, molecule);
        Frame frame = new Frame(width, height, molecule);

        // determine the coordinates of the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dim.getWidth()/2 - width/2);
        int y = (int) (dim.getHeight()/2 - height/2);
        frame.setLocation(x, y);

        // add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);

        // add the listeners
        panel.addMouseListener(frame);
        panel.addMouseMotionListener(frame);
        frame.addKeyListener(frame);

        // runnable
        while (!frame.shouldClose) {
            panel.repaint();
            PTable.repaint();
            try {
                Thread.sleep(20); // delay 20 ms
            } catch (InterruptedException ignored) {
            }
        }
        frame.dispose();
        PTable.dispose();
    }
}