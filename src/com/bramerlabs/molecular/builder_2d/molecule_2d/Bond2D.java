package com.bramerlabs.molecular.builder_2d.molecule_2d;


import java.awt.*;
import java.util.Objects;

public class Bond2D {

    // the x and y coordinate of both ends of the bond
    private int x1, y1, x2, y2;

    // the bond order - default 1
    private int order = 1;
    private final static int bondSep = 5;

    // the color of this bond - default gray
    private Color color = new Color(40, 40, 40);

    /**
     * default constructor for specified endpoints
     * @param x1 - the x position of the first endpoint
     * @param y1 - the y position of the first endpoint
     * @param x2 - the x position of the second endpoint
     * @param y2 - the y position of the second endpoint
     */
    public Bond2D(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * default constructor for specified endpoints and bond order
     * @param x1 - the x position of the first endpoint
     * @param y1 - the y position of the first endpoint
     * @param x2 - the x position of the second endpoint
     * @param y2 - the y position of the second endpoint
     * @param order - the bond order
     */
    public Bond2D(int x1, int y1, int x2, int y2, int order) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.order = order;
    }

    /**
     * constructor for specified connected atoms and bond order
     * @param a1 - the first connected atom
     * @param a2 - the second connected atom
     * @param order - the bond order
     */
    public Bond2D(Atom2D a1, Atom2D a2, int order) {
        this.x1 = a1.getX();
        this.y1 = a1.getY();
        this.x2 = a2.getX();
        this.y2 = a2.getY();
        this.order = order;
    }

    /**
     * constructor for specified connected atoms
     * @param a1 - the first connected atom
     * @param a2 - the second connected atom
     */
    public Bond2D(Atom2D a1, Atom2D a2) {
        this.x1 = a1.getX();
        this.y1 = a1.getY();
        this.x2 = a2.getX();
        this.y2 = a2.getY();
    }

    /**
     * constructor for 1 specified atom and 1 specified x, y pair
     * @param a1 - the atom that this bond is connected to
     * @param x2 - the x coordinate of the position this atom is connected to
     * @param y2 - the y coordinate of the position this atom is connected to
     */
    public Bond2D(Atom2D a1, int x2, int y2) {
        this.x1 = a1.getX();
        this.y1 = a1.getY();
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * constructor for 1 specified atom, 1 specified x, y pair, and bond order
     * @param a1 - the atom that this bond is connected to
     * @param x2 - the x coordinate of the position this atom is connected to
     * @param y2 - the y coordinate of the position this atom is connected to
     * @param order - the bond order
     */
    public Bond2D(Atom2D a1, int x2, int y2, int order) {
        this.x1 = a1.getX();
        this.y1 = a1.getY();
        this.x2 = x2;
        this.y2 = y2;
        this.order = order;
    }

    /**
     * getter method
     * @return - the x position of the first endpoint
     */
    public int getX1() {
        return x1;
    }

    /**
     * getter method
     * @return - the y position of the first endpoint
     */
    public int getY1() {
        return y1;
    }

    /**
     * getter method
     * @return - the x position of the second endpoint
     */
    public int getX2() {
        return x2;
    }

    /**
     * getter method
     * @return - the y position of the second endpoint
     */
    public int getY2() {
        return y2;
    }

    /**
     * paints the atom using java.awt
     * @param g - the graphics component handed down by panel.repaint()
     */
    public void paint(Graphics g) {
        g.setColor(color);
        if (order == 1 || order == 3) {
            g.drawLine(x1, y1, x2, y2);
        }
        if (order == 2 || order == 3) { // this is slopy
            // create a parallel line
            float L = (float) (1/(Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2) * (y1 - y2)))); // optimization by only dividing once
            int x1p = (int) (x1 + bondSep * (y2 - y1) * L);
            int x2p = (int) (x2 + bondSep * (y2 - y1) * L);
            int y1p = (int) (y1 + bondSep * (x1 - x2) * L);
            int y2p = (int) (y2 + bondSep * (x1 - x2) * L);
            g.drawLine(x1p, y1p, x2p, y2p);
            x1p = (int) (x1 - bondSep * (y2 - y1) * L);
            x2p = (int) (x2 - bondSep * (y2 - y1) * L);
            y1p = (int) (y1 - bondSep * (x1 - x2) * L);
            y2p = (int) (y2 - bondSep * (x1 - x2) * L);
            g.drawLine(x1p, y1p, x2p, y2p);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bond2D)) return false;
        Bond2D bond2D = (Bond2D) o;
        return x1 == bond2D.x1 &&
                y1 == bond2D.y1 &&
                x2 == bond2D.x2 &&
                y2 == bond2D.y2 &&
                order == bond2D.order &&
                Objects.equals(color, bond2D.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, y1, x2, y2, order, color);
    }
}
