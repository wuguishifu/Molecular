package com.bramerlabs.molecular.molecule.default_molecule;

import com.bramerlabs.engine.math.Vector3f;

public class Octahedral extends DefaultMolecule {

    // vectors representing the direction of the bonds
    private final static float[][] positions = new float[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {-1, 0, 0}, {0, -1, 0}, {0, 0, -1}};

    // the number of atoms in this molecule
    private final static int numAtoms = 7;

    // the bond order in this molecule
    private final static int bondOrder = 1;

    /**
     * constructor for specified values
     *
     * @param position - the position of the central molecule
     * @param bondLength - the length of the bonds in this molecule
     */
    public Octahedral(Vector3f position, float bondLength) {
        super(position, bondLength, positions, numAtoms, bondOrder);
    }

}
