package de.romjaki.lwjgl.renderer;

import de.romjaki.lwjgl.renderer.geometric.BasicEntity;

import java.awt.*;

import static de.romjaki.lwjgl.DisplayManager.displayId;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by RGR on 25.07.2017.
 */
public class Light extends BasicEntity {

    private float brightness;
    private boolean lastPressed = false;
    private Color color;
    private boolean on = true;

    public Light(float brightness, Color color) {
        this.brightness = brightness;
        this.color = color;
    }

    public Light() {
        this(1.0f);
    }

    public Light(float brightness) {
        this(brightness, Color.white);
    }

    @Override
    public void input() {
        if (GL_TRUE == glfwGetKey(displayId(), GLFW_KEY_L)) {
            if (!lastPressed) {
                on = !on;
            }
            lastPressed = true;
        } else {
            lastPressed = false;
        }
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public Color getColor() {
        return new Color((int) (color.getRed() * brightness), (int) (color.getGreen() * brightness), (int) (color.getBlue() * brightness));
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
