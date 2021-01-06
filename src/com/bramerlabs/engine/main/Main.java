package com.bramerlabs.engine.main;

import com.bramerlabs.engine.graphics.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.MousePicker;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.Camera;
import com.bramerlabs.engine.objects.shapes.Cylinder;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.ring_molecules.Benzaldehyde;
import org.lwjgl.glfw.GLFW;

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

    // the mouse picker object
    private MousePicker mousePicker;

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

        // create the shader
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");

        // create the renderer based on the main window and the shader
        renderer = new Renderer(window, shader);

        // initialize the shader
        shader.create();

        // create the mouse picker
        mousePicker = new MousePicker(camera, molecule, window, input);
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

        // update the mouse picker
        mousePicker.update();
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            System.out.println(mousePicker.getCurrentRay());
            getSelectedAtom();
        }

        // update the camera
        camera.update(molecule.getPosition());
    }

    /**
     * retrieves a selected atom on the molecule via raycasting
     */
    private void getSelectedAtom() {

        // find the origin of the raycast
        Vector3f origin = camera.getPosition();

        // create the ray
        Vector3f ray = Vector3f.normalize(mousePicker.getCurrentRay(), 0.1f);

        // scale the ray (error adjustment)
        ray = Vector3f.multiply(ray, new Vector3f(1, -1, 1));

        // add the ray to the origin repeatedly, testing each hitbox for each itiration
        Vector3f sumRay = Vector3f.add(origin, ray);

        // iterate between the nearest and farthest point
        while (Vector3f.length(sumRay) < 50f) {
            Atom del = null;

            // check each atom
            for (Atom a : molecule.getAtoms()) {
                if (a.getHitbox().intersects(sumRay)) {
                    del = a;
                    break;
                }
            }

            // if an atom intersects, set it for deletion and remove it
            if (del != null) {
                molecule.removeAtom(del);
                break;
            }

            // add the ray to the sumRay
            sumRay = Vector3f.add(sumRay, ray);
        }
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