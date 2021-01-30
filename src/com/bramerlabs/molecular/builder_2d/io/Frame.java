package com.bramerlabs.molecular.builder_2d.io;

import com.bramerlabs.molecular.builder_2d.io.ptabel.PTable;
import com.bramerlabs.molecular.builder_2d.molecule_2d.Atom2D;
import com.bramerlabs.molecular.builder_2d.molecule_2d.Bond2D;
import com.bramerlabs.molecular.builder_2d.molecule_2d.Molecule2D;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Frame extends JFrame implements KeyListener, MouseListener, MouseMotionListener {

    // the width and height of this frame
    private int width, height;

    // the molecule in the window
    private Molecule2D molecule;

    // if the frame should close
    public boolean shouldClose = false;

    // modes
    private static final int MODE_DRAW = 0;
    private static final int MODE_SELECTION = 1;
    private static final int MODE_DRAW_BONDS = 2;
    private static final int MODE_FIRST = 0;
    private static final int MODE_LAST = 2;
    private static final int DEFAULT_MODE = 0;
    private int mode = MODE_DRAW;
    private static final String[] MODE_LABELS = new String[]{"Atom Draw Mode", "Select Mode", "Bond Draw"};

    private static ArrayList<Atom2D> selectedAtoms = new ArrayList<>();

    // the title
    private static String title = "Builder 2D - Molecular";

    /**
     * default constructor
     * @param width - the width of this frame
     * @param height - the height of this frame
     */
    public Frame(int width, int height, Molecule2D molecule) {
        super(title + " - " + MODE_LABELS[DEFAULT_MODE]);
        this.molecule = molecule;
        this.width = width;
        this.height = height;

        // set defaults
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setSize(width, height);
        this.setResizable(false);
    }

    // cycles the mode
    private void cycleMode() {
        this.mode++;
        if (this.mode > MODE_LAST) {
            this.mode = MODE_FIRST;
        }
        this.setTitle(title + " - " + MODE_LABELS[mode]);
        for (Atom2D a : selectedAtoms) {
            a.toggleHighlight();
        }
        selectedAtoms.clear();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: this.shouldClose = true; break;
            case KeyEvent.VK_BACK_SPACE: this.molecule.clear(); break;
            case KeyEvent.VK_ENTER: PTable.selectAtom(); break;
            case KeyEvent.VK_SPACE: cycleMode(); break;
            case KeyEvent.VK_P: molecule.print(); break;
        }
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
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) { // left click event
            Atom2D atom = molecule.findAtomAtPosition(mouseEvent.getX(), mouseEvent.getY());
            switch (mode) {
                case MODE_DRAW: molecule.addAtom(new Atom2D(mouseEvent.getX(), mouseEvent.getY(), 20, PTable.selectedAtom)); break;
                case MODE_SELECTION:
                    if (atom != null) {
                        atom.toggleHighlight();
                        selectedAtoms.add(atom);
                    }
                    break;
                case MODE_DRAW_BONDS: createBond(atom); break;
            }
        } else if (mouseEvent.getButton() == MouseEvent.BUTTON3) { // right click event
            Atom2D a = molecule.findAtomAtPosition(mouseEvent.getX(), mouseEvent.getY());
            if (a != null) {
                if (mode == MODE_DRAW) {
                    molecule.removeAtom(molecule.findAtomAtPosition(mouseEvent.getX(), mouseEvent.getY()));
                }
            }
        }
    }

    /**
     * creates a bond
     * @param atom - the atom that was selected
     */
    private void createBond(Atom2D atom) {
        if (atom != null && selectedAtoms.size() < 2) {
            atom.toggleHighlight();
            selectedAtoms.add(atom);
        }
        if (atom == null) {
            for (Atom2D a : selectedAtoms) {
                a.toggleHighlight();
            }
            selectedAtoms.clear();
        }
        if (selectedAtoms.size() == 2) {
            Bond2D bond = new Bond2D(selectedAtoms.get(0), selectedAtoms.get(1), 1);
            molecule.addBond(bond);
            for (Atom2D a : selectedAtoms) {
                a.toggleHighlight();
            }
            selectedAtoms.clear();
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

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }
}