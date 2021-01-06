package com.bramerlabs.engine.io.window;

import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;
import com.bramerlabs.engine.objects.Camera;
import com.bramerlabs.molecular.molecule.Molecule;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.BufferUtils;

import java.nio.IntBuffer;

public class MousePicker {

    // the current ray being cast
    private Vector3f currentRay;

    // the camera of the scene
    private Camera camera;

    // the window
    private Window window;

    // the molecule in the scene
    private Molecule molecule;

    // the input device
    private Input input;

    // the projection and view matrices
    private Matrix4f projection, view;

    /**
     * default constructor
     * @param camera - the camera in the window
     * @param molecule - the molecule in the window
     */
    public MousePicker(Camera camera, Molecule molecule, Window window, Input input) {
        this.camera = camera;
        this.molecule = molecule;
        this.window = window;
        this.projection = window.getProjectionMatrix();
        this.input = input;
    }

    /**
     * updates the mousePicker object
     */
    public void update() {
        view = Matrix4f.view(camera.getPosition(), camera.getRotation());
        currentRay = calculateRay();
    }

    /**
     * calculates the ray
     */
    public Vector3f calculateRay() {
        float mouseX = (float) input.getMouseX();
        float mouseY = (float) input.getMouseY();
        Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedCoords, -1, 1);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        return toWorldCoords(eyeCoords);
    }

    /**
     * converts from eye coords to world coords
     * @param eyeCoords - the eye coords
     * @return - the 3-vector world coords
     */
    private Vector3f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invView = Matrix4f.invert(view);
        return Vector3f.normalize((Matrix4f.multiply(invView, eyeCoords).getXYZ()));
    }

    /**
     * converts to eye coords
     * @param clipCoords - the clipCoords
     * @return - the eye coords
     */
    private Vector4f toEyeCoords(Vector4f clipCoords) {
        // create the inverse projection matrix
        Matrix4f invProjection = Matrix4f.invert(projection);

        return Matrix4f.multiply(invProjection, clipCoords);
    }

    /**
     * calculates the normalized device coordinates
     * @param mouseX - the x position of the mouse
     * @param mouseY - the y position of the mouse
     * @return - the mouse coordinates in normalized coordinates
     */
    private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
        // get the width and height of the display
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowSize(window.getWindowHandle(), w, h);
        int width = w.get(0);
        int height = h.get(0);

        // return the new vector
        return new Vector2f(
                2 * mouseX / width - 1,
                2 * mouseY / height - 1
        );
    }

    /**
     * getter method
     * @return - the current ray being cast
     */
    public Vector3f getCurrentRay() {
        return this.currentRay;
    }
}