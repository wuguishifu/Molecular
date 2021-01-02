package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;

public class Vertex {

    // the position of this vertex
    private Vector3f position;

    // the texture coordinate of this vertex, to be used with shaders
    private Vector2f textureCoord;

    /**
     * default constructor for specified position and texture coordinate
     * @param position - the position of this vertex
     * @param textureCoord - the texture coordinate of this vertex
     */
    public Vertex(Vector3f position, Vector2f textureCoord) {
        this.position = position;
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
     * @return - the texture coordinate, to be used with shaders
     */
    public Vector2f getTextureCoord() {
        return this.textureCoord;
    }

}
