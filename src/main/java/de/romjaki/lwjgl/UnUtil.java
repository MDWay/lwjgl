package de.romjaki.lwjgl;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by RGR on 20.07.2017.
 */
public class UnUtil {
    private UnUtil() {
    }

    public static FloatBuffer createFloatBuffer(float[] vertices) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(vertices.length);
        buf.put(vertices);
        buf.flip();
        return buf;
    }

    public static IntBuffer createIntBuffer(int[] vertices) {
        IntBuffer buf = BufferUtils.createIntBuffer(vertices.length);
        buf.put(vertices);
        buf.flip();
        return buf;

    }
}
