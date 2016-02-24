#version 130

in vec4 in_Color;
out vec4 color;

void main(){
	color = in_Color;
	gl_Position = ftransform();
}