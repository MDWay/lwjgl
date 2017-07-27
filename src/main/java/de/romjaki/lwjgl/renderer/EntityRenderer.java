package de.romjaki.lwjgl.renderer;

import de.romjaki.lwjgl.renderer.geometric.Camera;
import de.romjaki.lwjgl.renderer.geometric.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by RGR on 18.07.2017.
 */
public class EntityRenderer implements Renderer {
    private final List<RenderableEntity> objs = new ArrayList<>();

    public EntityRenderer(RenderableEntity... objs) {
        this.objs.addAll(Arrays.asList(objs));
    }

    @Override
    public void render(Camera camera) {
        glEnable(GL_DEPTH_TEST);
        objs.forEach(obj -> {
            obj.bind(camera);
            obj.render(camera);
            obj.unbind(camera);
        });
        glDisable(GL_DEPTH_TEST);
    }

    public void input() {
        objs.forEach(Entity::input);
    }
}
