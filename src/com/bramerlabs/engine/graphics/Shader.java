package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.file_util.FileUtils;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Shader {

    // the number of attributes this shader has
    private int numAttribs = 2;

    // the vertex and fragment shaders
    private String vertexFile, fragmentFile;

    // pointers to shader program
    private int vertexID, fragmentID, programID;

    /**
     * default constructor for the shader from specified path to vertex and fragment shaders
     * @param pathToVertexShader - the path to the vertex shader
     * @param pathToFragmentShader - the path to the fragment shader
     */
    public Shader(String pathToVertexShader, String pathToFragmentShader) {
        this.vertexFile = FileUtils.loadAsString(pathToVertexShader);
        this.fragmentFile = FileUtils.loadAsString(pathToFragmentShader);
    }

    /**
     * initializes the shader
     */
    public void create() {
        programID = GL20.glCreateProgram();

        // create the shaders
        createVertexShader();
        createFragmentShader();

        // attach shaders to program
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        // link the program
        linkProgram(programID);
        validateProgram(programID);
    }

    private void createFragmentShader() {
        // create an id
        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        // point to the source
        GL20.glShaderSource(fragmentID, fragmentFile);

        // compile shader
        GL20.glCompileShader(fragmentID);

        // check to see if the compilation was successful
        if (GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: could not compile fragment shader.");
            System.err.println("Fragment Shader: " + GL20.glGetShaderInfoLog(fragmentID));
            throw new RuntimeException();
        }
    }

    /**
     * creates the vertex shader from the loaded vertex shader file
     */
    private void createVertexShader() {
        // create an id
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        // point to the source
        GL20.glShaderSource(vertexID, vertexFile);

        // compile shader
        GL20.glCompileShader(vertexID);

        // check to see if compilation was successful
        if (GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: could not compile vertex shader.");
            System.err.println("Vertex Shader: " + GL20.glGetShaderInfoLog(vertexID));
            throw new RuntimeException();
        }
    }

    /**
     * links the program
     * @param programID - the program id
     */
    private void linkProgram(int programID) {
        // attempt to link the program
        GL20.glLinkProgram(programID);

        // error catching
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: program could not be linked.");
            System.err.println("Program: " + GL20.glGetProgramInfoLog(programID));
            throw new RuntimeException();
        }
    }

    /**
     * validate the program with OpenGL
     * @param programID - the program id
     */
    private void validateProgram(int programID) {
        // attempt to validate the program
        GL20.glValidateProgram(programID);

        // error catching
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: program could not be validated.");
            System.err.println("Program: " + GL20.glGetProgramInfoLog(programID));
            throw new RuntimeException();
        }
    }

    /**
     * getter method
     * @param uniformName - the name of the uniform
     * @return - the location of the uniform
     */
    public int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    /**
     * sets the uniform to a float value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    /**
     * sets the uniform to an int value
     * @param name - the name of the uniform
     * @param value - the value
     */
    public void setUniform(String name, int value) {
        GL20.glUniform1i(getUniformLocation(name), value);
    }

    /**
     * sets the uniform to a boolean value (converts boolean to 1 or 0)
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, boolean value) {
        GL20.glUniform1f(getUniformLocation(name), value ? 1.0f : 0.0f);
    }

    /**
     * sets the uniform to a vec2 value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, Vector2f value) {
        GL20.glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }

    /**
     * sets the uniform to a vec3 value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, Vector3f value) {
        GL20.glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }

    /**
     * sets the uniform to a vec4 value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, Vector4f value) {
        GL20.glUniform4f(getUniformLocation(name), value.getX(), value.getY(), value.getZ(), value.getW());
    }

    /**
     * sets the uniform to a mat4 value
     * @param name - the name of the uniform
     * @param value - the value of the uniform
     */
    public void setUniform(String name, Matrix4f value) {
        // preallocate memory for the float values
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);

        // put the values flipped of the matrix into the float buffer
        matrix.put(value.getAll()).flip();

        // transpose is true because we create it in row major order instead of column major order
        GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }

    /**
     * binds the gl program to the application
     */
    public void bind() {
        GL20.glUseProgram(programID);
    }

    /**
     * unbinds the gl program
     */
    public void unbind() {
        GL20.glUseProgram(programID);
    }

    /**
     * releases the shaders
     */
    public void destroy() {
        // delete the shaders because we now have a program that does the shader stuff
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);

        // delete the shader program
        GL20.glDeleteShader(programID);
    }

    /**
     * getter method
     * @return - the number of attributes in this shader
     */
    public int getNumAttribs() {
        return this.numAttribs;
    }

    /**
     * sets the number of attributes
     * @param numAttribs - the number of attributes
     */
    public void setNumAttribs(int numAttribs) {
        this.numAttribs = numAttribs;
    }
}