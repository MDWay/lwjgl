#version 400 core

const int MAX_LIGHTS = {MAX_LIGHTS};
const bool USE_SPECULAR = true;

out vec4 out_Color;

uniform sampler2D sampler;
uniform float damper;
uniform float reflectivity;
uniform vec3 lights[MAX_LIGHTS];
uniform vec3 colors[MAX_LIGHTS];

in vec3 toCamera;
in vec3 toLight[MAX_LIGHTS];
in vec2 textureCoords;
in vec3 normal;

void main(){
    vec3 diffuse = vec3(0,0,0);
    for(int i = 0 ; i < MAX_LIGHTS ; i++){
        diffuse += dot(normalize(toLight[i]), normalize(normal)) * colors[i];
    }
    vec3 toCameraNormal = normalize(toCamera);
    vec3 specular = vec3(0,0,0);
    if (USE_SPECULAR){
        for(int i = 0; i < MAX_LIGHTS;i++) {
            vec3 fromLight = -normalize(toLight[i]);
            vec3 reflection = reflect(fromLight, normal);
            float specularFactor = dot(reflection, toCameraNormal);
            specularFactor = max(0, specularFactor);
            specularFactor = pow(specularFactor, damper) * reflectivity;
            specular += specularFactor * colors[i];
        }
    }
    diffuse = diffuse / MAX_LIGHTS;
    out_Color = texture2D(sampler, textureCoords) * vec4(diffuse, 1) + vec4(specular / MAX_LIGHTS, 1.0);
}