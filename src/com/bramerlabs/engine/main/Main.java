package com.bramerlabs.engine.main;

import com.bramerlabs.Molecular.Atom;
import com.bramerlabs.Molecular.Molecule;
import com.bramerlabs.sphere.Sphere;
import com.bramerlabs.sphere.Triangle;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

public class Main {

    // window variables
    private static long window;
    private static int width = 1600;
    private static int height = 1200;

    // application variables
    private static final String APP_NAME = "Molecular";

    // framerate calculator
    private static final int FRAMERATE = 1; // 60 fps
    private static int frames;
    private static long time;

    // camera variables
    private static float zoom = 20;
    private static int mouseX, mouseY;
    private static float pitch = 0.3f, yaw = 0.2f;
    private static float defaultPitch = 0, defaultYaw = 0;
    private static float translationX = 0, translationY = 0;
    private static float rotationSpeed = 0.005f;
    private static float translationSpeed = 0.0025f;

    // center vector
    private static final Vector3f center = new Vector3f();

    // cursor variables
    private static boolean cursorWasDown = false;
    private static boolean direction = true;

    // molecule variables
    private static Molecule molecule;

    /**
     * main method
     * @param args - args
     */
    public static void main(String[] args) {

        // initialize the window
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // create the window handle
        window = glfwCreateWindow(width, height, APP_NAME, NULL, NULL);

        // make sure the window was created properly
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // create the GLFW callbacks
        createCallbacks();

        // create an frameBuffer
        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
        width = framebufferSize.get(0);
        height = framebufferSize.get(1);

        // set the current context and give it capabilities
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // for framerate calculation
        time = System.currentTimeMillis();

        // set the framerate of the window
        org.lwjgl.glfw.GLFW.glfwSwapInterval(FRAMERATE);
        GLFW.glfwSetWindowTitle(window, APP_NAME + " | FPS: " + frames);

        // set the clear color
        glClearColor(0.9f, 0.9f, 0.9f, 1.0f);

        glEnable(GL_DEPTH_TEST);

        // create the view matrix
        Matrix4f mat = new Matrix4f();
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);

        // while the application is running
        while (!glfwWindowShouldClose(window)) {
            glViewport(0, 0, width, height);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glMatrixMode(GL_PROJECTION);
            glLoadMatrixf(
                    mat.setPerspective(
                            (float) Math.toRadians(45.0f),
                            (float) width / height,
                            0.01f,
                            100.0f).get(floatBuffer)
            );
            glMatrixMode(GL_MODELVIEW);

            // load arcball camera view matrix into 'mat'
            glLoadMatrixf(mat.translation(translationX, translationY, -zoom).rotateX(pitch).rotateY(yaw).translate(-center.x, -center.y, -center.z).get(floatBuffer));

            // apply model transformation to 'mat'
            glLoadMatrixf(mat.translate(center).get(floatBuffer));


            loadTestMolecule();

            glfwSwapBuffers(window);
            glfwPollEvents();

            displayFramerate();

        }
    }

    private static void drawSphere(Sphere sphere) {
        sphere.generateVertices();
        for (Triangle t : sphere.getFaces()) {
            glBegin(GL_TRIANGLES);
            glColor3f(t.getColor().x, t.getColor().y, t.getColor().z);
            glVertex3f(t.getV1().x, t.getV1().y, t.getV1().z);
            glVertex3f(t.getV2().x, t.getV2().y, t.getV2().z);
            glVertex3f(t.getV3().x, t.getV3().y, t.getV3().z);
            glEnd();
        }
    }

    private static void resetView() {
        pitch = defaultPitch;
        yaw = defaultYaw;
    }

    /**
     * generate the callbacks for the window
     */
    public static void createCallbacks() {

        // keyboard callbacks
        glfwSetKeyCallback(window, (win, k, s, a, m) -> {
            if (a == GLFW_PRESS) {
                switch (k) {
                    case GLFW_KEY_ESCAPE : glfwSetWindowShouldClose(window, true); break;
                    case GLFW_KEY_ENTER : resetView(); break;
                }
            }
        });

        // window size callback
        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            if (w > 0 && h > 0) {
                width = w;
                height = w;
            }
        });

        // cursor position callback
        glfwSetCursorPosCallback(window, (win, x, y) -> {
            if (glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
                if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
                    // for translating the plane - for some reason this doesn't act like i want it to
//                    translationX += 0.0001 * (x - mouseX);
//                    translationY -= 0.0001 * (y - mouseY);
                    return;

                } else if (glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
                    // this works for rotating the plane for some reason : don't change this @ future bo
                    pitch += ((int) y - mouseY) * rotationSpeed;
                    pitch = Math.abs(pitch) > 2 * Math.PI ? (float) (pitch % Math.PI) : pitch;

                    if ((Math.abs(pitch) < Math.PI/2 || Math.abs(pitch) > 3*Math.PI/2) && !cursorWasDown) {
                        direction = false;
                    } else if (!cursorWasDown){
                        direction = true;
                    }

                    if (direction) {
                        yaw -= ((int) x - mouseX) * rotationSpeed;
                    } else {
                        yaw += ((int) x - mouseX) * rotationSpeed;
                    }

                    yaw = Math.abs(yaw) > 2 * Math.PI ? (float) (yaw % Math.PI) : yaw;
                    cursorWasDown = true;
                }
            }

            mouseX = (int) x;
            mouseY = (int) y;
            if (glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_1) == GLFW_RELEASE) {
                cursorWasDown = false;
            }

        });

        // scroll wheel callback
        glfwSetScrollCallback(window, (win, x, y) -> {
            if (y > 0) {
                zoom /= 1.1f;
            } else {
                zoom *= 1.1f;
            }
        });
    }

    public static void displayFramerate() {
        frames ++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(window, APP_NAME + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    /**
     * Test methods
     */

    private static void loadTestMolecule() {
        float r2 = (float)Math.sqrt(2);

        molecule = new Molecule(new float[]{0f, 0f, 0f});
        molecule.addAtoms(new float[][]{{0f, -r2, -2f}, {0f, -r2, 2f}, {2f, r2, 0f}, {-2f, r2, 0f}}, new int[]{1, 0, 0}); // 4 H
        for (Atom a : molecule.getAtoms()) {
            a.setColor(new float[]{1f, 1f, 1f}).setShadedColor(0).setRadius(0.5f);
        }
        molecule.addAtom(new Atom(new float[]{ 0f,  0f,  0f}, new int[]{1}).setColor(new float[]{0f, 0f, 1f}).setShadedColor(0).setRadius(0.75f)); // C

        for (Atom a : molecule.getAtoms()) {
            drawSphere(a.getSphere());
        }
    }

    private static void renderGrid() {
        glBegin(GL_LINES);
        glColor3f(0.2f, 0.2f, 0.2f);
        for (int i = -20; i <= 20; i++) {
            glVertex3f(-20.0f, 0.0f, i);
            glVertex3f(20.0f, 0.0f, i);
            glVertex3f(i, 0.0f, -20.0f);
            glVertex3f(i, 0.0f, 20.0f);
        }
        glEnd();
    }
}
