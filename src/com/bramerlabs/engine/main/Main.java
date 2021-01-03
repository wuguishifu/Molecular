package com.bramerlabs.engine.main;

import com.bramerlabs.engine.graphics.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.Camera;
import com.bramerlabs.engine.objects.shapes.Cylinder;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.central_molecules.TrigonalPlanar;

public class Main implements Runnable {

    // the main window of the game
    private Window window;

    // the shader used to paint textures
    private Shader shader;

    // used to render objects
    private Renderer renderer;

    // used to handle inputs
    private Input input = new Input();

    // test game objects
    private Molecule molecule;

    // the camera
    public Camera camera = new Camera(new Vector3f(0, 0, 2), new Vector3f(0, 0, 0), input);

    // the position of the light
    private Vector3f lightPosition = new Vector3f(0, 100, 0);

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
        camera.setWindowHandle(window.getWindowHandle());
        camera.setInvProj(window.getInvProjectionMatrix());
        camera.setProj(window.getProjectionMatrix());

        // create molecules here
        generateMolecule();

        // give the camera a pointer to the molecule
        camera.setMolecule(molecule);

        // create the shader
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");

        // create the renderer based on the main window and the shader
        renderer = new Renderer(window, shader);

        // initialize the shader
        shader.create();
    }

    /**
     * releases the objects
     */
    private void close() {
        // release the window
        window.destroy();

        // release the game objects
        molecule.destroy();

        // release the shader
        shader.destroy();
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

        // update the camera
        camera.update(molecule.getPosition());
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
        molecule = new TrigonalPlanar(new Vector3f(0, 0, 0), 2.5f);
    }
}