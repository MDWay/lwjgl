package de.romjaki.lwjgl.renderer;

import de.romjaki.lwjgl.renderer.geometric.Camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RGR on 18.07.2017.
 */
public class GUIRenderer implements Renderer {

    List<Renderable> objs = new ArrayList<>();

    public GUIRenderer(Renderable... objs) {
        this.objs.addAll(Arrays.asList(objs));
    }

    @Override
    public void render(Camera camera) {
        objs.forEach(obj -> {
            obj.bind();
            obj.render(camera);
            obj.unbind();
        });
    }
}
