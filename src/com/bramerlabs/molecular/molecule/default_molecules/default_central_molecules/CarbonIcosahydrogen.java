package com.bramerlabs.molecular.molecule.default_molecules.default_central_molecules;

import com.bramerlabs.engine.math.Vector3f;

public class CarbonIcosahydrogen extends DefaultCentralMolecule {

    // the golden ratio
    private static final float phi = 1.16180339f;
    private static final float phi2 = 1.16180339f*1.16180339f;

    // vectors representing the direction of the bonds
    private static final float[][] positions = new float[][]{
            {phi, phi, phi}, {-phi, phi, phi}, {phi, -phi, phi}, {phi, phi, -phi},
            {-phi, -phi, phi}, {-phi, phi, -phi}, {phi, -phi, -phi}, {-phi,-phi, -phi},
            {0, phi, 1}, {0, -phi, 1}, {0, phi, -1}, {0, -phi, -1},
            {phi2, 1, 0}, {-phi2, 1, 0}, {phi2, -1, 0}, {-phi2, -1, 0},
            {1, 0, phi2}, {-1, 0, phi2}, {1, 0, -phi2}, {-1, 0, -phi2}
    };

    // the number of atoms in this molecule
    private static int numAtoms = 21;

    // the bond order in this molecule
    private final static int bondOrder = 1;

    /**
     * constructor for specified values
     *
     * @param position - the position of the central molecule
     * @param bondLength - the length of the bonds in this molecule
     */
    public CarbonIcosahydrogen(Vector3f position, float bondLength) {
        super(position, bondLength, positions, numAtoms, bondOrder);
    }
}
