#version 120

attribute vec3 vert;
attribute vec2 vertTexCoord;

varying vec2 fragTexCoord;

void main() {
    fragTexCoord = vertTexCoord;
    
    gl_Position = ftransform();
}