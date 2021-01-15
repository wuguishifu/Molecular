package com.bramerlabs.engine.math;

import java.util.Objects;

public class Vector3f {

    // the x, y, z components of this vector
    private float x, y, z;

    /**
     * default constructor for three specified values
     * @param x - the x component of this vector
     * @param y - the y component of this vector
     * @param z - the z component of this vector
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * constructor for one specified value
     * @param value - the value for all 3 components to be set to
     */
    public Vector3f(float value) {
        this.x = value;
        this.y = value;
        this.z = value;
    }

    /**
     * constructor for a specified vector
     * @param v - the other vector
     */
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * constructor from a float array
     * @param position - a float array containing position in form [x, y, z].
     * method will automatically set the position based off of available variables.
     * no minimum or maximum supplied variables, if more than 3 position will be first 3.
     */
    public Vector3f(float[] position) {
        x = position.length > 0 ? position[0] : 0;
        y = position.length > 1 ? position[1] : 0;
        z = position.length > 2 ? position[2] : 0;
    }

    /**
     * constructor from a vector2f and a z value
     * @param v - the 2 vector
     * @param z - the z value
     */
    public Vector3f(Vector2f v, float z) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = z;
    }

    /**
     * gets a random vector3f
     * @param min - the min value for each component
     * @param max - the max value for each component
     * @return - a new vector3f with 3 random components each between min, max
     */
    public static Vector3f getRandom(float min, float max) {
        float x = (float) (Math.random() * (max - min) + min);
        float y = (float) (Math.random() * (max - min) + min);
        float z = (float) (Math.random() * (max - min) + min);
        return new Vector3f(x, y, z);
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
     * creates an identity vector in the e3 direction
     * @return - the identity vector
     */
    public static Vector3f e3() {
        return new Vector3f(0, 0, 1);
    }

    /**
     * sets the components of this vector
     * @param x - the new x component
     * @param y - the new y component
     * @param z - the new z component
     */
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
            case 2 : this.z = val; break;
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
            case 2 : return this.z;
        }
        return -1;
    }

    /**
     * returns the negative of a vector
     * @param v - the vector
     * @return - the negative
     */
    public static Vector3f negative(Vector3f v) {
        return new Vector3f(-v.x, -v.y, -v.z);
    }

    /**
     * adds values to each component of this vector
     * @param dx - the value to be added to the x component
     * @param dy - the value to be added to the y component
     * @param dz - the value to be added to the z component
     * @return - this vector
     */
    public Vector3f add(float dx, float dy, float dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
        return this;
    }

    /**
     * scales the entire vector
     * @param val - the value for this vector to be scaled by
     * @return - this vector
     */
    public Vector3f scale(float val) {
        this.x *= val;
        this.y *= val;
        this.z *= val;
        return this;
    }

    /**
     * adds two vectors together
     * @param u - vector 1
     * @param v - vector 2
     * @return - a new vector u + v
     */
    public static Vector3f add(Vector3f v, Vector3f u) {
        return new Vector3f(v.x + u.x, v.y + u.y, v.z + u.z);
    }

    /**
     * subtracts two vectors
     * @param u - vector 1
     * @param v - vector 2
     * @return - a new vector u - v
     */
    public static Vector3f subtract(Vector3f u, Vector3f v) {
        return new Vector3f(u.x - v.x, u.y - v.y, u.z - v.z);
    }

    /**
     * element-wise multiplication of two vectors
     * @param vector1 - vector 1
     * @param vector2 - vector 2
     * @return - a new vector representing the element-wise multiplication of u, v
     */
    public static Vector3f multiply(Vector3f vector1, Vector3f vector2) {
        return new Vector3f(vector1.getX() * vector2.getX(), vector1.getY() * vector2.getY(), vector1.getZ() * vector2.getZ());
    }

    /**
     * scales a vector
     * @param vector1 - the vector
     * @param scaleFactor - the scale factor
     * @return - a new vector representing the scalar multiplication of the vector and float
     */
    public static Vector3f scale(Vector3f vector1, float scaleFactor) {
        return new Vector3f(vector1.getX() * scaleFactor, vector1.getY() * scaleFactor, vector1.getZ() * scaleFactor);
    }

    /**
     * element-wise division of two vectors
     * @param vector1 - vector 1
     * @param vector2 - vector 2
     * @return - a new vector representing the element-wise division of u, v
     */
    public static Vector3f divide(Vector3f vector1, Vector3f vector2) {
        return new Vector3f(vector1.getX() / vector2.getX(), vector1.getY() / vector2.getY(), vector1.getZ() / vector2.getZ());
    }

    /**
     * computes the dot product of two vectors
     * @param vector1 - vector 1
     * @param vector2 - vector 2
     * @return - the dot product (u dot v)
     */
    public static float dot(Vector3f vector1, Vector3f vector2) {
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY() + vector1.getZ() * vector2.getZ();
    }

    /**
     * takes the cross product of two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - the cross product of v and u (v x u)
     */
    public static Vector3f cross(Vector3f v, Vector3f u) {
        float x = v.y * u.z - v.z * u.y;
        float y = v.z * u.x - v.x * u.z;
        float z = v.x * u.y - v.y * u.x;
        return new Vector3f(x, y, z);
    }

    /**
     * determines the length between two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - the length between vector 1 and vector 2
     */
    public static float distance(Vector3f v, Vector3f u) {
        return length(multiply(subtract(u, v), subtract(u, v)));
    }

    /**
     * determines the length of a vector
     * @param vector - the vector
     * @return - the length
     */
    public static float length(Vector3f vector) {
        return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY() + vector.getZ() * vector.getZ());
    }

    /**
     * normalizes a vector
     * @param v - the vector
     * @return - the vector as a normal vector
     */
    public static Vector3f normalize(Vector3f v) {
        return Vector3f.divide(v, new Vector3f(length(v)));
    }

    /**
     * normalizes a vector to a specific length
     * @param v - the vector
     * @param length - the length to be normalized to
     * @return - the new vector
     */
    public static Vector3f normalize(Vector3f v, float length) {
        return (Vector3f.divide(v, new Vector3f(length(v)))).scale(length);
    }

    /**
     * determines the midpoint of the two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - a vector representing the midpoint of the two vectors
     */
    public static Vector3f midpoint(Vector3f v, Vector3f u) {
        return new Vector3f((v.x + u.x) / 2, (v.y + u.y) / 2, (v.z + u.z) / 2);
    }

    /**
     * determines the angle between two vectors
     * @param v - vector 1
     * @param u - vector 2
     * @return - the angle between
     */
    public static float angleBetween(Vector3f v, Vector3f u) {
        return (float) Math.acos(Vector3f.dot(v, u) * quickInverseSqrt(v) * quickInverseSqrt(u));
    }

    /**
     * quickly determines the inverse magnitude of a vector using the Fast Inverse Square Root formula
     * @param v - the vector
     * @return - the magnitude
     */
    public static float quickInverseSqrt(Vector3f v) {
        float val = v.x * v.x + v.y * v.y + v.z * v.z;
        float xHalf = 0.5f * val;
        int i = Float.floatToIntBits(val);
        i = 0x5f3759df - (i >> 1);
        val = Float.intBitsToFloat(i);
        val *= (1.5f - xHalf * val * val);
        return val;
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
     * getter method
     * @return - the k component of this vector
     */
    public float getK() {
        return this.z;
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
     * sets the z component
     * @param val - the value
     */
    public void setZ(float val) {
        this.z = val;
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
     * sets the k component
     * @param val - the value
     */
    public void setK(float val) {
        this.z = val;
    }

    /**
     * determines if two vectors are exactly identical
     * @param o - the other object
     * @return - true if this and o are both vectors that are exactly equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3f)) return false;
        Vector3f other = (Vector3f) o;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    /**
     * determines if two vectors are nearly identical
     * @param o - the other object
     * @param epsilon - the max error
     * @return - true if this and o are both vectors and the absolute error for each component is less than epsilon
     */
    public boolean equals(Object o, float epsilon) {
        if (this == o) return true;
        if (!(o instanceof Vector3f)) return false;
        Vector3f other = (Vector3f) o;
        for (int i = 0; i < 3; i++) {
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
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
