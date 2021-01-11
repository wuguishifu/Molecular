package com.bramerlabs.engine.math;

public class Key2 {

    // the values of this key
    private int x, y;

    /**
     * default constructor
     * @param x - the first value of this key
     * @param y - the second value of this key
     */
    public Key2(int x, int y) {
        this.x = x;
        this.y = y;
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
     * determines if two keys are equal
     * @param o - the other object
     * @return - true if the o is a key that is the same as this key
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Key2)) {
            return false;
        }
        Key2 key = (Key2) o;
        return this.x == key.x && this.y == key.y;
    }

    /**
     * creates a hashcode for this key
     * @return - the hashcode
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

}
