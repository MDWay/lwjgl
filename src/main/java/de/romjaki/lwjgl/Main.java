package de.romjaki.lwjgl;

import de.romjaki.lwjgl.renderer.*;
import de.romjaki.lwjgl.renderer.geometric.Camera;
import de.romjaki.lwjgl.renderer.geometric.Entity;
import de.romjaki.lwjgl.renderer.geometric.TexturedModelEntity;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.Configuration;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

import static de.romjaki.lwjgl.DisplayManager.displayId;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by RGR on 18.07.2017.
 */
public class Main {
    public static long lastTime = System.currentTimeMillis();
    public static float deltaTime;
    static float[] vertices = {
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f

    };
    static float[] textureCoords = {

            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };
    static int[] indices = {
            0, 1, 3,
            3, 1, 2,
            4, 5, 7,
            7, 5, 6,
            8, 9, 11,
            11, 9, 10,
            12, 13, 15,
            15, 13, 14,
            16, 17, 19,
            19, 17, 18,
            20, 21, 23,
            23, 21, 22

    };

    public static void main(String... args) throws IOException {
        if (!glfwInit()) {
            throw new IllegalStateException("Error during glfwInit()");
        }

        boolean debug = Arrays.asList(args).contains("debug");
        GLFWErrorCallback.createThrow().set();
        Configuration.DEBUG.set(debug);
        Configuration.DEBUG_MEMORY_ALLOCATOR.set(debug);
        Configuration.DEBUG_STREAM.set(System.err);


        DisplayManager.initDisplay(false);
        glEnable(GL_TEXTURE_2D);
        Light light = new Light(0.5f, Color.red);
        light.moveTo(0, 5, 0);
        DefaultShader shader = DefaultShader.INSTANCE;
        shader.bind();
        shader.loadLights(Arrays.asList(light));
        shader.loadTexture(0);
        shader.unbind();
        RenderableEntity entity = new TexturedModelEntity(OBJLoader.loadOBJ("dragon.obj", "white.png"));
        EntityRenderer entityRenderer = new EntityRenderer(entity);
        MasterRenderer renderer = new MasterRenderer(entityRenderer);
        entity.moveTo(0, 0, 0);
        entity.rotate(0, (float) Math.PI, 0);
        Camera camera = new Camera(70, 0.1f, 1000);
        System.out.println("INIT COMPLETE");
        while (!DisplayManager.closeRequested()) {
            deltaTime = (System.currentTimeMillis() - lastTime) / 1000.0f;
            lastTime = System.currentTimeMillis();
            light.input();
            camera.input();
            entityRenderer.input();
            if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(displayId(), true);
            }
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            entity.bind(camera);
            entity.render(camera);
            entity.unbind(camera);
            renderer.render(camera);

            glfwSwapBuffers(displayId());

        }
        glfwTerminate();
    }

    public static Matrix4f createTransformationMatrix(Entity entity) {
        return new Matrix4f().identity().setTranslation(entity.position()).setRotationXYZ(entity.getRotation().x, entity.getRotation().y, entity.getRotation().z).scale(entity.getScale());
    }

}
