#version 130

out vec3 position;

void main(void) {
	position = gl_Vertex.xyz;

	gl_Position = ftransform();
}