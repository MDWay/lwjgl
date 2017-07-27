package de.romjaki.lwjgl.renderer;

import org.joml.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL20.glBindAttribLocation;

/**
 * Created by RGR on 21.07.2017.
 */
public class DefaultShader extends Shader {
    public static final DefaultShader INSTANCE = new DefaultShader();

    public DefaultShader() {
        super("shader");
    }

    @Override
    protected void bindAttribLocations() {
        glBindAttribLocation(getProgram(), 0, "vertex");
        glBindAttribLocation(getProgram(), 1, "pass_normal");
        glBindAttribLocation(getProgram(), 2, "texture");
    }

    public void loadProjection(Matrix4f proj) {
        loadMatrix4f("proj", proj);
    }

    public void loadSpeculars(float damper, float reflectivity) {
        loadFloat("damper", damper);
        loadFloat("reflectivity", reflectivity);
    }

    public void loadLights(List<Light> lights) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                Light light = lights.get(i);
                loadFLoat3("lights[" + i + "]", light.position().x, light.position().y, light.position().z);
                loadFLoat3("colors[" + i + "]", light.getColor().getRed() / 255.0f, light.getColor().getGreen() / 255.0f, light.getColor().getBlue() / 255.0f);
            } else {
                loadFLoat3("lights[" + i + "]", 0, 0, 0);
                loadFLoat3("colors[" + i + "]", 1, 1, 1);
            }
        }
    }

    public void loadTexture(int location) {
        loadInt("sampler", location);
    }

    public void loadView(Matrix4f viewMatrix) {
        loadMatrix4f("view", viewMatrix);
    }

    public void loadTransformation(Matrix4f transformationMatrix) {
        loadMatrix4f("trans", transformationMatrix);
    }
}
