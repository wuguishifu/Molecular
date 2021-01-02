package com.bramerlabs.engine.math;

public class Position extends Vector3f {

    /**
     * default constructor for 3 specified values
     * @param x - the x position
     * @param y - the y position
     * @param z - the z position
     */
    public Position(float x, float y, float z) {
        super(x, y, z);
    }

    /**
     * default constructor
     */
    public Position() {
        super(0);
    }
}
