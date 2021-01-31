package com.bramerlabs.molecular.main;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.gui.Gui;
import com.bramerlabs.engine.io.gui.gui_object.buttons.Button;
import com.bramerlabs.engine.io.gui.gui_object.buttons.*;
import com.bramerlabs.engine.io.gui.gui_render.GuiRenderer;
import com.bramerlabs.engine.io.picking.CPRenderer;
import com.bramerlabs.engine.io.screenshots.ScreenshotTaker;
import com.bramerlabs.engine.io.text.font_mesh_creator.FontType;
import com.bramerlabs.engine.io.text.font_mesh_creator.GUIText;
import com.bramerlabs.engine.io.text.font_rendering.Loader;
import com.bramerlabs.engine.io.text.font_rendering.TextMaster;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.shapes.Cylinder;
import com.bramerlabs.molecular.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.file_io.MoleculeIO;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.default_molecules.Benzaldehyde;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;
import org.lwjglx.BufferUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
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
    private boolean shouldRenderGUI = true;

    // GUI variables
    private Gui gui; // the gui
    private GuiRenderer guiRenderer; // used for rendering the gui
    private Shader guiShader;

    // input handling variables
    private Input input = new Input(); // used to handle inputs
    private boolean lastFrameRightButtonDown = false; // if the right button was down in the previous frame
    private boolean lastFrameLeftButtonDown = false; // if the left button was down in the previous frame
    private boolean displayUsingCPRenderer = true; // how the molecule should be displayed - used for testing
    private CPRenderer cpRenderer; // used for color picking
    private Shader cpShader;
    private int pressedButtonID = 0;
    private boolean buttonTemp = false;
    private boolean buttonStaysPressed = false;

    // selected atoms
    private int numMaxSelectedItems = 1;
    boolean canSelectAtoms = true;
    private ArrayList<Atom> selectedAtoms = new ArrayList<>();
    private ArrayList<Bond> selectedBonds = new ArrayList<>();

    // camera variables
    public Camera camera = new Camera(new Vector3f(0, 0, 2), new Vector3f(0, 0, 0), input); // the camera
    public static final Vector3f LOOKING_AT = new Vector3f(0); // the point at which the camera is looking
    private Vector3f lightPosition = new Vector3f(0, 100, 0); // the position of the light

    // the molecules
    private ArrayList<Molecule> molecules;

    // the file used for loading the molecule
    private static String pathToLoadFile;
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Molecular Files", "mol");

    // for displaying text
    FontType font;
    String renderText = "";
    GUIText displayGUIText;

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
//        camera.setIdealPosition();

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

        // initialize the text
        Loader loader = new Loader();
        TextMaster.init(loader);
        font = new FontType(loader.loadTexture("arial"), new File("resources/fonts/arial.fnt"));
        displayGUIText = new GUIText(renderText, 1f, font, new Vector2f(0, 0.02f), 1f, true);

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

        TextMaster.cleanUp();
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
        gui.addButton(LoadButton.getInstance(2*size, 2*window.getHeight()-size, size, size, window));
        gui.addButton(NewFileButton.getInstance(3*size, 2*window.getHeight()-size, size, size, window));
        gui.addButton(ProtractorButton.getInstance(4*size, 2*window.getHeight()-size, size, size, window));
        gui.addButton(ScreenshotButton.getInstance(5*size, 2*window.getHeight()-size, size, size, window));

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

        // random things lmao
        // update the RGB
