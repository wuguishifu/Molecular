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
    private Vector3f lookingAt;
    private float distance = 20.0f;
    private float angle = 0.0f;
    private float horizontalDistance = 0, verticalDistance = 0;
//    private float verticalAngle = -45, horizontalAngle = 30; // used for looking at an angle
//    private float verticalAngle = -90, horizontalAngle = 0; // used for looking down
    private float verticalAngle = 0, horizontalAngle = 0; // used for looking straight forward
    private boolean rotatingVertical = false;
    private boolean rotatingHorizontal = false;

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
     * sets the vector that the arcball camera is looking at
     * @param v - the position the camera is rotating around
     */
    public void setLookingAt(Vector3f v) {
        this.lookingAt = v;
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
     */
    public void updateArcball() {

        // reset constraint rotation if the left mouse button is released
        if (!input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) || !input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            rotatingHorizontal = false;
            rotatingVertical = false;
        }
        System.out.println(rotatingHorizontal + ", " + rotatingVertical);

        // get the new x and y components of the mouse position
        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        // handle mouse motion
        float dmx = (float) (newMouseX - oldMouseX);
        float dmy = (float) (newMouseY - oldMouseY);

        // get the new x and y components of the scroll wheel
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
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && !input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
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
        this.position.set(lookingAt.getX() + xOffset,
                lookingAt.getY() - verticalDistance,
                lookingAt.getZ() + zOffset);

        // set the new camera rotation based on the object
        this.rotation.set(verticalAngle, -horizontalAngle, 0);
    }

    /**
     * updates the arcball camera in only one axis
     */
    public void updateArcballDirectional() {
        // get the new x and y components of the mouse position
        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        // handle mouse motion
        float dmx = (float) (newMouseX - oldMouseX);
        float dmy = (float) (newMouseY - oldMouseY);

        if (!rotatingHorizontal && !rotatingVertical) {
            if (Math.abs(dmx) > Math.abs(dmy)) {
                dmy = 0;
                rotatingHorizontal = true;
            } else {
                dmx = 0;
                rotatingVertical = true;
            }
        }
        if (rotatingHorizontal) {
            dmy = 0;
        }
        if (rotatingVertical) {
            dmx = 0;
        }

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        // change the rotation using the mouse
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            verticalAngle -= dmy * mouseSensitivity;
            horizontalAngle += dmx * mouseSensitivity;
        }

        // get the vertical and horizontal distances
        this.horizontalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle))); // using formula h = r*cos(theta_x)
        this.verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle))); // using formula v = r*sin(theta_x)

        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

        // set the new camera position based on the object
        this.position.set(lookingAt.getX() + xOffset,
                lookingAt.getY() - verticalDistance,
                lookingAt.getZ() + zOffset);

        // set the new camera rotation based on the object
        this.rotation.set(verticalAngle, -horizontalAngle, 0);
    }

    /**
     * translates the point at which the camera is looking at
     */
    public void translate() {
        // get the new x and y components of the mouse position
        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        // handle mouse motion
        float dmx = (float) (newMouseX - oldMouseX);
        float dmy = (float) (newMouseY - oldMouseY);

        // store the previous mouse position
        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        // find the vector pointing from the camera to the looking point
        Vector3f lookingDirection = Vector3f.normalize(Vector3f.subtract(lookingAt, position));
        // create a non-parallel vector in the plane of the horizontal angle
        Vector3f tempV1 = Vector3f.add(lookingDirection, new Vector3f(0, 0, 1.f)); // will never be parallel
        if (tempV1.equals(new Vector3f(0), 0.0001f)) {
            tempV1 = Vector3f.add(lookingDirection, new Vector3f(1.f, 0, 1.f));
        }
        System.out.println("Looking Direction: " + lookingDirection);
        System.out.println("Looking At: " + lookingAt);
        System.out.println("TempV1: " + tempV1);
        // create normal vector to these two vectors
        Vector3f zxNormal = Vector3f.normalize(Vector3f.cross(tempV1, lookingDirection));
        // create normal vector to the zx normal and the looking direction
        Vector3f yNormal = Vector3f.normalize(Vector3f.cross(lookingDirection, zxNormal));

        System.out.println(lookingAt + ", " + zxNormal + ", " + yNormal);

        // handle the mouse motion
        if (Math.abs(dmx) > Math.abs(dmy)) {
            dmy = 0;
        } else {
            dmx = 0;
        }
        if (dmx < 0) this.lookingAt = Vector3f.subtract(this.lookingAt, Vector3f.normalize(yNormal, moveSpeed * mouseSensitivity * Math.abs(dmx)));
        if (dmx > 0) this.lookingAt = Vector3f.add(this.lookingAt, Vector3f.normalize(yNormal, moveSpeed * mouseSensitivity * Math.abs(dmx)));
        if (dmy > 0) this.lookingAt = Vector3f.subtract(this.lookingAt, Vector3f.normalize(zxNormal, moveSpeed * mouseSensitivity * Math.abs(dmy)));
        if (dmy < 0) this.lookingAt = Vector3f.add(this.lookingAt, Vector3f.normalize(zxNormal, moveSpeed * mouseSensitivity * Math.abs(dmy)));

        System.out.println(lookingAt);

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

    /**
     * getter method
     * @return - the point this camera is looking at
     */
    public Vector3f getLookingAt() {
        return this.lookingAt;
    }
}
