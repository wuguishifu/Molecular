package com.bramerlabs.engine.math;

public class Key3 {

    // the values of this key
    private int x, y, z;

    /**
     * default constructor
     * @param x - the first value of this key
     * @param y - the second value of this key
     */
    public Key3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * getter method
     * @return - the x value of this key
     */
    public int getX() {
        return this.x;
    }

    /**
     * getter method
     * @return - the y value of this key
     */
    public int getY() {
        return this.y;
    }

    /**
     * getter method
     * @return - the z value of this key
     */
    public int getZ() {
        return this.z;
    }

    /**
     * determines if two keys are equal
     * @param o - the other object
     * @return - true if the o is a key that is the same as this key
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Key3)) {
            return false;
        }
        Key3 key = (Key3) o;
        return this.x == key.x && this.y == key.y && this.z == key.z;
    }

    /**
     * creates a hashcode for this key
     * @return - the hashcode
     */
    @Override
    public int hashCode() {
        return 31 * x + 17 * y + z;
    }

}
