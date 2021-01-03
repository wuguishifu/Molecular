package com.bramerlabs.molecular.molecule.default_molecules.central_molecules;

import com.bramerlabs.engine.math.Vector3f;

public class Planar extends DefaultCentralMolecule {

    // vectors representing the direction of the bonds
    private final static float[][] positions = new float[][]{{1, 0, 0}, {-1, 0, 0}};

    // the number of atoms in this molecule
    private final static int numAtoms = 3;

    // the bond order in this molecule
    private final static int bondOrder = 2;

    /**
     * constructor for specified values
     *
     * @param position   - the position of the central molecule
     * @param bondLength - the length of the bonds in this molecule
     */
    public Planar(Vector3f position, float bondLength) {
        super(position, bondLength, positions, numAtoms, bondOrder);
    }
}