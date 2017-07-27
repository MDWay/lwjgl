package de.romjaki.lwjgl.renderer.geometric;

import org.joml.Vector3f;

/**
 * Created by RGR on 18.07.2017.
 */
public abstract class BasicEntity implements Entity {
    Vector3f position = new Vector3f();
    Vector3f rotation = new Vector3f();
    float scale = 1.0f;

    public BasicEntity() {
        this(new Vector3f(0f, 0f, 0f));
    }

    public BasicEntity(Vector3f position) {
        moveTo(position);
    }

    @Override
    public void move(Vector3f vector3f) {
        this.position.add(vector3f);
    }

    @Override
    public void moveTo(Vector3f move) {
        this.position = move;
    }

    @Override
    public Vector3f position() {
        return position;
    }

    @Override
    public Vector3f getRotation() {
        return rotation;
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public void setScale(float s) {
        scale = s;
    }

    @Override
    public void rotateTo(Vector3f rotate) {
        this.rotation = rotate;
    }

    @Override
    public void rotate(Vector3f rotate) {
        this.rotation.add(rotate);
    }

}
