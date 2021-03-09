#version 330 core

// the output color
out vec4 color;

// value of the color
uniform vec3 pickingColor;

void main() {

    // set the color
    color = vec4(pickingColor, 1.0);
}