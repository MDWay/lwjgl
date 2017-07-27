package de.romjaki.lwjgl;

/**
 * Created by RGR on 20.07.2017.
 */
public interface IBindable {

    void bind();

    void unbind();

    default void cleanUp() {
    }
}
