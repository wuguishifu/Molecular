package com.bramerlabs.engine.main;

import com.bramerlabs.engine.graphics.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.picking.CPRenderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.Camera;
import com.bramerlabs.engine.objects.shapes.Cylinder;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.ring_molecules.Benzaldehyde;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;
import org.lwjglx.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Main implements Runnable {

    // the main window of the game
    private Window window;

    // the shaders used to paint textures and for color picking
    private Shader shader, cpShader;

    // used to render objects
    private Renderer renderer;

    // used for color picking
    private CPRenderer cpRenderer;

    // used to handle inputs
    private Input input = new Input();

    // test game objects
    private Molecule molecule;

    // the camera
    public Camera camera = new Camera(new Vector3f(0, 0, 2), new Vector3f(0, 0, 0), input);

    // the position of the light
    private Vector3f lightPosition = new Vector3f(0, 100, 0);

    // if the last frame had the right mouse button down
    private boolean lastFrameRightButtonDown = false;
    private boolean currentFrameRightButtonDown = false;

    /**
     * main method
     * @param args - args
     */
    public static void main(String[] args) {
        new Main().start();
    }

    /**
     * runs the game
     */
    public void run() {
        init();
        while (!window.shouldClose()) {
            update();
            render();
        }
        close();
    }

    /**
     * initialize the program
     */
    private void init() {
        //create the openJL window
        window = new Window(input);
        window.create();

        // create molecules here
        generateMolecule();

        // create the renderer
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        renderer = new Renderer(window, shader);

        // create the color picker renderer
        cpShader = new Shader("/shaders/colorPickerVertex.glsl", "/shaders/colorPickerFragment.glsl");
        cpRenderer = new CPRenderer(window, cpShader);

        // initialize the shader
        shader.create();
        cpShader.create();
    }

    /**
     * releases the objects
     */
    private void close() {
        // release the window
        window.destroy();

        // release the game objects
        molecule.destroy();

        // release the shaders
        shader.destroy();
        cpShader.destroy();
    }

    /**
     * begins the thread
     */
    public void start() {
        Thread main = new Thread(this, "Molecular");
        main.start();
    }

    /**
     * update the window and game objects
     */
    private void update() {

        // update the window
        window.update();

        // update the mouse picker
        currentFrameRightButtonDown = input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
        if (lastFrameRightButtonDown && !currentFrameRightButtonDown) {
            getSelectedAtom();
        }
        lastFrameRightButtonDown = currentFrameRightButtonDown;

        // clear the screen
        GL46.glClearColor(Window.bgc.getX(), Window.bgc.getY(), Window.bgc.getZ(), 1);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // poll GLFW for callbacks
        GLFW.glfwPollEvents();

        // update the camera
        camera.update(molecule.getPosition());
    }

    /**
     * retrieves a selected atom on the molecule via raycasting
     */
    private void getSelectedAtom() {

        // render the game objects
        for (Bond bond : molecule.getBonds()) {
            for (Cylinder cylinder : bond.getCylinders()) {
                cpRenderer.renderMesh(cylinder, camera);
            }
        }
        for (Atom atom : molecule.getAtoms()) {
            cpRenderer.renderMesh(atom.getSphere(), camera);
        }

        GL11.glFlush();
        GL11.glFinish();
        GL11.glRasterPos2d(input.getMouseX(), input.getMouseY());
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        float x = (float) input.getMouseX();
        float y = (float) input.getMouseY();
        ByteBuffer data = BufferUtils.createByteBuffer(3);
        GL11.glReadPixels((int)x, (int)y, 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, data);
        System.out.println(data.get(0) + ", " + data.get(1) + ", " + data.get(2));

    }

    /**
     * render the game objects
     */
    private void render() {

        // render the game objects
        for (Bond bond : molecule.getBonds()) {
            for (Cylinder cylinder : bond.getCylinders()) {
                renderer.renderMesh(cylinder, camera, lightPosition);
            }
        }
        for (Atom atom : molecule.getAtoms()) {
            renderer.renderMesh(atom.getSphere(), camera, lightPosition);
        }

        // must be called at the end
        window.swapBuffers();
    }

    /**
     * generates a molecule
     */
    private void generateMolecule() {
        molecule = new Benzaldehyde(new Vector3f(0, 0, 0), 2.5f);
    }
}