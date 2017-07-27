package de.romjaki.lwjgl.renderer.geometric;

import de.romjaki.lwjgl.DisplayManager;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static de.romjaki.lwjgl.DisplayManager.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by RGR on 23.07.2017.
 */
public class Camera extends BasicEntity {


    private final float fov;
    private final float nearplane;
    private final float farplane;
    private float speed = 0.1f;
    private float dragspeed = 5f;

    private Float lastX = null;
    private Float lastY = null;

    public Camera(float fov, float nearplane, float farplane) {
        this.fov = fov;
        this.nearplane = nearplane;
        this.farplane = farplane;
    }

    public Matrix4f createViewMatrix() {
        return new Matrix4f().identity().setRotationXYZ(getRotation().x, getRotation().y, getRotation().z).translate(new Vector3f(position()).negate());
    }

    @Override
    public void input() {
        float forwardMovement = 0.0f;
        float sidewardMovement = 0.0f;
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_A)) {
            sidewardMovement -= speed;
        }
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_D)) {
            sidewardMovement += speed;
        }
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_SPACE)) {
            move(0f, speed, 0f);
        }
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_LEFT_SHIFT)) {
            move(0f, -speed, 0f);
        }
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_W)) {
            forwardMovement -= speed;
        }
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_S)) {
            forwardMovement += speed;
        }
        if (GLFW_PRESS == glfwGetMouseButton(displayId(), GLFW_MOUSE_BUTTON_MIDDLE)) {
            DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

            glfwGetCursorPos(displayId(), x, y);

            x.rewind();
            y.rewind();

            float newX = (float) x.get();
            float newY = (float) y.get();

            float dY = 0;
            float dX = 0;

            if (lastX != null) {
                dY = lastX - newX;
                dX = lastY - newY;
            }

            lastX = newX;
            lastY = newY;

            rotate(dX / getWIDTH() * speed * dragspeed, dY / getHEIGHT() * speed * dragspeed, 0);
        } else {
            lastX = null;
            lastY = null;
        }
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_Q)) {
            rotate(0, 0, -speed);
        }
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_E)) {
            rotate(0, 0, speed);
        }
        if (GLFW_TRUE == glfwGetKey(displayId(), GLFW_KEY_R)) {
            rotateTo(0, 0, 0);
        }
        if (forwardMovement != 0.0f) {
            float dX = (float) (forwardMovement * Math.sin(-getRotation().y + 2 * Math.PI));
            float dZ = (float) (forwardMovement * Math.cos(-getRotation().y + 2 * Math.PI));
            move(dX, 0, dZ);
        }
        if (sidewardMovement != 0.0f) {
            float dZ = (float) (sidewardMovement * Math.sin(getRotation().y + 2 * Math.PI));
            float dX = (float) (sidewardMovement * Math.cos(getRotation().y + 2 * Math.PI));
            move(dX, 0, dZ);
        }
    }


    public Matrix4f createProjectionMatrix() {
        float aspectRatio = (float) DisplayManager.getWIDTH() / (float) DisplayManager.getHEIGHT();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = farplane - nearplane;

        Matrix4f projectionMatrix = new Matrix4f();

        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((farplane + nearplane) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * nearplane * farplane) / frustum_length));
        projectionMatrix.m33(0);
        return projectionMatrix;
    }
}