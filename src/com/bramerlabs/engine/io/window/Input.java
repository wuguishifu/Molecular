package com.bramerlabs.engine.io.window;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL46;

public class Input {

    private GLFWKeyCallback keyboard;
    private GLFWMouseButtonCallback mouseButtons;
    private GLFWCursorPosCallback cursorPosition;
    private GLFWWindowSizeCallback windowSize;
    private GLFWWindowPosCallback windowPosition;
    private GLFWScrollCallback scrollWheel;

    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private double mouseX;
    private double mouseY;

    private double prevMouseX;
    private double prevMouseY;

    private double scrollX;
    private double scrollY;

    private int windowHeight;
    private int windowWidth;
    private boolean resized;

    private int windowX;
    private int windowY;

    public Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        cursorPosition = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                prevMouseX = mouseX;
                prevMouseY = mouseY;
                mouseX = xpos;
                mouseY = ypos;
            }
        };

        windowSize = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                windowWidth = width;
                windowHeight = height;
                GL46.glViewport(0, 0, width, height);
                resized = true;
            }
        };

        windowPosition = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                windowX = xpos;
                windowY = ypos;
            }
        };

        scrollWheel = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public void destroy() {
        keyboard.free();
        mouseButtons.free();
        cursorPosition.free();
        scrollWheel.free();
        windowSize.free();
        windowPosition.free();
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public boolean isWindowResized() {
        return resized;
    }

    public int getWindowX() {
        return windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public void setWindowX(int x) {
        windowX = x;
    }

    public void setWindowY(int y) {
        windowY = y;
    }

    public void setResized(boolean bool) {
        resized = bool;
    }

    public boolean isKeyDown(int key) {
        return keys[key];
    }

    public boolean isMouseButtonDown(int button) {
        return buttons[button];
    }

    public void setKeyDown(int key, boolean bool) {
        keys[key] = bool;
    }

    public void setButtonDown(int button, boolean bool) {
        buttons[button] = bool;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getPrevMouseX() {
        return prevMouseX;
    }

    public double getPrevMouseY() {
        return prevMouseY;
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallback() {
        return mouseButtons;
    }

    public GLFWCursorPosCallback getCursorPositionCallback() {
        return cursorPosition;
    }

    public GLFWWindowSizeCallback getWindowSizeCallback() {
        return windowSize;
    }

    public GLFWWindowPosCallback getWindowPositionCallback() {
        return windowPosition;
    }

    public GLFWScrollCallback getScrollWheelCallback() {
        return scrollWheel;
    }
}