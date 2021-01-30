package com.bramerlabs.molecular.builder_2d.molecule_2d;

import com.bramerlabs.molecular.builder_2d.io.ptabel.PTable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class Atom2D {

    // the x and y position of this atom
    private int x, y;

    // the radius of this atom
    private int r;

    // the atomic number of this atom
    private int atomicNumber;

    // the color of this atom - default black
    private Color color = Color.BLACK;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final Color HIGHLIGHT_COLOR = new Color(19, 173, 0);

    // if the atom is highlighted
    private boolean isHighlighted = false;

    /**
     * default constructor for specified values
     * @param x - the x position
     * @param y - the y position
     * @param r - the radius
     */
    public Atom2D(int x, int y, int r, int atomicNumber) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.atomicNumber = atomicNumber;
    }

    /**
     * getter method
     * @return - the x position
     */
    public int getX() {
        return x;
    }

    /**
     * getter method
     * @return - the y position
     */
    public int getY() {
        return y;
    }

    /**
     * getter method
     * @return - the radius of the atom
     */
    public int getRadius() {
        return this.r;
    }

    /**
     * sets the color
     * @param color - the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * toggles the high light
     */
    public void toggleHighlight() {
        isHighlighted = !isHighlighted;
        color = isHighlighted ? HIGHLIGHT_COLOR : DEFAULT_COLOR;
    }

    /**
     * paints the atom using java.awt
     * @param g - the graphics component handed down by panel.repaint()
     */
    public void paint(Graphics g) {
        g.setColor(UIManager.getColor("Panel.background"));
        g.fillOval(x - r, y - r, r*2, r*2);
        g.setColor(color);
        g.drawOval(x - r, y - r, r*2, r*2);
        drawCenteredText(g, x - r, y - r, x + r, y + r, PTable.atomicAbbreviations[this.atomicNumber]);
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
    private void drawCenteredText(Graphics g, int minX, int minY, int maxX, int maxY, String string) {
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(string, g2d);
        int x = minX + ((maxX - minX) - (int) r.getWidth()) / 2;
        int y = minY + ((maxY - minY) - (int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(string, x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Atom2D)) return false;
        Atom2D atom2D = (Atom2D) o;
        return x == atom2D.x &&
                y == atom2D.y &&
                r == atom2D.r &&
                Objects.equals(color, atom2D.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, color);
    }
}