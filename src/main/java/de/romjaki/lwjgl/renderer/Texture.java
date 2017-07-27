package de.romjaki.lwjgl.renderer;

import de.romjaki.lwjgl.IBindable;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Created by RGR on 20.07.2017.
 */
public class Texture implements IBindable {
    int sampler;
    private int id;
    private int width;
    private int height;
    private float reflectivity = 3;
    private float damper = 10;

    public Texture(String name, int sampler) {
        this.sampler = sampler;
        try {
            BufferedImage im = ImageIO.read(new File("src/main/resources/textures/" + name));
            width = im.getWidth();
            height = im.getHeight();
            int[] pixels = im.getRGB(0, 0, width, height, null, 0, width);
            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    Color pixel = new Color(pixels[j * width + i]);
                    buffer.put((byte) pixel.getRed());
                    buffer.put((byte) pixel.getGreen());
                    buffer.put((byte) pixel.getBlue());
                    buffer.put((byte) pixel.getAlpha());
                }
            }
            buffer.flip();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        } catch (IOException e) {
        }
    }

    @Override
    public void bind() {
        glActiveTexture(GL_TEXTURE0 + sampler);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
        glActiveTexture(0);
    }

    public float getDamper() {
        return damper;
    }

    public void setDamper(float damper) {
        this.damper = damper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
