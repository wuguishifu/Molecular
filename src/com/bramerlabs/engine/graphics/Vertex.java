package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;

public class Vertex {

    // the position of this vertex
    private Vector3f position;

    // the color of this vertex, to be used with shaders
    private Vector3f color;

    // a vector normal to this vertex
    private Vector3f normal;

    private Vector2f textureCoord;

    /**
     * default constructor for specified position and texture coordinate
     * @param position - the position of this vertex
     * @param color - the color of this vertex
     */
    public Vertex(Vector3f position, Vector3f color, Vector3f normal) {
        this.position = position;
        this.color = color;
        this.normal = normal;
    }

    /**
     * constructs a vertex with no normal vector (used for GUI rendering)
     * @param position - the position of the vertex
     * @param color - the color of the vertex
     */
    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
        this.normal = new Vector3f(0, 0, 1.f);
    }

    /**
     * constructs a vertex with no normal vector and a texture coord (used for GUI rendering)
     * @param position - the position of the vertex
     * @param textureCoord - the texture coord of the vertex
     */
    public Vertex(Vector2f position, Vector2f textureCoord) {
        this.position = new Vector3f(position, -1.0f);
        this.textureCoord = textureCoord;
    }

    /**
     * getter method
     * @return - the position of this vertex
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * getter method
     * @return - the color, to be used with shaders
     */
    public Vector3f getColor() {
        return this.color;
    }

    /**
     * getter method
     * @return - the normal vector
     */
    public Vector3f getNormal() {
        return this.normal;
    }

    /**
     * getter method
     * @return - the texture coord of this vertex
     */
    public Vector2f getTextureCoord() {
        return this.textureCoord;
    }

}
