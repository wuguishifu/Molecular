package com.bramerlabs.engine.io.gui.gui_render;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Vertex;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GuiMesh {

    // the vertices and indices of this mesh
    private Vertex[] vertices;
    private int[] indices;

    // vertex array object
    private int vao;

    // position buffer object
    private int pbo;

    // texture buffer object
    private int tbo;

    // index buffer object
    private int ibo;

    // the material this mesh is made of
    private Material material;

    /**
     * default constructor for specified vertices and indices
     * @param material - the material that this mesh is made of
     * @param vertices - the vertices of this mesh
     * @param indices - the indices of this mesh
     */
    public GuiMesh(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    /**
     * creates this mesh
     */
    public void create() {
        // generate and bind the vertex array
        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);

        // create the buffers
        makePositionBuffer();
        makeTextureBuffer();
        makeIndexBuffer();

        material.create();
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
     * helper method to create the texture buffer object
     */
    private void makeTextureBuffer() {
        // preallocate memory
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);

        // create a new temp array to store texture coord data
        float[] textureData = new float[vertices.length * 2];

        // add all the texture coord data to the temp array
        for (int i = 0; i < vertices.length; i++) {
            textureData[i * 2    ] = vertices[i].getTextureCoord().getX();
            textureData[i * 2 + 1] = vertices[i].getTextureCoord().getY();
        }

        // flip the data to make it handleable by OpenGL
        textureBuffer.put(textureData).flip();

        // store the texture coord data in the texture buffer object
        tbo = storeData(textureBuffer, 1, 2);
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
     * @return - the index buffer object
     */
    public int getIBO() {
        return this.ibo;
    }

    /**
     * getter method
     * @return - the texture buffer object
     */
    public int getTBO() {
        return this.tbo;
    }

    /**
     * getter method
     * @return - the material of this mesh
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * release the mesh
     */
    public void destroy() {
        // delete the buffers
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(tbo);
        GL15.glDeleteBuffers(ibo);

        // delete the vertex array
        GL30.glDeleteVertexArrays(vao);

        // release the material
        material.destroy();
    }

}