//        updateRGB();
//        camera.incHorizontalAngle(100);
//        renderText = camera.getLookingAt().toString();

        // update the window
        window.update();

        // update the molecule occasionally
        for (Molecule m : molecules) {
            m.update(time);
        }

        // clear the screen
        GL46.glClearColor(Window.bgc.getX(), Window.bgc.getY(), Window.bgc.getZ(), 1);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        // handle inputs and button presses
        boolean shouldSwapBuffers = handleInputs();
        if (!getPressedButtons()) {
            camera.updateArcball();
        }
        if (!input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && lastFrameLeftButtonDown) {
            handleButtonPress();
        }

        // update the text object
        displayGUIText.setTextString(renderText);
        displayGUIText.loadText();

        lastFrameLeftButtonDown = input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT);

        return shouldSwapBuffers;
    }

    /**
     * handles inputs
     * @return - if the buffers should be swapped
     */
    private boolean handleInputs() {

        // update the mouse picker
        boolean shouldSwapBuffers = true;
        boolean currentFrameRightButtonDown = input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
        if (lastFrameRightButtonDown && !currentFrameRightButtonDown) {
            getSelectedAtom();
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
     */
    private void getSelectedAtom() {
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

        // select an atom or bond
        boolean selectedMolecule = false;
        for (Molecule molecule : molecules) {
            for (Atom a : molecule.getAtoms()) {
                if (a.getSphere().getID() == data.get(0)) {
                    if (!selectedAtoms.contains(a)) {
                        selectedAtoms.add(0, a);
                        renderText = "Atom: " + a.getAtomicAbbrName() + ", charge: " + a.getCharge();
                    }
                    while (selectedAtoms.size() > numMaxSelectedItems) {
                        selectedAtoms.remove(selectedAtoms.size() - 1);
                    }
                    selectedMolecule = true;
                    break;
                }
            }
        }

        if (!selectedMolecule) {
            setDefaults();
            selectedAtoms.clear(); // empty the array list
            renderText = "";
        }
    }

    /**
     * retrieves a selected button
     */
    private boolean getPressedButtons() {

        int overCurrentButton = 0;

        boolean isOverButton = false;
        if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            // get the mouse coords
            float mouseX = (float) input.getMouseX();
            float mouseY = (float) input.getMouseY();
            float width = window.getWidth();
            float height = window.getHeight();

            mouseX = 2 * mouseX / width - 1;
            mouseY = 1 - 2 * mouseY / height;

            for (Button button : gui.getButtons()) {
                if (button.containsCoords(mouseX, mouseY)) {
                    if (button.getID() == Button.BUTTON_PROTRACTOR) {
                        buttonStaysPressed = true;
                    }
                    pressedButtonID = button.getID();
                    overCurrentButton = button.getID();
                    button.setState(Button.STATE_PRESSED);
                    break;
                }
            }

            for (Button button : gui.getButtons()) {
                button.setState(Button.STATE_RELEASED);
            }
            if (overCurrentButton != 0) {
                isOverButton = true;
                gui.getButton(pressedButtonID).setState(Button.STATE_PRESSED);
            } else {
                pressedButtonID = 0;
            }
        } else {
            for (Button button : gui.getButtons()) {
                if (!buttonStaysPressed) {
                    button.setState(Button.STATE_RELEASED);
                }
            }
        }

        return isOverButton;
    }

    /**
     * handles button presses
     */
    private void handleButtonPress() {
        if (pressedButtonID == 0) {
            return;
        }

        // handle the protractor button
        if (pressedButtonID == Button.BUTTON_PROTRACTOR) {
            if (!buttonTemp) {
                buttonTemp = true;
                selectedAtoms.clear();
                numMaxSelectedItems = 3; // set the max number of selected atoms
            }
            if (selectedAtoms.size() == 3) {
                Vector3f v1 = Vector3f.subtract(selectedAtoms.get(0).getPosition(), selectedAtoms.get(1).getPosition());
                Vector3f v2 = Vector3f.subtract(selectedAtoms.get(2).getPosition(), selectedAtoms.get(1).getPosition());
                float angle = (float)Math.toDegrees(Vector3f.angleBetween(v1, v2));
                int angleInt = (int) (angle * 100);
                angle = angleInt / 100.f;
                renderText = "Angle: " + angle + " degrees";
                numMaxSelectedItems = 1;
                pressedButtonID = 0;
                buttonTemp = false;
                buttonStaysPressed = false;
            }
        } else if (pressedButtonID == Button.BUTTON_SCREENSHOT) {
            ScreenshotTaker.takeScreenshot(window.getWidth(), window.getHeight());
            pressedButtonID = 0;
        } else if (pressedButtonID == Button.BUTTON_INFORMATION) {
            InformationButton.onClick(this.molecules.get(0));
            pressedButtonID = 0;
        } else if (pressedButtonID == Button.BUTTON_SAVE) {
            String name = System.currentTimeMillis() + ".mol";
            MoleculeIO.saveMolecule(name, this.molecules.get(0));
            pressedButtonID = 0;
        } else if (pressedButtonID == Button.BUTTON_LOAD) {
            loadMoleculeFromFile();
            pressedButtonID = 0;
        }
    }

    /**
     * renders the molecule
     */
    private void renderMolecule() {
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

        for (Atom atom : selectedAtoms) {
            renderer.renderMesh(atom.getSelectionSphere(), camera, lightPosition, true);
        }
        for (Bond bond : selectedBonds) {
            for (Cylinder cylinder : bond.getSelectionCylinders()) {
                renderer.renderMesh(cylinder, camera, lightPosition, true);
            }
        }
    }

    /**
     * loads a molecule from a specific file
     */
    private void loadMoleculeFromFile() {
        JFileChooser chooser = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent)
                    throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                // config here as needed - just to see a difference
                dialog.setLocationByPlatform(true);
                // might help - can't know because I can't reproduce the problem
                dialog.setAlwaysOnTop(true);
                return dialog;
            }
        };

        chooser.setFileFilter(filter);
        chooser.setFocusable(true);
        chooser.requestFocus();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            molecules.clear();
            molecules.add(MoleculeIO.loadMolecule(chooser.getSelectedFile()));
        }
    }

    /**
     * render the game objects
     */
    private void render(boolean shouldSwapBuffers) {
        renderMolecule();

        if (shouldRenderGUI) {

            // render the GUI
            for (Button button : gui.getButtons()) {
                guiRenderer.renderMesh(button);
            }

            TextMaster.render();
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
        this.molecules.add(new Benzaldehyde(new Vector3f(0)));
    }

    /**
     * resets the buttons
     */
    private void setDefaults() {
        pressedButtonID = 0;
        buttonTemp = false;
        buttonStaysPressed = false;
        numMaxSelectedItems = 1;
    }

    private void pinkMode() {
        renderer.setLightColor(Vector3f.divide(new Vector3f(Color.PINK), new Vector3f(255)));
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