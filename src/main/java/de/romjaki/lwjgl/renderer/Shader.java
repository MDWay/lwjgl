package de.romjaki.lwjgl.renderer;

import de.romjaki.lwjgl.IBindable;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by RGR on 18.07.2017.
 */
public abstract class Shader implements IBindable {

    public static int MAX_LIGHTS = 1;

    private int program;
    private int vertex;
    private int fragment;
    private Map<String, Integer> cachedLocations = new HashMap<>();

    public Shader(String name) {
        program = glCreateProgram();

        vertex = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertex, applyVars(readAll("src/main/resources/shaders/" + name + ".vert")));
        glCompileShader(vertex);
        if (glGetShaderi(vertex, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new IllegalStateException("Failed to compile vertex shader:" + glGetShaderInfoLog(vertex));
        }

        fragment = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragment, applyVars(readAll("src/main/resources/shaders/" + name + ".frag")));
        glCompileShader(fragment);
        if (glGetShaderi(fragment, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new IllegalStateException("Failed to compile fragment shader:" + glGetShaderInfoLog(fragment));
        }

        glAttachShader(program, vertex);
        glAttachShader(program, fragment);

        bindAttribLocations();

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            throw new IllegalStateException(glGetProgramInfoLog(program));
        }
        glValidateProgram(program);
    }

    private String applyVars(String s) {
        return s.replaceAll("\\{MAX_LIGHTS}", MAX_LIGHTS + "");
    }

    protected void loadFloat4(String name, float x, float y, float z, float w) {
        glUniform4f(cachedLocation(name), x, y, z, w);
    }

    protected void loadMatrix4f(String name, Matrix4f value) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        value.get(buf);
        glUniformMatrix4fv(cachedLocation(name), false, buf);
    }

    protected void loadInt3(String name, int... value) {
        glUniform3i(cachedLocation(name), value[0], value[1], value[2]);
    }

    protected void loadFLoat3(String name, float x, float y, float z) {
        glUniform3f(cachedLocation(name), x, y, z);
    }

    protected int cachedLocation(String name) {
        return cachedLocations.computeIfAbsent(name, s -> glGetUniformLocation(getProgram(), name));
    }

    protected void loadFloat(String name, float value) {
        glUniform1f(cachedLocation(name), value);
    }

    protected void loadInt(String name, int value) {
        glUniform1i(cachedLocation(name), value);
    }


    public int getProgram() {
        return program;
    }

    public int getFragment() {
        return fragment;
    }

    public int getVertex() {
        return vertex;
    }

    @Override
    public void bind() {
        glUseProgram(program);
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }

    protected abstract void bindAttribLocations();

    private String readAll(String name) {
        try (Scanner s = new Scanner(new File(name))) {
            s.useDelimiter("\\A");
            return s.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
