package com.bramerlabs.engine.graphics;

import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Mesh {

    // the vertices and indices of this mesh
    private Vertex[] vertices;
    private int[] indices;

    // vertex array object
    private int vao;

    // position buffer object
    private int pbo;

    // color buffer object
    private int cbo;

    // index buffer object
    private int ibo;

    // normal vector buffer object
    private int nbo;

    /**
     * default constructor for specified vertices and indices, and specified material
     * @param vertices - the vertices of this mesh
     * @param indices - the indices of this mesh
     */
    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    /**
     * constructor for specified list of vertices
     * @param vertices - the list of vertices
     */
    public Mesh(ArrayList<Vertex> vertices) {
        this.vertices = new Vertex[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            this.vertices[i] = vertices.get(i);
        }

        this.indices = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            this.indices[i] = i;
        }
    }

    /**
     * creates the mesh
     */
    public void create() {
        // generate and bind the vertex array
        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);

        // create the buffers
        makePositionBuffer();
        makeColorBuffer();
        makeNormalBuffer();
        makeIndexBuffer();
    }

    /**
     * helper method to create the position buffer object
     */
    private void makePositionBuffer() {
        // preallocate memory
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);

        // create a new temp array to store position data
        float[] positionData = new float[vertices.length * 3];

        // add all the position data to the temp array
        for (int i = 0; i < vertices.length; i++) {
            positionData[i * 3    ] = vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
        }

        // flip the data to make it handleable by OpenGL
        positionBuffer.put(positionData).flip();

        // store the position data in the position buffer object
        pbo = storeData(positionBuffer, 0, 3);
    }

    /**
     * helper method to create the color buffer object
     */
    public void makeColorBuffer() {
        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] colorData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            colorData[i * 3] = vertices[i].getColor().getX();
            colorData[i * 3 + 1] = vertices[i].getColor().getY();
            colorData[i * 3 + 2] = vertices[i].getColor().getZ();
        }
        colorBuffer.put(colorData).flip();

        cbo = storeData(colorBuffer, 1, 3);
    }

    /**
     * helper method to create a buffer object for the vector normal to the surface at every vertex
     */
    public void makeNormalBuffer() {
        FloatBuffer normalBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] normalData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            normalData[i * 3] = vertices[i].getNormal().getX();
            normalData[i * 3 + 1] = vertices[i].getNormal().getY();
            normalData[i * 3 + 2] = vertices[i].getNormal().getZ();
        }
        normalBuffer.put(normalData).flip();

        nbo = storeData(normalBuffer, 2, 3);
    }

    /**
     * helper method to create the index buffer object
     */
    private void makeIndexBuffer() {
        // preallocate memory
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);

        // put the indices into the index buffer flipped
        indicesBuffer.put(indices).flip();

        // generate a buffer object
        ibo = GL15.glGenBuffers();

        // bind the buffer object
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);

        // add the index data
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);

        // unbind the buffer object
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * helper method to store data in a certain buffer object
     * @param buffer - the float buffer containing the float data
     * @param index - the index of the data
     * @param size - the size of the data
     * @return - the buffer ID
     */
    private int storeData(FloatBuffer buffer, int index, int size) {
        // generate a buffer ID
        int bufferID = GL15.glGenBuffers();

        // bind the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);

        // add the data from the buffer to the gl buffer object
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // set the GL attribute
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);

        // unbind the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        return bufferID;
    }

    /**
     * getter method
     * @return - the vertices of this mesh
     */
    public Vertex[] getVertices() {
        return this.vertices;
    }

    /**
     * getter method
     * @return - the indices of this mesh
     */
    public int[] getIndices() {
        return this.indices;
    }

    /**
     * getter method
     * @return - the normal vector buffer object
     */
    public int getNBO() {
        return this.nbo;
    }

    /**
     * getter method
     * @return - the vertex array object
     */
    public int getVAO() {
        return this.vao;
    }

    /**
     * getter method
     * @return - the position buffer object
     */
    public int getPBO() {
        return this.pbo;
    }

    /**
     * getter method
     * @return - the color buffer object
     */
    public int getCBO() {
        return this.cbo;
    }

    /**
     * getter method
     * @return - the index buffer object
     */
    public int getIBO() {
        return this.ibo;
    }

    /**
     * release the mesh
     */
    public void destroy() {
        // delete the buffers
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(cbo);
        GL15.glDeleteBuffers(ibo);

        // delete the vertex array
        GL30.glDeleteVertexArrays(vao);
    }

}