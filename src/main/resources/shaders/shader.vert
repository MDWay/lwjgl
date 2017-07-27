#version 400 core

const int MAX_LIGHTS = {MAX_LIGHTS};

attribute vec3 vertex;
attribute vec2 texture;
attribute vec3 pass_normal;

uniform mat4 proj;
uniform mat4 trans;
uniform mat4 view;
uniform vec3 lights[MAX_LIGHTS];

out vec3 toCamera;
out vec3 toLight[MAX_LIGHTS];
out vec3 normal;
out vec2 textureCoords;

void main() {
    normal = pass_normal;
    textureCoords = texture;
    vec4 worldPosition = trans * vec4(vertex,1);
    for(int i = 0;i<MAX_LIGHTS;i++){
        toLight[i] = lights[i] - worldPosition.xyz;
    }
    toCamera = (inverse(view) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
    gl_Position = proj * view * worldPosition;
}