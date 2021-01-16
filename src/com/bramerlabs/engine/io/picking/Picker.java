package com.bramerlabs.engine.io.picking;

import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.molecular.molecule.atom.Atom;

public class Picker {

    // the camera of the scene
    private Camera camera;

    // the window
    private Window window;

    // the input device
    private Input input;

    // the projection and view matrices
    private Matrix4f projection, view;

    /**
     * default constructor
     * @param camera - the camera in the window
     * @param window - the display window
     * @param input - the object for handling input
     */
    public Picker(Camera camera, Window window, Input input) {
        this.camera = camera;
        this.window = window;
        this.projection = window.getProjectionMatrix();
        this.input = input;
    }

    public Atom getSelectedAtom() {
        return null;
    }

}