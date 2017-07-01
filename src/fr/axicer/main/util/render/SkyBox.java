package fr.axicer.main.util.render;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import fr.axicer.main.util.datas.Vector3D;

public class SkyBox {
	private int VBO;
	private FloatBuffer buffer;
	private int textureID;
	
	public SkyBox(String[] textures) {
		VBO = glGenBuffers();
		buffer = BufferUtils.createFloatBuffer(6*4*3);
		buffer.put(blockData()).flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		createCubeMap(textures);
	}
	
	private void createCubeMap(String[] textures){
		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);
		
		for(int i = 0 ; i < textures.length ; i++){
			TextureData data = decode(textures[i]);
			
			glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X+i, 0, GL_RGBA, data.width, data.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data.buffer);
		}
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
	}
	
	private TextureData decode(String path){
		int[] pixels = null;
		int width = 0, height = 0;
		try{
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		int[] data = new int[pixels.length];
		for(int i = 0 ; i < pixels.length ; i++){
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return new TextureData(glGenTextures(), width, height, buffer);
	}
	
	public void render(Vector3D pos){
		Shader.SKYBOX.bind();
		glPushMatrix();
		glTranslatef((float)pos.x, (float)pos.y+1.75f, (float)pos.z);
		
		glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);
		
		glEnableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3*4, 0);
		
		glDrawArrays(GL_QUADS, 0, 4*6);
		
		glDisableVertexAttribArray(0);
		
		glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
		
		glPopMatrix();
		Shader.unbind();
	}
	
	private float[] blockData(){
		int size = 10000;
		return new float[]{
			-size , -size , -size,
			+size , -size , -size,
			+size , +size , -size,
			-size , +size , -size,
			
			-size , -size , +size,
			-size , +size , +size,
			+size , +size , +size,
			+size , -size , +size,
			
			-size , -size , -size,
			-size , -size , +size,
			+size , -size , +size,
			+size , -size , -size,
			
			-size , +size , -size,
			+size , +size , -size,
			+size , +size , +size,
			-size , +size , +size,
			
			-size , -size , -size,
			-size , +size , -size,
			-size , +size , +size,
			-size , -size , +size,
			
			+size , -size , -size,
			+size , -size , +size,
			+size , +size , +size,
			+size , +size , -size
		};
	}
	
	class TextureData{
		public int id;
		public int width, height;
		public IntBuffer buffer;
		
		public TextureData(int id, int w, int h, IntBuffer buffer) {
			this.id = id;
			this.width = w;
			this.height = h;
			this.buffer = buffer;
		}
	}
}
