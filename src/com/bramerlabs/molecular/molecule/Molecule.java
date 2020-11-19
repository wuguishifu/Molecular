package com.bramerlabs.molecular.molecule;

import com.bramerlabs.molecular.molecule.atom.Atom;

import java.util.ArrayList;

public class Molecule {

    // list of atoms that this molecule is made up of
    private ArrayList<Atom> atoms;

    // position stored in the form [x, y, z] of the center of the molecule
    private float[] position;

    // default position
    private final float[] defaultPosition = {0, 0, 0};

    /**
     * default constructor
     */
    public Molecule() {
        this.atoms = new ArrayList<>();
        this.position = defaultPosition;
    }

    /**
     * constructor with specified position
     * @param position - the position of the molecule
     */
    public Molecule(float[] position) {
        this.atoms = new ArrayList<>();
        if (position.length == 3) {
            this.position = position;
        } else {
            this.position = defaultPosition;
        }
    }

    /**
     * adds an atom to the molecule
     * @param atom - the atom to be added
     */
    public void addAtom(Atom atom) {
        this.atoms.add(atom);
    }

    /**
     * adds an atom to the molecule at a specified position
     * @param atom - the atom to be added
     * @param position - the position of the atom
     */
    public void addAtom(Atom atom, float[] position) {
        if (position.length == 3) {
            atom.setPosition(position);
        }
        this.atoms.add(atom);
    }

    /**
     * adds a group of atoms
     * @param atoms - group of atoms
     */
    public void addAtoms(ArrayList<Atom> atoms) {
        this.atoms.addAll(atoms);
    }

    /**
     * adds a group of atoms at specified positions with a single specified identity
     * @param positions - an array of arrays containing positions in the form [x, y, z]
     */
    public void addAtoms(float[][] positions, int[] identity) {
        for (float[] pos : positions) {
            this.atoms.add(new Atom(pos).setIdentity(identity));
        }
    }

    /**
     * getter method
     * @return - the position of the molecule
     */
    public float[] getPosition() {
        return this.position;
    }

    /**
     * gets the index of an atom
     * @param atom - the atom (or copy of) to find the index of
     * @return - the index of the atom in the ArrayList
     */
    public int indexOf(Atom atom) {
        return atoms.indexOf(atom);
    }

    /**
     * removes an atom from the ArrayList
     * @param atom - the atom (or copy of) to remove from the ArrayList
     */
    public void removeAtom(Atom atom) {
        atoms.remove(atom);
    }

    /**
     * getter method
     * @return - the list of atoms that makes up this molecule
     */
    public ArrayList<Atom> getAtoms() {
        return this.atoms;
    }
}
