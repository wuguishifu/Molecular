package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.file_util.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {

    // the path to the texture
    private String pathToTexture;

    // the format of the texture file
    private static final String FORMAT = "PNG";

    // the dimensions of the texture
    private float width, height;

    // the textureID
    private int textureID;

    // the texture interface
    private Texture texture;

    /**
     * default constructor for specified path to texture
     * @param pathToTexture - the path to the texture
     */
    public Material(String pathToTexture) {
        this.pathToTexture = pathToTexture;
    }

    /**
     * create the texture
     */
    public void create() {
        try {
            texture = TextureLoader.getTexture(FORMAT, FileUtils.class.getModule().getResourceAsStream(pathToTexture), GL11.GL_LINEAR);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: could not load texture.");
        }

        // get the values
        width = texture.getWidth();
        height = texture.getHeight();
        textureID = texture.getTextureID();
    }

    /**
     * release the texture
     */
    public void destroy() {
        GL20.glDeleteTextures(textureID);
    }

    /**
     * getter method
     * @return - the width of this texture
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * getter method
     * @return - the height of this texture
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * getter method
     * @return - the id of this texture
     */
    public int getTextureID() {
        return this.textureID;
    }
}