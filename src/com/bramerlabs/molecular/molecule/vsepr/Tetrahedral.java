package com.bramerlabs.molecular.molecule.vsepr;

import com.bramerlabs.engine.math.Vector3f;

public class Tetrahedral {

    // the value sqrt(2)/3
    private static final float R2O3 = 0.866025f;

    // vectors representing the coordinates of external atoms
    private static final Vector3f[] ATOM_COORDINATES = new Vector3f[]{
            new Vector3f(-R2O3, -0.5f, 0), // lower left
            new Vector3f(R2O3, -0.5f, 0), // lower right
            new Vector3f(0, 0.5f, -R2O3), // upper forward
            new Vector3f(0, 0.5f, R2O3) // upper back
    };



}
