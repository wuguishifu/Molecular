package com.bramerlabs.molecular.molecule.default_molecule;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Tetrahedral extends Molecule {

    // the radius of the atoms in this molecule
    private static final float atomicRadius = 1f;

    // the color of the atoms in this molecule
    private static Vector3f c1 = new Vector3f(0.2f); // center atom
    private static Vector3f c2 = new Vector3f(0.8f); // auxiliary atoms

    // vectors representing the direction of the bonds
    private final static float[][] positions = new float[][]{{1, 1, -1}, {-1, 1, 1}, {-1, -1, -1}, {1, -1, 1}};
    public final static int numAtoms = 5;

    /**
     * constructor for specified position
     * @param position - the position of the central atom
     */
    public Tetrahedral(Vector3f position, float bondLength) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // create the atoms
        this.addAtom(new Atom(position, atomicRadius, c1)); // create central atom
        for (float[] p : positions) { // create auxiliary atoms
            this.addAtom(new Atom(Vector3f.add(Vector3f.normalize(new Vector3f(p), bondLength), position), atomicRadius, c2));
        }

        // create the bonds
        for (int i = 1; i < numAtoms; i++) {
            this.addBond(new Bond(this.getAtoms().get(i), this.getAtoms().get(0), 1));
        }
    }
}
