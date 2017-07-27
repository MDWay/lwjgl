package de.romjaki.lwjgl;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

/**
 * Created by RGR on 20.07.2017.
 */
public class GLElementBuffer extends GLBuffer {
    private int id;

    public GLElementBuffer(int[] order) {
        super(GL_ELEMENT_ARRAY_BUFFER, order.length);
        data(order, GL_STATIC_DRAW);
    }

    @Override
    public void cleanUp() {

    }
}
