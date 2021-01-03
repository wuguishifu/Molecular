package com.bramerlabs.molecular.molecule;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Molecule {

    // a list of atoms in this molecule
    private ArrayList<Atom> atoms;

    // a list of bonds in this molecule
    private ArrayList<Bond> bonds;

    // the position of the central atom in this molecule
    private Vector3f position;

    /**
     * constructor for specified atoms and bonds
     * @param position - the position of the central atom
     * @param atoms - the atoms making up this molecule
     * @param bonds - the bonds making up this molecule
     */
    public Molecule(Vector3f position, ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        this.position = position;
        this.atoms = atoms;
        this.bonds = bonds;
    }

    /**
     * getter method
     * @return - the list of atoms in this molecule
     */
    public ArrayList<Atom> getAtoms() {
        return this.atoms;
    }

    /**
     * getter method
     * @return - the list of bonds in this molecule
     */
    public ArrayList<Bond> getBonds() {
        return this.bonds;
    }

    /**
     * adds an atom to this molecule
     * @param a - the atom to be added
     */
    public void addAtom(Atom a) {
        this.atoms.add(a);
    }

    /**
     * adds a bond to this molecule
     * @param b - the bond to be added
     */
    public void addBond(Bond b) {
        this.bonds.add(b);
    }

    /**
     * removes an atom from this molecule
     * @param a - the atom to be removed
     */
    public void removeAtom(Atom a) {
        ArrayList<Bond> bondsToRemove = new ArrayList<>();
        this.atoms.remove(a);
        for (Bond b : bonds) {
            if (b.getAtoms().contains(a)) {
                bondsToRemove.add(b);
            }
        }
        for (Bond b : bondsToRemove) {
            this.removeBond(b);
        }
    }

    /**
     * removes a bond from this molecule
     * @param b - the bond to be removed
     */
    public void removeBond(Bond b) {
        this.bonds.remove(b);
    }

    /**
     * getter method
     * @return - the position of the central atom in this molecule
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * destroys the molecule
     */
    public void destroy() {
        for (Atom a : atoms) {
            a.getSphere().destroy();
        }
        for (Bond b : bonds) {
            b.destroy();
        }
    }

    /**
     * getter method
     * @return - the central atom in this molecule
     */
    public Atom getCentralAtom() {
        return this.atoms.get(0);
    }
}