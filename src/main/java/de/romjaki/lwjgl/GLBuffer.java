package de.romjaki.lwjgl;

import static org.lwjgl.opengl.GL15.*;

/**
 * Created by RGR on 20.07.2017.
 */
public class GLBuffer implements IBindable {
    protected int id;
    protected int target;
    private int dataLength;

    public GLBuffer(int target, int dataLength) {
        id = glGenBuffers();
        this.dataLength = dataLength;
        this.target = target;
    }

    public void data(int[] vertices, int usage) {
        bind();
        glBufferData(target, UnUtil.createIntBuffer(vertices), usage);
        unbind();
    }

    public void data(float[] vertices, int usage) {
        bind();
        glBufferData(target, UnUtil.createFloatBuffer(vertices), usage);
        unbind();
    }

    @Override
    public void bind() {
        glBindBuffer(target, id);
    }

    @Override
    public void unbind() {
        glBindBuffer(target, 0);
    }

    @Override
    public void cleanUp() {

    }

    public int dataLength() {
        return dataLength;
    }
}
