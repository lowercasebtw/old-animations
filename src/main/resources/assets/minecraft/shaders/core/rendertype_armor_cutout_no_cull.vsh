#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

// Code used from/inspired by:
// Licensed CC0-1.0
// https://modrinth.com/user/rizecookey
// https://modrinth.com/mod/cookeymod
// https://github.com/rizecookey/CookeyMod/tree/1.20.6/src/main

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
in vec3 Position;

out float vertexDistance;
uniform int FogShape;

out vec4 vertexColor;
uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;
in vec3 Normal;
in vec4 Color;
uniform sampler2D Sampler2;
in ivec2 UV2;

out vec4 overlayColor;
uniform sampler2D Sampler1;
in ivec2 UV1;

out vec2 texCoord0;
in vec2 UV0;

out vec2 texCoord1;
out vec4 normal;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexDistance = fog_distance(Position, FogShape);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color) * texelFetch(Sampler2, UV2 / 16, 0);
    overlayColor = texelFetch(Sampler1, UV1, 0);
    texCoord0 = UV0;
    texCoord1 = UV1;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
