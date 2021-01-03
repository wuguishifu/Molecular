package com.bramerlabs.engine.objects;

import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
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
    private float verticalAngle = -45, horizontalAngle = 30;

    // the position of the mouse
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;

    // the position of the scroll wheel
    private double oldScrollX = 0, oldScrollY = 0, newScrollX = 0, newScrollY = 0;

    // the window handle
    private long windowHandle;

    // the projection and inverse projection matrices
    private Matrix4f invProj, proj;

    // the molecule in the scene
    private Molecule molecule;

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
     * sets the window handle
     * @param windowHandle - the window handle
     */
    public void setWindowHandle(long windowHandle) {
        this.windowHandle = windowHandle;
    }

    /**
     * sets the inverse projection matrix
     * @param m - the matrix
     */
    public void setInvProj(Matrix4f m) {
        this.invProj = m;
    }

    /**
     * sets the projection matrix
     * @param m - the matrix
     */
    public void setProj(Matrix4f m) {
        this.proj = m;
    }

    /**
     * sets the molecule
     * @param m - the molecule
     */
    public void setMolecule(Molecule m) {
        this.molecule = m;
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

//        // handle ray casting
//        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
//            FloatBuffer z = BufferUtils.createFloatBuffer(1);
//            GL11.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, z);
//
//            FloatBuffer model = BufferUtils.createFloatBuffer(16);
//            FloatBuffer projection = BufferUtils.createFloatBuffer(16);
//            IntBuffer viewport = BufferUtils.createIntBuffer(16);
//
//            model.put(GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX)).flip();
//            projection.put(GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX)).flip();
//            viewport.put(GL11.glGetInteger(GL11.GL_VIEWPORT)).flip();
//
//            FloatBuffer objPos = BufferUtils.createFloatBuffer(3);
//
//            for (Atom a : molecule.getAtoms()) {
//                FloatBuffer modelMatrix = Matrix4f.toFloatBuffer(Matrix4f.transform(a.getSphere().getPosition(), a.getSphere().getRotation(), a.getSphere().getScale()));
//                if (GLU.gluUnProject(Mouse.getX(), Mouse.getY(), z.get(0), modelMatrix, projection, viewport, objPos)) {
//                    System.out.println(objPos.get(0) + ", " + objPos.get(1) + ", " + objPos.get(2));
//                }
//            }

//        }

//        // handle ray casting
//        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {

//            // determine the width and height of the screen
//            IntBuffer w = BufferUtils.createIntBuffer(1);
//            IntBuffer h = BufferUtils.createIntBuffer(1);
//            GLFW.glfwGetWindowSize(windowHandle, w, h);
//            int windowWidth = w.get(0);
//            int windowHeight = h.get(0);
//
//            // convert from viewport space to normalized space
//            float aspectRatio = windowWidth/(float)windowHeight;
//            float screenSpaceX = (float) (2.0 * newMouseX / windowWidth - 1.0);
//            float screenSpaceY = (float) (1.0 - 2.0 * newMouseY / windowHeight);
//            float screenSpaceZ = -1; // the furthest possible z position
//            float screenSpaceW = 1;
//
//            // multiply by the inverse projection matrix
//            float[] invProjVec = Matrix4f.multiplyVector(screenSpaceX, screenSpaceY, screenSpaceZ, screenSpaceW, invProj);
//
//            // make inverse view matrix
//            Matrix4f view = Matrix4f.view(this.position, this.rotation);
//            Matrix4f invView = Matrix4f.invert(view);
//
//            // multiply by the inverse view matrix
//            float[] invViewVec = Matrix4f.multiplyVector(invProjVec, invView);
//
//            // set the w to 0 and z to -1
//            //invViewVec[2] = -1;
//            invViewVec[3] = 0;
//
//            System.out.println(invViewVec[0] + ", " + invViewVec[1] + ", " + invViewVec[2] + ", " + invViewVec[3]);
//
//            float length = 0;
//            Vector3f ray = new Vector3f(invViewVec[0], invViewVec[1], invViewVec[2]);
//            ray = Vector3f.normalize(ray, 0.1f);
//            Vector3f curRay = Vector3f.add(new Vector3f(ray), position);
//            while (length < 20f) {
//                curRay = Vector3f.add(curRay, ray);
//                Atom a = null;
//                for (Atom atom : molecule.getAtoms()) {
//                    if (atom.getHitbox().intersects(curRay)) {
//                        a = atom;
//                        break;
//                    }
//                }
//                if (a != null) {
//                    molecule.removeAtom(a);
//                    break;
//                }
//                length = Vector3f.length(Vector3f.subtract(curRay, position));
//            }
//        }

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
