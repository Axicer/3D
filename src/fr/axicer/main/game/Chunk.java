package fr.axicer.main.game;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import fr.axicer.main.Main;
import fr.axicer.main.game.block.Block;
import fr.axicer.main.game.block.GrassBlock;
import fr.axicer.main.game.tree.OakTree;
import fr.axicer.main.render.Shader;

public class Chunk{
	
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public static final int LENGTH = 16;
	
	private FloatBuffer buffer;
	private int vbo;
	private int bufferSize;	
	
	public int x,y,z;
	public Block[][][] blocks;
	public World world;
	public Noise noise;
	
	public Chunk(int x, int y, int z,World world) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.noise = world.noise;
		
		blocks = new Block[WIDTH][HEIGHT][LENGTH];
		
		generate();
	}
	
	private void generate(){
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					int xx = this.x * WIDTH + x;
					int yy = this.y * HEIGHT + y;
					int zz = this.z * LENGTH + z;
					
					if(noise.getNoise(xx, zz) > yy-1){
						blocks[x][y][z] = new GrassBlock();
					}
				}
			}
		}
	}
	
	public void addVegetation(){
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					int xx = this.x * WIDTH + x;
					int yy = this.y * HEIGHT + y;
					int zz = this.z * LENGTH + z;
					
					boolean grounded = noise.getNoise(xx, zz) > yy-1 && noise.getNoise(xx, zz) < yy;
					
					if(Math.random() < 0.01f && grounded){
						OakTree.addTree(world, xx, yy, zz);
					}
				}
			}
		}
	}
	
	public void createChunk(){
		buffer = BufferUtils.createFloatBuffer(WIDTH*HEIGHT*LENGTH*6*4*7);
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					int xx = this.x * WIDTH + x;
					int yy = this.y * HEIGHT + y;
					int zz = this.z * LENGTH + z;
					
					boolean up = world.getBlock(xx, yy+1, zz) != null;
					boolean down = world.getBlock(xx, yy-1, zz) != null;
					boolean left = world.getBlock(xx-1, yy, zz) != null;
					boolean right = world.getBlock(xx+1, yy, zz) != null;
					boolean front = world.getBlock(xx, yy, zz-1) != null;
					boolean back = world.getBlock(xx, yy, zz+1) != null;
					
					if(up && down && left && right && front && back)continue;
					if(blocks[x][y][z] == null)continue;
					
					Block block = blocks[x][y][z];
					
					int size = 0;
					if(!up){
						buffer.put(block.faceUpData(xx, yy, zz));
						size++;
					}
					if(!down){
						buffer.put(block.faceDownData(xx, yy, zz));
						size++;
					}
					if(!left){
						buffer.put(block.faceLeftData(xx, yy, zz));
						size++;
					}
					if(!right){
						buffer.put(block.faceRightData(xx, yy, zz));
						size++;
					}
					if(!front){
						buffer.put(block.faceFrontData(xx, yy, zz));
						size++;
					}
					if(!back){
						buffer.put(block.faceBackData(xx, yy, zz));
						size++;
					}

					bufferSize += size*4;
				}
			}
		}
		buffer.flip();
		createBuffer();
	}
	
	private void createBuffer(){
		vbo = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}
	
	public void updateChunk(){
		buffer.clear();
		bufferSize = 0;
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					int xx = this.x * WIDTH + x;
					int yy = this.y * HEIGHT + y;
					int zz = this.z * LENGTH + z;
					
					boolean up = world.getBlock(xx, yy+1, zz) != null;
					boolean down = world.getBlock(xx, yy-1, zz) != null;
					boolean left = world.getBlock(xx-1, yy, zz) != null;
					boolean right = world.getBlock(xx+1, yy, zz) != null;
					boolean front = world.getBlock(xx, yy, zz-1) != null;
					boolean back = world.getBlock(xx, yy, zz+1) != null;
					
					if(up && down && left && right && front && back)continue;
					if(blocks[x][y][z] == null)continue;
					
					Block block = blocks[x][y][z];
					
					int size = 0;
					if(!up){
						buffer.put(block.faceUpData(xx, yy, zz));
						size++;
					}
					if(!down){
						buffer.put(block.faceDownData(xx, yy, zz));
						size++;
					}
					if(!left){
						buffer.put(block.faceLeftData(xx, yy, zz));
						size++;
					}
					if(!right){
						buffer.put(block.faceRightData(xx, yy, zz));
						size++;
					}
					if(!front){
						buffer.put(block.faceFrontData(xx, yy, zz));
						size++;
					}
					if(!back){
						buffer.put(block.faceBackData(xx, yy, zz));
						size++;
					}
					
					bufferSize += size*4;
				}
			}
		}
		buffer.flip();
		updateBuffer();
	}
	
	private void updateBuffer(){
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}
	
	public void update(){
		
	}
	
	public void render(){
		Shader.CHUNK.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 7*4, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 7*4, 12);
		
		glDrawArrays(GL_QUADS, 0, bufferSize);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		Shader.unbind();
		if(Main.debug){
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glLineWidth(1);
			glDisable(GL_CULL_FACE);
			glBegin(GL_QUADS);
			glVertex3f(x*WIDTH, 		y*HEIGHT, 			z*LENGTH);
			glVertex3f(x*WIDTH+WIDTH, 	y*HEIGHT, 			z*LENGTH);
			glVertex3f(x*WIDTH+WIDTH, 	y*HEIGHT+HEIGHT, 	z*LENGTH);
			glVertex3f(x*WIDTH, 		y*HEIGHT+HEIGHT, 	z*LENGTH);
				
			glVertex3f(x*WIDTH, 		y*HEIGHT, 			z*LENGTH+LENGTH);
			glVertex3f(x*WIDTH+WIDTH, 	y*HEIGHT, 			z*LENGTH+LENGTH);
			glVertex3f(x*WIDTH+WIDTH, 	y*HEIGHT+HEIGHT, 	z*LENGTH+LENGTH);
			glVertex3f(x*WIDTH, 		y*HEIGHT+HEIGHT, 	z*LENGTH+LENGTH);
				
			glVertex3f(x*WIDTH, 		y*HEIGHT, 			z*LENGTH);
			glVertex3f(x*WIDTH+WIDTH, 	y*HEIGHT, 			z*LENGTH);
			glVertex3f(x*WIDTH+WIDTH, 	y*HEIGHT,			z*LENGTH+LENGTH);
			glVertex3f(x*WIDTH, 		y*HEIGHT, 			z*LENGTH+LENGTH);
				
			glVertex3f(x*WIDTH, 		y*HEIGHT+HEIGHT, 	z*LENGTH);
			glVertex3f(x*WIDTH+WIDTH, 	y*HEIGHT+HEIGHT, 	z*LENGTH);
			glVertex3f(x*WIDTH+WIDTH, 	y*HEIGHT+HEIGHT,	z*LENGTH+LENGTH);
			glVertex3f(x*WIDTH, 		y*HEIGHT+HEIGHT, 	z*LENGTH+LENGTH);
			glEnd();
			glEnable(GL_CULL_FACE);
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}
		
	}
	
	public Block getBlock(int x, int y, int z){
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT || z < 0 || z >= LENGTH) return null;
		
		return blocks[x][y][z];
	}
	
	public void addBlock(int x, int y, int z, Block b){
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT || z < 0 || z >= LENGTH) return;
		blocks[x][y][z] = b;
		
		if(buffer != null){
			updateChunk();
			int xx = this.x;
			int yy = this.y;
			int zz = this.z;
			if(x == 0){
				if(world.getChunk(xx-1, yy, zz) != null){
					world.getChunk(xx-1, yy, zz).updateChunk();
				}
			}
			if(x == WIDTH-1){
				if(world.getChunk(xx+1, yy, zz) != null){
					world.getChunk(xx+1, yy, zz).updateChunk();
				}
			}
			if(y == 0){
				if(world.getChunk(xx, yy-1, zz) != null){
					world.getChunk(xx, yy-1, zz).updateChunk();
				}
			}
			if(y == HEIGHT-1){
				if(world.getChunk(xx, yy+1, zz) != null){
					world.getChunk(xx, yy+1, zz).updateChunk();
				}
			}
			if(z == 0){
				if(world.getChunk(xx, yy, zz-1) != null){
					world.getChunk(xx, yy, zz-1).updateChunk();
				}
			}
			if(z == LENGTH-1){
				if(world.getChunk(xx, yy, zz+1) != null){
					world.getChunk(xx, yy, zz+1).updateChunk();
				}
			}
		}
	}
	
	public void removeBlock(int x, int y, int z){
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT || z < 0 || z >= LENGTH) return;
		blocks[x][y][z] = null;
		
		if(buffer != null){
			updateChunk();
			int xx = this.x;
			int yy = this.y;
			int zz = this.z;
			if(x == 0){
				if(world.getChunk(xx-1, yy, zz) != null){
					world.getChunk(xx-1, yy, zz).updateChunk();
				}
			}
			if(x == WIDTH-1){
				if(world.getChunk(xx+1, yy, zz) != null){
					world.getChunk(xx+1, yy, zz).updateChunk();
				}
			}
			if(y == 0){
				if(world.getChunk(xx, yy-1, zz) != null){
					world.getChunk(xx, yy-1, zz).updateChunk();
				}
			}
			if(y == HEIGHT-1){
				if(world.getChunk(xx, yy+1, zz) != null){
					world.getChunk(xx, yy+1, zz).updateChunk();
				}
			}
			if(z == 0){
				if(world.getChunk(xx, yy, zz-1) != null){
					world.getChunk(xx, yy, zz-1).updateChunk();
				}
			}
			if(z == LENGTH-1){
				if(world.getChunk(xx, yy, zz+1) != null){
					world.getChunk(xx, yy, zz+1).updateChunk();
				}
			}
		}
	}
}