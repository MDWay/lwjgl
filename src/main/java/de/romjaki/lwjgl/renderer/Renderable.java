package de.romjaki.lwjgl.renderer;

import de.romjaki.lwjgl.IBindable;
import de.romjaki.lwjgl.renderer.geometric.Camera;

/**
 * Created by RGR on 18.07.2017.
 */
public interface Renderable extends IBindable {

    Shader getShader();

    default void bind() {
        bind(null);
    }

    void bind(Camera o);

    default void unbind() {
        unbind(null);
    }

    void unbind(Camera o);

    void render(Camera camera);

}
