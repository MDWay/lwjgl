package de.romjaki.lwjgl.renderer.geometric;

import de.romjaki.lwjgl.Main;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by RGR on 18.07.2017.
 */
public interface Entity {

    void input();

    default void move(float x, float y, float z) {
        move(new Vector3f(x, y, z));
    }

    void move(Vector3f vector3f);

    default void moveTo(float x, float y, float z) {
        moveTo(new Vector3f(x, y, z));
    }

    void moveTo(Vector3f move);

    Vector3f position();

    default float getX() {
        return position().x;
    }

    default float getY() {
        return position().y;
    }

    default float getZ() {
        return position().z;
    }

    float getScale();

    void setScale(float s);

    Vector3f getRotation();

    default void rotate(float x, float y, float z) {
        rotate(new Vector3f(x, y, z));
    }

    default void rotateTo(float x, float y, float z) {
        rotateTo(new Vector3f(x, y, z));
    }

    void rotateTo(Vector3f rotate);


    void rotate(Vector3f rotate);

    default Matrix4f createTransformationMatrix() {
        return Main.createTransformationMatrix(this);
    }
}
