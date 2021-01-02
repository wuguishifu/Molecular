package com.bramerlabs.engine.math;

import java.util.Objects;

public class Vector2f {

    // the x and y components of this vector
    private float x, y;

    /**
     * default constructor for two specified values
     * @param x - the x component
     * @param y - the y component
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * constructor for 1 value
     * @param val - the value for both components to be set to
     */
    public Vector2f(float val) {
        this.x = val;
        this.y = val;
    }

    /**
     * creates an identity vector in the e1 direction
     * @return - the identity vector
     */
    public static Vector3f e1() {
        return new Vector3f(1, 0, 0);
    }

    /**
     * creates an identity vector in the e2 direction
     * @return - the identity vector
     */
    public static Vector3f e2() {
        return new Vector3f(0, 1, 0);
    }

    /**
     * sets the components of this vector
     * @param x - the x component
     * @param y - the y component
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * sets a specific component of this vector
     * @param c - which component to set
     * @param val - the value to be set
     */
    public void set(int c, float val) {
        switch (c) {
            case 0 : this.x = val; break;
            case 1 : this.y = val; break;
        }
    }

    /**
     * gets a specific value of this vector
     * @param c - which component to get
     * @return - the value
     */
    public float get(int c) {
        switch (c) {
            case 0 : return this.x;
            case 1 : return this.y;
        }
        return -1;
    }

    /**
     * adds values to each component of this vector
     * @param dx - the value to be added to the x component
     * @param dy - the value to be added to the y component
     * @return - this vector
     */
    public Vector2f add(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    /**
     * scales the entire vector
     * @param val - the value for this vector to be scaled by
     * @return - this vector
     */
    public Vector2f scale(float val) {
        this.x *= val;
        this.y *= val;
        return this;
    }

    /**
     * adds two vectors together
     * @param u - vector 1
     * @param v - vector 2
     * @return - a new vector u + v
     */
    public static Vector2f add(Vector2f u, Vector2f v) {
        return new Vector2f(u.x + v.x, u.y + v.y);
    }

    /**
     * subtracts two vectors
     * @param u - vector 1
     * @param v - vector 2
     * @return - a new vector u - v
     */
    public static Vector2f subtract(Vector2f u, Vector2f v) {
        return new Vector2f(u.x - v.x, u.y - v.y);
    }

    /**
     * element-wise multiplication of two vectors
     * @param u - vector 1
     * @param v - vector 2
     * @return - a new vector representing the element-wise multiplication of u, v
     */
    public static Vector2f multiply(Vector2f u, Vector2f v) {
        return new Vector2f(u.x * v.x, u.y * v.y);
    }

    /**
     * element-wise division of two vectors
     * @param u - vector 1
     * @param v - vector 2
     * @return - a new vector representing the element-wise division of u, v
     */
    public static Vector2f divide(Vector2f u, Vector2f v) {
        return new Vector2f(u.x / v.x, u.y / v.y);
    }

    /**
     * computes the dot product of two vectors
     * @param u - vector 1
     * @param v - vector 2
     * @return - the dot product (u dot v)
     */
    public static float dotProduct(Vector2f u, Vector2f v) {
        return u.x * v.x + u.y * v.y;
    }

    /**
     * determines the length of a vector
     * @param v - the vector
     * @return - the length
     */
    public static float length(Vector2f v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    /**
     * normalizes a vector
     * @param v - the vector
     * @return - the vector as a normal vector
     */
    public static Vector2f normalize(Vector2f v) {
        return Vector2f.divide(v, new Vector2f(length(v)));
    }

    /**
     * normalizes a vector to a specific length
     * @param v - the vector
     * @param length - the length to be normalized to
     * @return - the new vector
     */
    public static Vector2f normalize(Vector2f v, float length) {
        return (Vector2f.divide(v, new Vector2f(length(v)))).scale(length);
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
     * @return - the i component of this vector
     */
    public float getI() {
        return this.x;
    }

    /**
     * getter method
     * @return - the j component of this vector
     */
    public float getJ() {
        return this.y;
    }

    /**
     * sets the x component
     * @param val - the value
     */
    public void setX(float val) {
        this.x = val;
    }

    /**
     * sets the y component
     * @param val - the value
     */
    public void setY(float val) {
        this.y = val;
    }

    /**
     * sets the i component
     * @param val - the value
     */
    public void setI(float val) {
        this.x = val;
    }

    /**
     * sets the j component
     * @param val - the value
     */
    public void setJ(float val) {
        this.y = val;
    }

    /**
     * determines if two vectors are exactly identical
     * @param o - the other object
     * @return - true if this and o are both vectors that are exactly equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2f)) return false;
        Vector2f vector2f = (Vector2f) o;
        return this.x == vector2f.x && this.y == vector2f.y;
    }

    /**
     * determines if two vectors are nearly identical
     * @param o - the other object
     * @param epsilon - the max error
     * @return - true if this and o are both vectors and the absolute error for each component is less than epsilon
     */
    public boolean equals(Object o, float epsilon) {
        if (this == o) return true;
        if (!(o instanceof Vector2f)) return false;
        Vector2f vector2f = (Vector2f) o;
        for (int i = 0; i < 2; i++) {
            if (Math.abs(this.get(i) - vector2f.get(i)) > epsilon) {
                return false;
            }
        }
        return true;
    }

    /**
     * generates a hashcode of this vector
     * @return - the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
