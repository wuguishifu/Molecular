package com.bramerlabs.molecular.molecule.default_molecule;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Benzene extends Molecule {

    // the radius of the atoms in this molecule
    private static final float atomicRadius = 1f;

    // the color of the atoms in this molecule
    private static Vector3f c1 = new Vector3f(0.4f); // carbon atoms
    private static Vector3f c2 = new Vector3f(0.8f); // hydrogen atoms

    // vectors representing the direction of the bonds
    private final static float[][] positions = new float[][]{{0, 0, 1}, {-0.866025f, 0, 0.5f}, {-0.866025f, 0, -0.5f}, {0, 0, -1}, {0.866025f, 0, -0.5f}, {0.866025f, 0, 0.5f}};

    // the number of atoms in this molecule
    private final static int numAtoms = 6;

    /**
     * constructor for specified values
     *
     * @param position - the position of the central molecule
     * @param bondLength - the length of the bonds in this molecule
     */
    public Benzene(Vector3f position, float bondLength) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // create the atoms
        for (float[] p : positions) {
            this.addAtom(new Atom(Vector3f.add(Vector3f.normalize(new Vector3f(p), bondLength), position), 2*atomicRadius/3, c1));
        }

        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(0), 1));
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(1), 2));
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(2), 1));
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(3), 2));
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(4), 1));
        this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(5), 2));

        for (float[] p : positions) {
            this.addAtom(new Atom(Vector3f.add(Vector3f.normalize(new Vector3f(p), bondLength * 2), position), atomicRadius/2, c2));
        }

        this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(6), 1));
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(7), 1));
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(8), 1));
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(9), 1));
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(10), 1));
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(11), 1));

    }

}