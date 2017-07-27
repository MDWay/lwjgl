package de.romjaki.lwjgl;

import javafx.scene.paint.Color;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * Created by RGR on 18.07.2017.
 */
public class DisplayManager {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final Color BACKGROUND = Color.CORNFLOWERBLUE;
    private static long displayId = 0L;
    private static GLFWVidMode vidMode;

    private DisplayManager() {
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void initDisplay(boolean fullscreen) {
        displayId = glfwCreateWindow(WIDTH, HEIGHT, "Test", fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
        if (displayId == 0) {
            throw new IllegalStateException("Error during display creation");
        }
        vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(displayId, (vidMode.width() - WIDTH) / 2, (vidMode.height() - HEIGHT) / 2);
        glfwShowWindow(displayId);
        glfwMakeContextCurrent(displayId);
        GL.createCapabilities(true);
        glClearColor((float) BACKGROUND.getRed(), (float) BACKGROUND.getGreen(), (float) BACKGROUND.getBlue(), 1.0f);
    }

    public static boolean closeRequested() {
        return glfwWindowShouldClose(displayId);
    }

    public static long displayId() {
        return displayId;
    }
}
