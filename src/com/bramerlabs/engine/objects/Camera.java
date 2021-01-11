package com.bramerlabs.engine.objects;

import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    // the position and rotation of the camera
    private Vector3f position, rotation;

    // the input object for handling callbacks
    private Input input;

    // mouse motion variables
    private final static float moveSpeed = 0.05f, mouseSensitivity = 0.1f;
    private final static float rotateSpeed = 0.02f * 360;

    // arcball camera variables
    private float distance = 10.0f;
    private float angle = 0.0f;
    private float horizontalDistance = 0, verticalDistance = 0;
//    private float verticalAngle = -45, horizontalAngle = 30; // used for looking at an angle
//    private float verticalAngle = -90, horizontalAngle = 0; // used for looking down
    private float verticalAngle = 0, horizontalAngle = 0; // used for looking straight forward

    // the position of the mouse
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;

    // the position of the scroll wheel
    private double oldScrollX = 0, oldScrollY = 0, newScrollX = 0, newScrollY = 0;

    /**
     * default constructor for specified position, rotation, and input object
     * @param position - the position of the camera object
     * @param rotation - the rotation of the camera object
     * @param input - the callback input object
     */
    public Camera(Vector3f position, Vector3f rotation, Input input) {
        this.position = position;
        this.rotation = rotation;
        this.input = input;
    }

    /**
     * updates the camera based on keyboard and mouse input
     */
    public void update() {
        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        float x = (float)Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
        float z = (float)Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

        // handle the WASD keys
        if (input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-z, 0,  x));
        if (input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f( z, 0, -x));
        if (input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(-x, 0, -z));
        if (input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f( x, 0,  z));

        // handle going up and down
        if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));
        if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));

        // handle mouse motion
        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        // rotate according to the mouse motion
        rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0)); //dx, dy must be flipped and inverted
    }

    /**
     * update method for an arcball camera
     * @param centralPosition - the position the arcball camera is orbiting
     */
    public void update(Vector3f centralPosition) {
        // get the new x and y components of the mouse position
        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        // handle mouse motion
        float dmx = (float) (newMouseX - oldMouseX);
        float dmy = (float) (newMouseY - oldMouseY);

        // get the new x and y components of the scrollball
        //newScrollX = input.getScrollX();
        newScrollY = input.getScrollY();

        // handle scroll motion
        //float dsx = (float) (newScrollX - oldScrollX);
        float dsy = (float) (newScrollY - oldScrollY);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
        //oldScrollX = newScrollX;
        oldScrollY = newScrollY;

        // change the rotation using the mouse
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            verticalAngle -= dmy * mouseSensitivity;
            horizontalAngle += dmx * mouseSensitivity;
        }

        // change the camera distance using the scroll wheel
        if (distance > 0) {
            distance -= dsy;
        } else {
            distance = 0.1f;
        }

        // get the vertical and horizontal distances
        this.horizontalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle))); // using formula h = r*cos(theta_x)
        this.verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle))); // using formula v = r*sin(theta_x)

        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

        // set the new camera position based on the object
        this.position.set(centralPosition.getX() + xOffset,
                centralPosition.getY() - verticalDistance,
                centralPosition.getZ() + zOffset);

        // set the new camera rotation based on the object
        this.rotation.set(verticalAngle, -horizontalAngle, 0);
    }

    /**
     * getter method
     * @return - the position of this camera
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * getter method
     * @return - the rotation of this camera
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * getter method
     * @return - the vertical angle
     */
    public float getVerticalAngle() {
        return this.verticalAngle;
    }

    /**
     * getter method
     * @return - the horizontal angle
     */
    public float getHorizontalAngle() {
        return this.horizontalAngle;
    }
}
