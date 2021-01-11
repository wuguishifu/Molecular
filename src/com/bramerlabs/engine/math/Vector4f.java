package com.bramerlabs.engine.math;

import java.util.Objects;

public class Vector4f {

    // the x, y, z, w components of this vector
    private float x, y, z, w;

    /**
     * default constructor for all specified values
     * @param x - the x component of this vector
     * @param y - the y component of this vector
     * @param z - the z component of this vector
     * @param w - the w component of this vector
     */
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * default constructor for one specified values
     * @param val - the value for all 4 components to be set to
     */
    public Vector4f(float val) {
        this.x = val;
        this.y = val;
        this.z = val;
        this.w = val;
    }

    /**
     * constructor for specified vector
     * @param v - the other vector
     */
    public Vector4f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * constructor from a float array
     * @param v - a float array containing values in form [x, y, z, w]
     * method will automatically set the position based off of available variables.
     * No minimum or maximum necessary supplied variables, if more than 4 are supplied the vector
     * will be constructed out of the first 4.
     */
    public Vector4f(float[] v) {
        x = v.length > 0 ? v[0] : 0;
        y = v.length > 1 ? v[1] : 0;
        z = v.length > 2 ? v[2] : 0;
        w = v.length > 3 ? v[3] : 0;
    }

    /**
     * constructor from a 3-vector and a w component
     * @param v - the 3-vector
     * @param w - the w component to be tacked on
     */
    public Vector4f(Vector3f v, float w) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        this.w = w;
    }

    /**
     * constructor from a 2-vector and z, w components
     * @param v - the 2-vector
     * @param z - the z component to be added
     * @param w - the w component to be added
     */
    public Vector4f(Vector2f v, float z, float w) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = z;
        this.w = w;
    }

    /**
     * creates an identity vector in the E1 direction
     * @return - the identity vector
     */
    public static Vector4f E1() {
        return new Vector4f(1, 0, 0, 0);
    }

    /**
     * creates an identity vector in the E2 direction
     * @return - the identity vector
     */
    public static Vector4f E2() {
        return new Vector4f(0, 1, 0, 0);
    }

    /**
     * creates an identity vector in the E3 direction
     * @return - the identity vector
     */
    public static Vector4f E3() {
        return new Vector4f(0, 0, 1, 0);
    }

    /**
     * creates an identity vector in the E4 direction
     * @return - the identity vector
     */
    public static Vector4f E4() {
        return new Vector4f(0, 0, 0, 1);
    }

    /**
     * setter method for all new specified components
     * @param x - the value for the x component to be set to
     * @param y - the value for the y component to be set to
     * @param z - the value for the z component to be set to
     * @param w - the value for the z component to be set to
     */
    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * setter method
     * @param x - the value for the x component to be set to
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * setter method
     * @param y - the value for the y component to be set to
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * setter method
     * @param z - the value for the z component to be set to
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * setter method
     * @param w - the value for the w component to be set to
     */
    public void setW(float w) {
        this.w = w;
    }

    /**
     * setter method
     * @param c - which component to set
     * @param v - the value for component c to be set to
     */
    public void set(int c, float v) {
        switch (c) {
            case 0 : this.x = v; break;
            case 1 : this.y = v; break;
            case 2 : this.z = v; break;
            case 3 : this.w = v; break;
        }
    }

    /**
     * getter method
     * @return - all the components in an array
     */
    public float[] getAll() {
        return new float[]{x, y, z, w};
    }

    /**
     * getter method
     * @return - the x component of this vector
     */
    public float getX() {
        return this.x;
    }

    /**
     * getter method
     * @return - the y component of this vector
     */
    public float getY() {
        return this.y;
    }

    /**
     * getter method
     * @return - the z component of this vector
     */
    public float getZ() {
        return this.z;
    }

    /**
     * getter method
     * @return - the w component of this vector
     */
    public float getW() {
        return this.w;
    }

    /**
     * getter method
     * @param c - which component to be returned
     * @return - the value of component c
     */
    public float get(int c) {
        switch (c) {
            case 0 : return this.x;
            case 1 : return this.y;
            case 2 : return this.z;
            case 3 : return this.w;
        }
        return -1;
    }

    /**
     * converts the x, y, z components of this vector into a 3-vector
     * @return - the 3-vector
     */
    public Vector3f getXYZ() {
        return new Vector3f(x, y, z);
    }

    /**
     * converts the x, y, z components of this vector into a 3-vector
     * @return - the 3-vector
     */
    public Vector3f toVector3f() {
        return getXYZ();
    }

    /**
     * converts the x, y components of this vector into a 2-vector
     * @return - the 2-vector
     */
    public Vector2f getXY() {
        return new Vector2f(x, y);
    }

    /**
     * converts this vector4f into a float array
     * @return - a 4d array
     */
    public float[] toFloatArray() {
        return new float[]{x, y, z, w};
    }

    /**
     * determines if two vectors are exactly identical
     * @param o - the other object
     * @return - true if this and o are both vectors that are exactly equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector4f)) return false;
        Vector4f other = (Vector4f) o;
        return this.x == other.x && this.y == other.y && this.z == other.z && this.w == other.w;
    }

    /**
     * determines if two vectors are nearly identical
     * @param o - the other object
     * @param epsilon - the max error
     * @return - true if this and o are both vectors and the absolute error for each component is less than epsilon
     */
    public boolean equals(Object o, float epsilon) {
        if (this == o) return true;
        if (!(o instanceof Vector4f)) return false;
        Vector4f other = (Vector4f) o;
        for (int i = 0; i < 4; i++) {
            if (Math.abs(this.get(i) - other.get(i)) > epsilon) {
                return false;
            }
        }
        return true;
    }

    /**
     * creates a hashcode of this vector
     * @return - the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }
}