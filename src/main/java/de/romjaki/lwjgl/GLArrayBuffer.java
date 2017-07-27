package de.romjaki.lwjgl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * Created by RGR on 20.07.2017.
 */
public class GLArrayBuffer extends GLBuffer {
    private int type;
    private int dim;

    public GLArrayBuffer(float[] vertices, int dim) {
        this(dim, GL_FLOAT, vertices.length);
        data(vertices, GL_STATIC_DRAW);
    }

    private GLArrayBuffer(int dim, int type, int rawDataLength) {
        super(GL_ARRAY_BUFFER, rawDataLength / dim);
        this.dim = dim;
        this.type = type;
    }

    public void activeVertexAttribPointer(int index, int stride, long pointer) {
        bind();
        glVertexAttribPointer(index, dim, type, false, 0, 0);
        unbind();
    }

    public int getDimension() {
        return dim;
    }

    public int getType() {
        return type;
    }

    public void activeVertexPointer(int stride, long pointer) {
        bind();
        glVertexPointer(dim, type, stride, pointer);
        unbind();
    }

    public void activeTexCoordPointer(int stride, long pointer) {
        bind();
        glTexCoordPointer(dim, type, stride, pointer);
        unbind();
    }
}
