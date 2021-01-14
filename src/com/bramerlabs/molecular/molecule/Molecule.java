package com.bramerlabs.molecular.molecule;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.shapes.Cylinder;
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
     * default update method - override in child class
     */
    public void update(int time) {}

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
     * sets the list of atoms
     * @param atoms - the list of atoms
     */
    public void setAtoms(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }

    /**
     * sets the list of bonds
     * @param bonds - the list of bonds
     */
    public void setBonds(ArrayList<Bond> bonds) {
        this.bonds = bonds;
    }

    /**
     * sets the position of the molecule
     * @param position - the position
     */
    public void setPosition(Vector3f position) {
        this.position = position;
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
     * gets an atom
     * @param ID - the ID of the atom
     * @return - the atom corresponding to this ID
     */
    public Atom getAtom(int ID) {
        for (Atom a : this.atoms) {
            if (a.getSphere().getID() == ID) {
                return a;
            }
        }
        return null;
    }

    /**
     * gets a bond
     * @param ID - the ID of the bond
     * @return - the bond corresponding to this ID
     */
    public Bond getBond(int ID) {
        for (Bond b : this.bonds) {
            for (Cylinder c : b.getCylinders()) {
                if (c.getID() == ID) {
                    return b;
                }
            }
        }
        return null;
    }

    /**
     * gets an atom with a specific ID
     * @param ID - the ID
     * @return - the atom
     */
    public Atom getAtomFromAtomID(int ID) {
        for (Atom a : this.atoms) {
            if (a.getID() == ID) {
                return a;
            }
        }
        return null;
    }

    /**
     * gets a bond with a specifid ID
     * @param ID - the ID
     * @return - the bond
     */
    public Bond getBondFromBondID(int ID) {
        for (Bond b : this.bonds) {
            if (b.getID() == ID) {
                return b;
            }
        }
        return null;
    }

    /**
     * getter method
     * @return - the central atom in this molecule
     */
    public Atom getCentralAtom() {
        return this.atoms.get(0);
    }
}