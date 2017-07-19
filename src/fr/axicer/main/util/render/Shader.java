package fr.axicer.main.util.render;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import fr.axicer.main.util.datas.Vector3D;

public class Shader {
	
	public static Shader CHUNK = new Shader("chunk");
	public static Shader SKYBOX = new Shader("skybox");
	
	public int program,vs,fs;
	
	public static void CreateShaders() {
		CHUNK = new Shader("chunk");
		SKYBOX = new Shader("skybox");
		
		glBindAttribLocation(Shader.CHUNK.program, 0, "vert");
		glBindAttribLocation(Shader.CHUNK.program, 1, "vertTexCoord");
	}
	
	public Shader(String filename) {
		program = glCreateProgram();
		
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(filename+".vs"));
		glCompileShader(vs);
		if(glGetShaderi(vs, GL_COMPILE_STATUS) != 1){
			System.err.println(glGetShaderInfoLog(vs, 2048));
			System.exit(1);
		}
		
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(filename+".fs"));
		glCompileShader(fs);
		if(glGetShaderi(fs, GL_COMPILE_STATUS) != 1){
			System.err.println(glGetShaderInfoLog(fs, 2048));
			System.exit(1);
		}
		
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) != 1){
			System.err.println(glGetProgramInfoLog(program, 2048));
			System.exit(1);
		}
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS) != 1){
			System.err.println(glGetProgramInfoLog(program, 2048));
			System.exit(1);
		}
	}
	
	public void setUniformf(String name, float v) {
		glUniform1f(glGetUniformLocation(program, name), v);
	}

	public void setUniformi(String name, int i) {
		glUniform1i(glGetUniformLocation(program, name), i);
	}
	
	public void setUniform(String name, Vector3D v) {
		glUniform3f(glGetUniformLocation(program, name), (float)v.x, (float)v.y, (float)v.z);
	}
	
	public void bind(){
		glUseProgram(program);
	}
	public static void unbind(){
		glUseProgram(0);
	}
	
	private String readFile(String filename){
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(Shader.class.getResource("/shaders/"+filename).toURI())));
			String line;
			while((line = br.readLine()) != null){
				string.append(line);
				string.append("\n");
			}
			br.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return string.toString();
	}
}