package com.bramerlabs.molecular.builder_2d.io.ptabel;

import java.awt.event.*;

public class PTableListener implements KeyListener, MouseListener, MouseMotionListener {

    // the mouse x an y position
    private int mouseX, mouseY;

    // if the table should close
    public boolean shouldClose = false;

    /**
     * default constructor
     */
    public PTableListener() {
        // do nothing
    }

    /**
     * getter method
     * @return - the x position of the cursor
     */
    public int getMouseX() {
        return mouseX;
    }

    /**
     * getter method
     * @return - the y position of the cursor
     */
    public int getMouseY() {
        return mouseY;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // process atomic number selector
        // floor the values
        int selectedMinX = (int) ((PTable.gridSize*9/8) * Math.floor((mouseX)/((float)PTable.gridSize*9/8)));
        int selectedMinY = (int) ((PTable.gridSize*9/8) * Math.floor((mouseY)/((float)PTable.gridSize*9/8)));

        // determine the index of the grid box the mouse is in
        int index = -1;
        for (int i = 0; i < PTable.numElems; i++) {
            int[] gridBox = PTable.gridBoxPositions[i];
            if (gridBox[0] * (PTable.gridSize*9/8) == selectedMinX && gridBox[1] * (PTable.gridSize*9/8) == selectedMinY) {
                index = i;
                break;
            }
        }

        // the index happens to be the selected atom atomic number - 1
        if (index != -1) {
            PTable.selectedAtom = index;
            PTable.setVisible(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        this.mouseX = mouseEvent.getX();
        this.mouseY = mouseEvent.getY();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        this.mouseX = mouseEvent.getX();
        this.mouseY = mouseEvent.getY();
    }
}
