package de.romjaki.lwjgl.renderer;

import de.romjaki.lwjgl.renderer.geometric.Camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RGR on 18.07.2017.
 */
public class MasterRenderer implements Renderer {
    private List<Renderer> subRenderes = new ArrayList<>();

    public MasterRenderer(Renderer... renderers) {
        subRenderes.addAll(Arrays.asList(renderers));
    }

    public void render(Camera camera) {
        subRenderes.forEach(r -> r.render(camera));
    }
}
