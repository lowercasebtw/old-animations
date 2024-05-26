#version 150

#moj_import <fog.glsl>

// Code used from/inspired by:
// Licensed CC0-1.0
// https://modrinth.com/user/rizecookey
// https://modrinth.com/mod/cookeymod
// https://github.com/rizecookey/CookeyMod/tree/1.20.6/src/main

uniform sampler2D Sampler0;
in vec2 texCoord0;
in vec4 vertexColor;
uniform vec4 ColorModulator;

in vec4 overlayColor;
out vec4 fragColor;
in float vertexDistance;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (color.a < 0.1)
        discard;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
