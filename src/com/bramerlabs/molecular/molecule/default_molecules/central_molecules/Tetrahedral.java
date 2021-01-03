package com.bramerlabs.molecular.molecule.default_molecules.central_molecules;

import com.bramerlabs.engine.math.Vector3f;

public class Tetrahedral extends DefaultCentralMolecule {

    // vectors representing the direction of the bonds
    private final static float[][] positions = new float[][]{{1, 1, -1}, {-1, 1, 1}, {-1, -1, -1}, {1, -1, 1}};

    // the number of atoms in this molecule
    private final static int numAtoms = 5;

    // the bond order in this molecule
    private final static int bondOrder = 1;

    /**
     * constructor for specified values
     *
     * @param position - the position of the central molecule
     * @param bondLength - the length of the bonds in this molecule
     */
    public Tetrahedral(Vector3f position, float bondLength) {
        super(position, bondLength, positions, numAtoms, bondOrder);
    }
}