#version 330 core

// input vertex data
layout(location = 0) in vec3 position;

// the uniforms
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {

    // output position of vertex
    gl_Position = projection * view * model * vec4(position, 1.0);
}