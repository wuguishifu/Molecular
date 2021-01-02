package com.bramerlabs.engine.io.window;

import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Window {

    // the window title
    private static final String TITLE = "Design Buddies Game Jam!";

    // the framerate of the game
    private static int FRAMERATE = 1; // this designates 60 fps

    // get the fullscreen size
    private DisplayMode displayMode;

    // window dimensions and position
    private int width, height;
    private int windowX, windowY;

    // background color
    private static final Color backgroundColorAsColor = new Color(204, 232, 220);
    private static final Vector3f bgc = Vector3f.divide(new Vector3f(
            backgroundColorAsColor.getRed(),
            backgroundColorAsColor.getGreen(),
            backgroundColorAsColor.getBlue()
    ), new Vector3f(255.0f));

    // default window dimensions
    private int defaultWidth, defaultHeight;

    // the window handle
    private long windowHandle;

    // framerate calculation
    private int frames;
    private long time;

    // window altering
    private boolean isFullscreen = false;

    // window callbacks
    Input input;

    // the projection matrix
    private Matrix4f projection;

    /**
     * constructor for specified input
     * @param input - the input for handling callbacks in this window
     */
    public Window(Input input) {
        // get the display mode
        this.displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

        // set default width, height variables
        this.defaultWidth = displayMode.getWidth() / 2;
        this.defaultHeight = displayMode.getHeight() / 2;

        // set the current width, height to the default
        this.width = defaultWidth;
        this.height = defaultHeight;

        // create a projection matrix
        projection = Matrix4f.projection(70.0f, (width/(float)height), 0.1f, 1000f);

        this.input = input;
    }

    /**
     * initialize the window
     */
    public void create() {
        // the start time - used for framerate calculations
        time = System.currentTimeMillis();

        // attempt to initialize the GLFW window
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize the GLFW.");
        }

        // set the window hints
        GLFW.glfwDefaultWindowHints(); // default window hints
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE); // if the window is maximized
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE); // if the window is decorated

        // create the window
        // used for GLFW variables
        windowHandle = GLFW.glfwCreateWindow(
                isFullscreen ? displayMode.getWidth() : width,
                isFullscreen ? displayMode.getHeight() : height,
                TITLE,
                isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0,
                0 // null value
        );

        // check to see if the window was properly initialized
        if (windowHandle == 0) { // null value
            throw new RuntimeException("Failed to create the GLFW window.");
        }

        // get resolution of current monitor
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        // center the window
        if (!isFullscreen) {
            assert vidMode != null;
            GLFW.glfwSetWindowSize(windowHandle, vidMode.width() / 2, vidMode.height() / 2);
            GLFW.glfwSetWindowPos(windowHandle, vidMode.width() / 4, vidMode.height() / 4);

            // store the centered position of the window
            this.windowX = vidMode.width() / 4;
            this.windowY = vidMode.height() / 4;
        }

        // set the GLFW context
        GLFW.glfwMakeContextCurrent(windowHandle);

        // make an OpenGL window - must be done before running any OpenGL methods
        GL.createCapabilities();

        // only renders objects that are facing the camera
        GL11.glEnable(GL_CULL_FACE);

        // render a vertex if and only if it appears in front of another vertex
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glClear(GL_DEPTH_BUFFER_BIT);
        GL11.glDepthFunc(GL_LESS);

        // set the clear color
        GL46.glClearColor(bgc.getX(), bgc.getY(), bgc.getZ(), 1);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // set the viewport
        GL46.glViewport(0, 0, defaultWidth, defaultHeight);

        // set the callbacks
        GLFW.glfwSetKeyCallback(windowHandle, input.getKeyboardCallback());
        GLFW.glfwSetMouseButtonCallback(windowHandle, input.getMouseButtonsCallback());
        GLFW.glfwSetCursorPosCallback(windowHandle, input.getCursorPositionCallback());
        GLFW.glfwSetWindowSizeCallback(windowHandle, input.getWindowSizeCallback());
        GLFW.glfwSetWindowPosCallback(windowHandle, input.getWindowPositionCallback());
        GLFW.glfwSetScrollCallback(windowHandle, input.getScrollWheelCallback());

        // set the window's position callback's position
        assert vidMode != null;
        input.setWindowX(vidMode.width() / 4);
        input.setWindowY(vidMode.height() / 4);

        // show the window
        GLFW.glfwShowWindow(windowHandle);

        // set the framerate of the window
        GLFW.glfwSwapInterval(FRAMERATE);
        GLFW.glfwSetWindowTitle(windowHandle, TITLE + " | FPS: " + frames);
    }

    public void update() {
        // decide if the window should close
        if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            GLFW.glfwSetWindowShouldClose(windowHandle, true);
        }

        // change the window size, fullscreen option if necessary
        // set window fullscreen
        if (input.isKeyDown(GLFW.GLFW_KEY_F)) {
            isFullscreen = !isFullscreen;
            input.setResized(!isFullscreen);
            if (isFullscreen) {
                windowX = input.getWindowX();
                windowY = input.getWindowY();
                GLFW.glfwSetWindowMonitor( // set the window to fullscreen
                        windowHandle,
                        GLFW.glfwGetPrimaryMonitor(),
                        0,
                        0,
                        defaultWidth,
                        defaultHeight,
                        GLFW.GLFW_REFRESH_RATE
                );
                GLFW.glfwSetWindowSize( // set the size of the window to the monitor size
                        windowHandle,
                        displayMode.getWidth(),
                        displayMode.getHeight()
                );
            } else {
                GLFW.glfwSetWindowMonitor( // set the window to not fullscreen
                        windowHandle,
                        0,
                        this.windowX,
                        this.windowY,
                        defaultWidth,
                        defaultHeight,
                        GLFW.GLFW_REFRESH_RATE
                );
            }
            GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        }

        // clear the screen
        GL46.glClearColor(bgc.getX(), bgc.getY(), bgc.getZ(), 1);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // poll GLFW for callbacks
        GLFW.glfwPollEvents();

        // calculate framerate
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(windowHandle, TITLE + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    /**
     * swap the buffers of the window
     */
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    /**
     * getter method
     * @return - true if the window should close
     */
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void close() {
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
    }

    /**
     * release the window
     */
    public void destroy() {
        input.destroy();
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }

    /**
     * sets the current mouse state of the window
     * @param lock - whether or not the mouse should be locked
     */
    public void mouseState(boolean lock) {
        GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }

    /**
     * toggles fullscreen
     */
    public void toggleFullscreen() {
        this.isFullscreen = !isFullscreen;
    }

    /**
     * getter method
     * @return - the title of this window
     */
    public static String getTitle() {
        return TITLE;
    }

    /**
     * getter method
     * @return - the width of the window
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * getter method
     * @return - the height of the window
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * getter method
     * @return - the window handle
     */
    public long getWindowHandle() {
        return this.windowHandle;
    }

    /**
     * getter method
     * @return - true if the window is fullscreen
     */
    public boolean isFullscreen() {
        return this.isFullscreen;
    }

    /**
     * getter method
     * @return - this projection matrix
     */
    public Matrix4f getProjectionMatrix() {
        return this.projection;
    }
}