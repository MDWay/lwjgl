package de.romjaki.lwjgl.renderer.geometric;

import de.romjaki.lwjgl.renderer.DefaultShader;
import de.romjaki.lwjgl.renderer.RenderableEntity;
import de.romjaki.lwjgl.renderer.Shader;
import de.romjaki.lwjgl.renderer.Texture;

/**
 * Created by RGR on 20.07.2017.
 */
public class TexturedModelEntity extends BasicEntity implements RenderableEntity {


    private TexturedModel model;

    public TexturedModelEntity(TexturedModel model) {
        this.model = model;
    }

    public TexturedModelEntity(Texture texture, float[] vertices, int[] indices, float[] textureCoords, float[] normals, DefaultShader shader) {
        this(new TexturedModel(texture, vertices, indices, textureCoords, normals, shader));
    }


    @Override
    public void input() {
    }

    @Override
    public Shader getShader() {
        return model.getShader();
    }

    @Override
    public void render(Camera camera) {
        model.render(camera, this);
    }

    @Override
    public void bind(Camera o) {
        model.bind(o);
    }

    @Override
    public void unbind(Camera o) {
        model.unbind(o);
    }
}
