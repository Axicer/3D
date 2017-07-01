package fr.axicer.main.util.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture {
	
	public int ID;
	
	public Texture(int id) {
		this.ID = id;
	}
	
	public void bind(int sampler){
		glActiveTexture(GL_TEXTURE0+sampler);
		glBindTexture(GL_TEXTURE_2D, ID);
	}
	
	public static void unbind(){
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
