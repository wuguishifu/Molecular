package com.bramerlabs.molecular.builder_2d.molecule_2d;

import java.awt.*;
import java.util.ArrayList;

public class Molecule2D {

    // the atoms and bonds in the molecule
    public ArrayList<Atom2D> atoms;
    public ArrayList<Bond2D> bonds;

    /**
     * default constructor
     * @param atoms - the list of atoms in this molecule
     * @param bonds - the list of bonds in this molecule
     */
    public Molecule2D(ArrayList<Atom2D> atoms, ArrayList<Bond2D> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;
    }

    /**
     * adds an atom to this molecule
     * @param a - the atom to be added
     */
    public void addAtom(Atom2D a) {
        this.atoms.add(a);
    }

    /**
     * adds a bond to this molecule
     * @param bond - the bond to be added
     */
    public void addBond(Bond2D bond) {
        boolean exists = false;
        Bond2D existentBond = null;
        for (Bond2D b : bonds) {
            if (b.equals(bond)) {
                exists = true;
                existentBond = b;
                break;
            }
        }
        if (exists) {
            existentBond.incOrder();
        } else {
            this.bonds.add(bond);
        }
    }

    /**
     * removes an atom from this molecule
     * @param a - the atom to be removed
     */
    public void removeAtom(Atom2D a) {
        this.atoms.remove(a);
    }

    /**
     * removes a bond from this molecule
     * @param b - the bond to be removed
     */
    public void removeBond(Bond2D b) {
        this.bonds.remove(b);
    }

    /**
     * empties the molecule
     */
    public void clear() {
        this.atoms.clear();
        this.bonds.clear();
    }

    /**
     * chooses an atom under the mouse coordinates
     * @param x - the x mouse coordinate
     * @param y - the y mouse coordinate
     * @return - an atom under the mouse coordinate or null
     */
    public Atom2D findAtomAtPosition(int x, int y) {
        // iterate over every atom
        for (int i = atoms.size()-1; i >= 0; i--) {
            Atom2D a = atoms.get(i);
            int ax = a.getX();
            int ay = a.getY();
            int dx = ax - x;
            int dy = ay - y;
            if (Math.sqrt(dx * dx + dy * dy) <= a.getRadius()) {
                return a;
            }
        }
        return null;
    }

    /**
     * paints this molecule using java.awt
     * @param g - the graphics component handed down by panel.repaint()
     */
    public void paint(Graphics g) {
        for (Bond2D b : bonds) {
            b.paint(g);
        }
        for (Atom2D a : atoms) {
            a.paint(g);
        }
    }

    /**
     * getter method
     * @return - the list of atoms in this array
     */
    public ArrayList<Atom2D> getAtoms() {
        return this.atoms;
    }

    /**
     * prints out the molecule
     */
    public void print() {
        System.out.println("pos 0.0 0.0 0.0");
        for (Atom2D a : atoms) {
            a.print();
        }
        for (Bond2D b : bonds) {
            b.print();
        }
    }
}