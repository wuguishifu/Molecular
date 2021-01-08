package com.bramerlabs.engine.io.picking;

import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;
import com.bramerlabs.engine.objects.Camera;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjglx.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjglx.util.glu.Project.gluUnProject;

public class MousePicker {

    // the current ray being cast
    private Vector3f currentRay;

    // the camera of the scene
    private Camera camera;

    // the window
    private Window window;

    // the input device
    private Input input;

    // the projection and view matrices
    private Matrix4f projection, view;

    /**
     * default constructor
     * @param camera - the camera in the window
     * @param window - the display window
     * @param input - the object for handling input
     */
    public MousePicker(Camera camera, Window window, Input input) {
        this.camera = camera;
        this.window = window;
        this.projection = window.getProjectionMatrix();
        this.input = input;
    }

    /**
     * gets a picking ray - obsolete
     * @param cursorX - the cursor x position
     * @param cursorY - the cursor y position
     * @return - the picking ray
     */
    public static Vector3f getPickingRay(float cursorX, float cursorY) {
        IntBuffer viewport = ByteBuffer.allocateDirect((Integer.SIZE/8)*16).order(ByteOrder.nativeOrder()).asIntBuffer();
        FloatBuffer modelView = ByteBuffer.allocateDirect((Float.SIZE/8)*16).order(ByteOrder.nativeOrder()).asFloatBuffer();
        FloatBuffer projection = ByteBuffer.allocateDirect((Float.SIZE/8)*16).order(ByteOrder.nativeOrder()).asFloatBuffer();
        FloatBuffer pickingRayBuffer = ByteBuffer.allocateDirect((Float.SIZE/8)*3).order(ByteOrder.nativeOrder()).asFloatBuffer();
        FloatBuffer zBuffer = ByteBuffer.allocateDirect((Float.SIZE/8)).order(ByteOrder.nativeOrder()).asFloatBuffer();
        GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelView);
        GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);
        // convert window coordinates to OpenGL coordinates (top left to bottom left for (0,0)
        float winY = (float) viewport.get(3) - cursorY;

        // now unproject this to get the  vector in to the screen
        // take the frustum and unproject in to the screen
        // frustum has a near plane and a far plane

        // first the near vector
        gluUnProject(cursorX, winY,  0, modelView, projection, viewport, pickingRayBuffer);
        Vector3f nearVector = new Vector3f(pickingRayBuffer.get(0),pickingRayBuffer.get(1),pickingRayBuffer.get(2));

        pickingRayBuffer.rewind();

        // now the far vector
        gluUnProject(cursorX, winY,  1, modelView, projection, viewport, pickingRayBuffer);
        Vector3f farVector = new Vector3f(pickingRayBuffer.get(0),pickingRayBuffer.get(1),pickingRayBuffer.get(2));

        //save the results in a vector, far-near
        return Vector3f.normalize(Vector3f.subtract(farVector, nearVector));
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