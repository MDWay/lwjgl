package de.romjaki.lwjgl.renderer.geometric;

import de.romjaki.lwjgl.GLArrayBuffer;
import de.romjaki.lwjgl.GLElementBuffer;
import de.romjaki.lwjgl.renderer.DefaultShader;
import de.romjaki.lwjgl.renderer.Renderable;
import de.romjaki.lwjgl.renderer.Shader;
import de.romjaki.lwjgl.renderer.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

/**
 * Created by RGR on 24.07.2017.
 */
public class TexturedModel implements Renderable {
    private GLArrayBuffer normals;
    private GLElementBuffer indices;
    private Texture texture;
    private GLArrayBuffer vertices;
    private int v_count;
    private DefaultShader shader;
    private GLArrayBuffer textureCoords;

    public TexturedModel(Texture texture, float[] vertices, int[] order, float[] textureCoords, float[] normals, DefaultShader shader) {
        this(texture, new GLArrayBuffer(vertices, 3), new GLElementBuffer(order), new GLArrayBuffer(textureCoords, 2), new GLArrayBuffer(normals, 3), shader);
    }

    public TexturedModel(Texture texture, GLArrayBuffer vertices, GLElementBuffer indices, GLArrayBuffer texCoords, GLArrayBuffer normals, DefaultShader shader) {
        this.texture = texture;
        this.shader = shader;
        this.v_count = indices.dataLength();
        this.vertices = vertices;
        this.textureCoords = texCoords;
        this.indices = indices;
        this.normals = normals;
    }

    @Override
    public void bind(Camera camera) {


        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        vertices.activeVertexAttribPointer(0, 0, 0);
        normals.activeVertexAttribPointer(1, 0, 0);
        textureCoords.activeVertexAttribPointer(2, 0, 0);

        shader.bind();
        shader.loadProjection(camera.createProjectionMatrix());
        shader.loadView(camera.createViewMatrix());
        shader.loadSpeculars(texture.getDamper(), texture.getReflectivity());

        texture.bind();

        indices.bind();

    }


    public void render(Camera camera, Entity entity) {
        shader.loadTransformation(entity.createTransformationMatrix());
        render(camera);
    }


    @Override
    public void unbind(Camera camera) {
        indices.unbind();
        shader.unbind();
        texture.unbind();

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    @Override
    public Shader getShader() {
        return shader;
    }

    @Override
    public void render(Camera camera) {
        glDrawElements(GL_TRIANGLES, v_count, GL_UNSIGNED_INT, 0);
    }
}
