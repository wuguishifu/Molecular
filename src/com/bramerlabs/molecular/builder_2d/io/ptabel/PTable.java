package com.bramerlabs.molecular.builder_2d.io.ptabel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PTable {

    // the size of the grid
    private static final int gridWidth = 18;
    private static final int gridHeight = 9;

    // the grid size
    public static final int gridSize = 32;

    // the width and height of the frame
    private static final int width = gridSize * gridWidth + gridSize/8*(gridWidth-1);
    private static final int height = gridSize * gridHeight + gridSize/8*(gridHeight-1);

    // the window variables
    private static JFrame frame;
    private static JPanel panel;
    private static PTableListener listener;

    // if an element is selected
    private static boolean hasSelected = false;

    // what element is selected, default carbon (5 because index starts at 0)
    public static int selectedAtom = 5;

    /**
     * initialize the ptable class
     */
    public static void init() {

        // initialize the frame
        frame = new JFrame("Periodic Table");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setFocusable(true);
        frame.setResizable(false);

        // determine the coordinates of the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dim.getWidth() / 2 - width / 2);
        int y = (int) (dim.getHeight() / 2 - height / 2);
        frame.setLocation(x, y);

        // initialize the panel
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                PTable.drawPeriodicTable(g);
            }
        };
        panel.setSize(width, height);

        // fix the frame size to account for the top bar
        frame.getContentPane().setPreferredSize(new Dimension(width + 1, height + 1));
        frame.pack();

        // add the panel to the frame
        frame.add(panel);

        // initialize the listener
        listener = new PTableListener();

        // add the listeners
        frame.addKeyListener(listener);
        panel.addMouseMotionListener(listener);
        panel.addMouseListener(listener);

        // request the focus of the mouse
        frame.requestFocus();
        panel.repaint();
    }

            /**
             * draws centered text
             * @param g - the graphics object handed down by panel.repaint()
             * @param minX - the min x value to draw in
             * @param minY - the min y value to draw in
             * @param maxX - the max x value to draw in
             * @param maxY - the max y value to draw in
             * @param string - the string to draw
             */
    private static void drawCenteredText(Graphics g, int minX, int minY, int maxX, int maxY, String string) {
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(string, g2d);
        int x = minX + ((maxX - minX) - (int) r.getWidth()) / 2;
        int y = minY + ((maxY - minY) - (int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(string, x, y);
    }

    /**
     * shows the periodic table to select an atom
     */
    public static void selectAtom() {
        frame.setVisible(true);
    }

    /**
     * closes the window
     */
    public static void dispose() {
        frame.dispose();
    }

    /**
     * rounds a number to the nearest multiple of another number
     * @param num - the number to round
     * @param multipleOf - the value to round a multiple to
     * @return - the rounded value
     */
    private static double round(double num, int multipleOf) {
        return Math.floor((num +  (double)multipleOf / 2) / multipleOf) * multipleOf;
    }

    public static void setVisible(boolean bool) {
        frame.setVisible(bool);
    }

    // the positions to draw boxes
    public static final int[][] gridBoxPositions = new int[][]{
            {0, 0},                                                                                                                                        {17, 0},
            {0, 1}, {1, 1},                                                                                   {12, 1}, {13, 1}, {14, 1}, {15, 1}, {16, 1}, {17, 1},
            {0, 2}, {1, 2},                                                                                   {12, 2}, {13, 2}, {14, 2}, {15, 2}, {16, 2}, {17, 2},
            {0, 3}, {1, 3}, {2, 3}, {3, 3}, {4, 3}, {5, 3}, {6, 3}, {7, 3}, {8, 3}, {9, 3}, {10, 3}, {11, 3}, {12, 3}, {13, 3}, {14, 3}, {15, 3}, {16, 3}, {17, 3},
            {0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}, {6, 4}, {7, 4}, {8, 4}, {9, 4}, {10, 4}, {11, 4}, {12, 4}, {13, 4}, {14, 4}, {15, 4}, {16, 4}, {17, 4},
            {0, 5}, {1, 5}, {2, 5}, {3, 5}, {4, 5}, {5, 5}, {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5}, {11, 5}, {12, 5}, {13, 5}, {14, 5}, {15, 5}, {16, 5}, {17, 5},
            {0, 6}, {1, 6}, {2, 6}, {3, 6}, {4, 6}, {5, 6}, {6, 6}, {7, 6}, {8, 6}, {9, 6}, {10, 6}, {11, 6}, {12, 6}, {13, 6}, {14, 6}, {15, 6}, {16, 6}, {17, 6},
                            {2, 7}, {3, 7}, {4, 7}, {5, 7}, {6, 7}, {7, 7}, {8, 7}, {9, 7}, {10, 7}, {11, 7}, {12, 7}, {13, 7}, {14, 7}, {15, 7},
                            {2, 8}, {3, 8}, {4, 8}, {5, 8}, {6, 8}, {7, 8}, {8, 8}, {9, 8}, {10, 8}, {11, 8}, {12, 8}, {13, 8}, {14, 8}, {15, 8},
    };

    // the atomic abbreviations
    public final static String[] atomicAbbreviations = new String[]{
            "H" , "He", "Li", "Be", "B" , "C" , "N" , "O" , "F" , "Ne", "Na", "Mg", "Al", "Si", "P" , "S" , "Cl", "Ar",
            "K" , "Ca", "Sc", "Ti", "V" , "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr",
            "Rb", "Sr", "Y" , "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I" , "Xe",
            "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf",
            "Ta", "W" , "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th",
            "Pa", "U" , "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs",
            "Mt", "Ds", "Rg", "Cn", "Nh", "Fl", "Mc", "Lv", "Ts", "Og",
    };

    // the amount of elements there are
    final static int numElems = 118;

    /**
     * draws the periodic table on the panel
     */
    public static void drawPeriodicTable(Graphics g) {
        for (int i = 0; i < numElems; i++) {
            int[] gridBox = gridBoxPositions[i];
            int minX = (gridSize*9/8) * gridBox[0];
            int minY = (gridSize*9/8) * gridBox[1];
            g.setColor(Color.BLACK);
            g.drawRect(minX, minY, gridSize, gridSize);
            drawCenteredText(g, minX, minY, minX + gridSize, minY + gridSize, atomicAbbreviations[i]);
        }

        // the position of the mouse
        int mouseX = listener.getMouseX();
        int mouseY = listener.getMouseY();

        // floor the values
        int selectedMinX = (int) ((gridSize*9/8) * Math.floor((mouseX)/((float)gridSize*9/8)));
        int selectedMinY = (int) ((gridSize*9/8) * Math.floor((mouseY)/((float)gridSize*9/8)));

        // determine the index of the grid box the mouse is in
        int index = -1;
        for (int i = 0; i < numElems; i++) {
            int[] gridBox = gridBoxPositions[i];
            if (gridBox[0] * (gridSize*9/8) == selectedMinX && gridBox[1] * (gridSize*9/8) == selectedMinY) {
                index = i;
                break;
            }
        }

        // only draw if the mouse is in a grid box
        if (index != -1) {
            // color the highlighted box
            g.setColor(new Color(95, 221, 255));
            g.drawRect(selectedMinX, selectedMinY, gridSize, gridSize);
            drawCenteredText(g, selectedMinX, selectedMinY, selectedMinX + gridSize, selectedMinY + gridSize, atomicAbbreviations[index]);
        }
    }

    /**
     * repaints the ptable
     */
    public static void repaint() {
        panel.repaint();
    }
}
