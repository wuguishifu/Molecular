package com.bramerlabs.engine.main;

import com.bramerlabs.engine.graphics.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.gui.Gui;
import com.bramerlabs.engine.io.gui.gui_object.Button;
import com.bramerlabs.engine.io.gui.gui_render.GuiRenderer;
import com.bramerlabs.engine.io.picking.CPRenderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.Camera;
import com.bramerlabs.engine.objects.shapes.Cylinder;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.carbon.Carbon;
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.Benzaldehyde;
import com.bramerlabs.molecular.molecule.vsepr.Tetrahedral;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;
import org.lwjglx.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Main implements Runnable {

    // the main window of the game
    private Window window;

    // the shaders used to paint textures and for color picking
    private Shader shader, cpShader, guiShader;

    // used to render objects
    private Renderer renderer;

    // used for color picking
    private CPRenderer cpRenderer;

    // GUI variables
    private Gui gui; // the gui
    private Button button; // the buttons
    private GuiRenderer guiRenderer; // used for rendering the gui

    // used to handle inputs
    private Input input = new Input();

    // the molecules
    private ArrayList<Molecule> molecules;

    // the camera
    public Camera camera = new Camera(new Vector3f(0, 0, 2), new Vector3f(0, 0, 0), input);

    // the point the camera is looking at
    public static final Vector3f LOOKING_AT = new Vector3f(0);

    // the position of the light
    private Vector3f lightPosition = new Vector3f(0, 100, 0);

    // if the last frame had the right mouse button down
    private boolean lastFrameRightButtonDown = false;

    private static int time = 0;

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
            boolean shouldSwapBuffers = update();
            render(shouldSwapBuffers);
        }
        close();
    }

    /**
     * initialize the program
     */
    private void init() {

        // initialize the data compilers
        AtomicDataCompiler.init();

        //create the openJL window
        window = new Window(input);
        window.create();

        // set the camera's arcball orbit focus
        camera.setLookingAt(LOOKING_AT);

        // initialize the GUI
        initializeGUI();

        // create molecules here
        molecules = new ArrayList<>();
        generateMolecules();

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
        for (Molecule molecule : molecules) {
            molecule.destroy();
        }

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
     * initialize the gui
     */
    private void initializeGUI() {

        // initialize the GUI
        gui = new Gui();
        button = Button.getInstance(2*window.getWidth()-200, 2*window.getHeight()-200, 200, 200, window);
        button.setID(Button.INFORMATION_BUTTON);
        gui.addButton(button);

        // create the gui renderer
        guiShader = new Shader("/shaders/guiVertex.glsl", "/shaders/guiFragment.glsl");
        guiRenderer = new GuiRenderer(guiShader);

        // create the GUI shader
        guiShader.create();

    }

    /**
     * update the window and game objects
     */
    private boolean update() {

        // update the global timer
        time++;
        if (time > 1800) { // reset timer every second
            time %= 1800;
        }

        // update the window
        window.update();

        // update the molecule occasionally
        for (Molecule m : molecules) {
            m.update(time);
        }

        // clear the screen
        GL46.glClearColor(Window.bgc.getX(), Window.bgc.getY(), Window.bgc.getZ(), 1);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        // update the mouse picker
        boolean shouldSwapBuffers = true;
        boolean currentFrameRightButtonDown = input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
        if (lastFrameRightButtonDown && !currentFrameRightButtonDown) {
            int buttonPressed = getPressedButtons();
            getSelectedAtom(buttonPressed == -1);
            shouldSwapBuffers = false;
        }
        lastFrameRightButtonDown = currentFrameRightButtonDown;

        // translates the camera
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && input.isKeyDown(GLFW.GLFW_KEY_LEFT_ALT)) camera.translate();
        // resets the camera position, rotation, and distance
        if (input.isKeyDown(GLFW.GLFW_KEY_ENTER)) camera.resetPosition(LOOKING_AT);

        camera.updateArcball();
        return shouldSwapBuffers;
    }

    /**
     * retrieves a selected atom on the molecule via raycasting
     * @param unselect - if the atoms should be unselected
     */
    private void getSelectedAtom(boolean unselect) {
        // render the game objects
        for (Molecule molecule : molecules) {
            for (Bond bond : molecule.getBonds()) {
                for (Cylinder cylinder : bond.getCylinders()) {
                    cpRenderer.renderMesh(cylinder, camera);
                }
            }
            for (Atom atom : molecule.getAtoms()) {
                cpRenderer.renderMesh(atom.getSphere(), camera);
            }
        }

        GL11.glFlush();
        GL11.glFinish();
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        float x = (float) input.getMouseX();

        float height = window.getHeight();
        float y = (float) input.getMouseY();
        y = height - y;
        ByteBuffer data = BufferUtils.createByteBuffer(3);
        GL11.glReadPixels((int)x, (int)y, 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, data);

        for (Molecule molecule : molecules) {
            Atom a = molecule.getAtom(data.get(0));
            for (Atom atom : molecule.getAtoms()) {
                if (atom != a && unselect) {
                    atom.setSelected(false);
                }
            }
            if (a != null) {
                a.toggleSelected();
            }
        }
        for (Molecule molecule : molecules) {
            Bond b = molecule.getBond(data.get(0));
            for (Bond bond : molecule.getBonds()) {
                if (bond != b && unselect) {
                    bond.setSelected(false);
                }
            }
            if (b != null) {
                b.toggleSelected();
            }
        }
    }

    /**
     * retrieves a selected button
     */
    private int getPressedButtons() {

        // get the mouse coords
        float mouseX = (float) input.getMouseX();
        float mouseY = (float) input.getMouseY();
        float width = window.getWidth();
        float height = window.getHeight();

        mouseX = 2 * mouseX / width - 1;
        mouseY = 1 - 2 * mouseY / height;

        int buttonID = -1;
        for (Button button : gui.getButtons()) {
            if (button.containsCoords(mouseX, mouseY)) {
                buttonID = button.getID();
                break;
            }
        }
        if (buttonID == Button.INFORMATION_BUTTON) {
            for (Molecule molecule : molecules) {
                for (Atom atom : molecule.getAtoms()) {
                    if (atom.isSelected()) {
                        System.out.println(atom.getAtomicAbbrName() + ", " + atom.getAtomicNumber());
                    }
                }
            }
        }
        return buttonID;
    }

    /**
     * render the game objects
     */
    private void render(boolean shouldSwapBuffers) {

        // render the molecules
        for (Molecule molecule : molecules) {
            for (Bond bond : molecule.getBonds()) {
                for (Cylinder cylinder : bond.getCylinders()) {
                    renderer.renderMesh(cylinder, camera, lightPosition, false);
//                    cpRenderer.renderMesh(cylinder, camera);
                }
            }
            for (Atom atom : molecule.getAtoms()) {
                renderer.renderMesh(atom.getSphere(), camera, lightPosition, false);
//                cpRenderer.renderMesh(atom.getSphere(), camera);
            }
        }

        // render selection molecules
        for (Molecule molecule : molecules) {
            for (Atom atom : molecule.getAtoms()) {
                if (atom.isSelected()) {
                    renderer.renderMesh(atom.getSelectionSphere(), camera, lightPosition, true);
                }
            }
            for (Bond bond : molecule.getBonds()) {
                if (bond.isSelected()) {
                    for (Cylinder cylinder : bond.getSelectionCylinders()) {
                        renderer.renderMesh(cylinder, camera, lightPosition, true);
                    }
                }
            }
        }

        // render the GUI
        for (Button button : gui.getButtons()) {
            guiRenderer.renderMesh(button);
        }

        // must be called at the end
        if (shouldSwapBuffers) {
            window.swapBuffers();
        }
    }

    /**
     * generates a molecule
     */
    private void generateMolecules() {
        molecules.add(new Benzaldehyde(new Vector3f(0, 0, 0)));
//        generateTestEmptyMolecule();
    }

    private void generateTestEmptyMolecule() {
        molecules.add(new Molecule(
                new Vector3f(0, 0, 0),
                new ArrayList<>() {{
                    add(new Carbon(new Vector3f(0)));
                }},
                new ArrayList<>() {{
                    add(new Bond(new Carbon(new Vector3f(0)), new Atom(Tetrahedral.getAtomCoord(0, 3.5f), Atom.TITANIUM), 1));
                    add(new Bond(new Carbon(new Vector3f(0)), new Atom(Tetrahedral.getAtomCoord(1, 3.5f), Atom.TITANIUM), 1));
                    add(new Bond(new Carbon(new Vector3f(0)), new Atom(Tetrahedral.getAtomCoord(2, 3.5f), Atom.TITANIUM), 1));
                    add(new Bond(new Carbon(new Vector3f(0)), new Atom(Tetrahedral.getAtomCoord(3, 3.5f), Atom.TITANIUM), 1));
                }}
                )
        );
    }
}