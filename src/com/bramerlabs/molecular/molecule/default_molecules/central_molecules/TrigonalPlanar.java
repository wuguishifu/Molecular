package com.bramerlabs.molecular.molecule.default_molecules.central_molecules;

import com.bramerlabs.engine.math.Vector3f;

public class TrigonalPlanar extends DefaultCentralMolecule{

    // vectors representing the direction of the bonds
    private final static float[][] positions=  new float[][]{{0, 0, -1}, {-0.86602540378444f, 0, 0.5f}, {0.86602540378444f, 0, 0.5f}};

    // the number of atoms in this molecule
    private final static int numAtoms = 4;

    // the bond order in this molecule
    private final static int bondOrder = 1;

    /**
     * constructor for specified values
     *
     * @param position - the position of the central atom
     * @param bondLength - the length of the bonds in this atom
     */
    public TrigonalPlanar(Vector3f position, float bondLength) {
        super(position, bondLength, positions, numAtoms, bondOrder);
    }
}