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

    /**
     * gets a certain tetrahedral positional vector
     * @param index - the index
     * @return - the position or (0, 0, 0)
     */
    public static Vector3f getAtomCoord(int index) {
        try {
            return ATOM_COORDINATES[index];
        } catch (IndexOutOfBoundsException e) {
            return new Vector3f(0);
        }
    }

    /**
     * gets a certain tetrahedral positional vector
     * @param index - the index
     * @param magnitude - the magnitude to return it
     * @return - the position or (0, 0, 0)
     */
    public static Vector3f getAtomCoord(int index, float magnitude) {
        try {
            return Vector3f.normalize(ATOM_COORDINATES[index], magnitude);
        } catch (IndexOutOfBoundsException e) {
            return new Vector3f(0);
        }
    }
}