package com.bramerlabs.molecular.main;

import com.bramerlabs.engine.graphics.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.gui.Gui;
import com.bramerlabs.engine.io.gui.gui_object.buttons.Button;
import com.bramerlabs.engine.io.gui.gui_object.buttons.InformationButton;
import com.bramerlabs.engine.io.gui.gui_object.buttons.NewFileButton;
import com.bramerlabs.engine.io.gui.gui_object.buttons.SaveButton;
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
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.Benzaldehyde;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;
import org.lwjglx.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Main implements Runnable {

    // controlling RGB lights
    int alpha = 0;
    int timer = 0;
    int[] rgbLights = new int[] {
            0, 4, 8, 13, 17, 21, 25, 30, 34, 38, 42, 47, 51, 55, 59, 64, 68, 72, 76,
            81, 85, 89, 93, 98, 102, 106, 110, 115, 119, 123, 127, 132, 136, 140, 144,
            149, 153, 157, 161, 166, 170, 174, 178, 183, 187, 191, 195, 200, 204, 208,
            212, 217, 221, 225, 229, 234, 238, 242, 246, 251, 255
    };

    // main rendering variables
    private Window window; // the main window of the game
    private Shader shader; // the shaders used to paint textures
    private Renderer renderer; // used to render objects
    private static int time = 0; // the time of the window

    // GUI variables
    private Gui gui; // the gui
    private GuiRenderer guiRenderer; // used for rendering the gui
    private Shader guiShader;

    // input handling variables
    private Input input = new Input(); // used to handle inputs
    private boolean lastFrameRightButtonDown = false; // if the last frame had the right mouse button down
    private boolean displayUsingCPRenderer = false; // how the molecule should be displayed - used for testing
    private CPRenderer cpRenderer; // used for color picking
    private Shader cpShader;

    // camera variables
    public Camera camera = new Camera(new Vector3f(0, 0, 2), new Vector3f(0, 0, 0), input); // the camera
    public static final Vector3f LOOKING_AT = new Vector3f(0); // the point at which the camera is looking
    private Vector3f lightPosition = new Vector3f(0, 100, 0); // the position of the light

    // the molecules
    private ArrayList<Molecule> molecules;

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

        int size = 200;
        gui.addButton(InformationButton.getInstance(0, 2*window.getHeight()-size, size, size, window));
        gui.addButton(SaveButton.getInstance(size, 2*window.getHeight()-size, size, size, window));
        gui.addButton(NewFileButton.getInstance(2*size, 2*window.getHeight()-size, size, size, window));

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

//        updateRGB();

        // update the window
        window.update();

        // update the molecule occasionally
        for (Molecule m : molecules) {
            m.update(time);
        }

        // clear the screen
        GL46.glClearColor(Window.bgc.getX(), Window.bgc.getY(), Window.bgc.getZ(), 1);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        boolean shouldSwapBuffers = handleInputs();

        if (!getPressedButtons()) {
            camera.updateArcball();
        }
        return shouldSwapBuffers;
    }

    /**
     * handles inputs
     * @return - if the buffers should be swapped
     */
    private boolean handleInputs() {

        // handle pressing buttons
        boolean unselectAtom = true;

        // update the mouse picker
        boolean shouldSwapBuffers = true;
        boolean currentFrameRightButtonDown = input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
        if (lastFrameRightButtonDown && !currentFrameRightButtonDown) {

            // get the mouse coords
            float mouseX = (float) input.getMouseX();
            float mouseY = (float) input.getMouseY();
            float width = window.getWidth();
            float height = window.getHeight();

            mouseX = 2 * mouseX / width - 1;
            mouseY = 1 - 2 * mouseY / height;

            for (Button button : gui.getButtons()) {
                if (button.containsCoords(mouseX, mouseY)) {
                    unselectAtom = false;
                    break;
                }
            }

            getSelectedAtom(unselectAtom);
            shouldSwapBuffers = false;
        }
        lastFrameRightButtonDown = currentFrameRightButtonDown;

        // check to see how the molecule should be rendered
        displayUsingCPRenderer = input.isKeyDown(GLFW.GLFW_KEY_C);

        // camera motion
        // translation of the camera
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && input.isKeyDown(GLFW.GLFW_KEY_LEFT_ALT)) camera.translate();
        // reset the camera position, rotation, and distance
        if (input.isKeyDown(GLFW.GLFW_KEY_ENTER)) camera.resetPosition(LOOKING_AT);

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
    private boolean getPressedButtons() {
        boolean isOverButton = false;
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
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
                    button.setState(Button.STATE_PRESSED);
                    break;
                }
            }

            for (Button button : gui.getButtons()) {
                button.setState(Button.STATE_RELEASED);
            }
            if (gui.getButton(buttonID) != null) {
                isOverButton = true;
                gui.getButton(buttonID).setState(Button.STATE_PRESSED);
            }

        } else {
            for (Button button : gui.getButtons()) {
                button.setState(Button.STATE_RELEASED);
            }
        }
        return isOverButton;
    }

    /**
     * render the game objects
     */
    private void render(boolean shouldSwapBuffers) {

        // render the molecules
        for (Molecule molecule : molecules) {
            for (Bond bond : molecule.getBonds()) {
                for (Cylinder cylinder : bond.getCylinders()) {
                    if (displayUsingCPRenderer) {
                        cpRenderer.renderMesh(cylinder, camera);
                    } else {
                        renderer.renderMesh(cylinder, camera, lightPosition, false);
                    }
                }
            }
            for (Atom atom : molecule.getAtoms()) {
                if (displayUsingCPRenderer) {
                    cpRenderer.renderMesh(atom.getSphere(), camera);
                } else {
                    renderer.renderMesh(atom.getSphere(), camera, lightPosition, false);
                }
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
    }

    /**
     * secret feature???
     * cycles the color through RGB
     */
    private void updateRGB() {
        // update the global timer
        float red;
        float green;
        float blue;
        timer ++;
        if (timer > 10) {
            timer = 0;
        }
        alpha ++;
        if (alpha > 360) alpha = 0;
        if (alpha < 60) {
            red = 1.0f;
            green = rgbLights[alpha]/255.f;
            blue = 0.0f;
        } else if (alpha < 120) {
            red = rgbLights[120 - alpha]/255.f;
            green = 1.0f;
            blue = 0.0f;
        } else if (alpha < 180) {
            red = 0.0f;
            green = 1.0f;
            blue = rgbLights[alpha - 120]/255.f;
        } else if (alpha < 240) {
            red = 0.0f;
            green = rgbLights[240-alpha]/255.f;
            blue = 1.0f;
        } else if (alpha < 300) {
            red = rgbLights[alpha-240]/255.f;
            green = 0.0f;
            blue = 1.0f;
        } else {
            red = 1.0f;
            green = 0.0f;
            blue = rgbLights[360-alpha]/255.f;
        }
        renderer.setLightColor(new Vector3f(red, green, blue));
    }
}