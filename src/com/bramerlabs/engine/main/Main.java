package com.bramerlabs.engine.main;

import com.bramerlabs.engine.graphics.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.Camera;
import com.bramerlabs.engine.objects.game_objects.Cube;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

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
    private ArrayList<Cube> cubes = new ArrayList<>();

    // the camera
    public Camera camera = new Camera(new Vector3f(0, 0, 2), new Vector3f(0, 0, 0), input);

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
            if (input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
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

        // create game objects here
        String dPath = "/textures/3ttest.png";
        cubes.add(new Cube(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), "/textures/3ttest.png"));
        for (Cube cube : cubes) {
            cube.createMesh();
        }

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
        for (Cube cube : cubes) {
            cube.destroy();
        }

        // release the shader
        shader.destroy();
    }

    /**
     * begins the thread
     */
    public void start() {
        Thread main = new Thread(this, "Game Buddies Game Jam!");
        main.start();
    }

    /**
     * update the window and game objects
     */
    private void update() {

        // update the window
        window.update();

        // update the camera
        camera.update(cubes.get(0));
    }

    /**
     * render the game objects
     */
    private void render() {
        // render the game objects
        for (Cube cube : cubes) {
            renderer.renderMesh(cube, camera);
        }

        // must be called at the end
        window.swapBuffers();
    }
}
