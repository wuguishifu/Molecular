package com.bramerlabs.molecular.molecule.default_molecules.default_central_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.DefaultAtom;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class DefaultCentralMolecule extends Molecule {

    // the radius of the atoms in this molecule
    private static final float atomicRadius = 1f;

    // the color of the atoms in this molecule
    private static Vector3f c1 = new Vector3f(0.4f); // center atom
    private static Vector3f c2 = new Vector3f(0.8f); // auxiliary atoms

    // vectors representing the direction of the bonds
    private float[][] positions;

    // the number of atoms in this molecule
    private int numAtoms;

    /**
     * constructor for specified values
     * @param position - the position of the central molecule
     * @param bondLength - the length of the bonds in this molecule
     * @param positions - vectors corresponding to the direction of each auxiliary atom
     * @param numAtoms - the number of atoms (including the central atom) in this molecule
     * @param bondOrder - the bond order in this atom
     */
    public DefaultCentralMolecule(Vector3f position, float bondLength, float[][] positions, int numAtoms, int bondOrder) {
        super(position, new ArrayList<>(), new ArrayList<>());

        this.positions = positions;
        this.numAtoms = numAtoms;

        // create the atoms
        this.addAtom(new DefaultAtom(position, atomicRadius, c1)); // create the central atom
        for (float[] p : positions) { // create auxiliary atoms
            this.addAtom(new DefaultAtom(Vector3f.add(Vector3f.normalize(new Vector3f(p), bondLength), position), atomicRadius, c2));
        }

        // create the bonds
        for (int i = 1; i < numAtoms; i++) {
            this.addBond(new Bond(this.getAtoms().get(i), this.getAtoms().get(0), bondOrder));
        }
    }
}