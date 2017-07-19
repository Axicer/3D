package fr.axicer.main.game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import fr.axicer.main.game.block.Block;
import fr.axicer.main.game.block.BlockType;
import fr.axicer.main.game.generation.OakTree;
import fr.axicer.main.util.render.Camera;
import fr.axicer.main.util.render.Shader;
import fr.axicer.main.util.render.Texture;
import fr.axicer.main.util.render.TextureManager;

public class Chunk{
	
	public static final int SIZE = 16;
	public static final int MAX_BUILD_HEIGHT = 256;
	
	private FloatBuffer buffer_v;
	private FloatBuffer buffer_t;
	private int vbo_v;
	private int vbo_t;
	private int bufferSize;
	
	public int x,z;
	public Block[][][] blocks;
	public World world;
	
	public Chunk(int x, int z,World world) {
		this.x = x;
		this.z = z;
		this.world = world;
		blocks = new Block[SIZE][MAX_BUILD_HEIGHT][SIZE];
	}
	
	public void addVegetation(){
		for(int x = 0 ; x < SIZE ; x++){
			for(int y = 0 ; y < MAX_BUILD_HEIGHT ; y++){
				for(int z = 0 ; z < SIZE ; z++){
					int xx = this.x * SIZE + x;
					int yy = y;
					int zz = this.z * SIZE + z;
					
					boolean grounded = world.noise.getNoise(xx, zz) > yy-1 && world.noise.getNoise(xx, zz) < yy;
					
					if(Math.random() < 0.01f && grounded){
						OakTree.addTree(world, xx, yy, zz);
					}
				}
			}
		}
	}
	
	public void createChunk(){
		buffer_v = BufferUtils.createFloatBuffer(SIZE*MAX_BUILD_HEIGHT*SIZE*6*4*3);
		buffer_t = BufferUtils.createFloatBuffer(SIZE*MAX_BUILD_HEIGHT*SIZE*6*4*2);
		vbo_v = glGenBuffers();
		vbo_t = glGenBuffers();
		updateChunk();
	}
	public void updateChunk(){
		buffer_v.clear();
		buffer_t.clear();
		bufferSize = 0;
		for(int x = 0 ; x < SIZE ; x++){
			for(int y = 0 ; y < MAX_BUILD_HEIGHT ; y++){
				for(int z = 0 ; z < SIZE ; z++){
					int xx = this.x * SIZE + x;
					int yy = y;
					int zz = this.z * SIZE + z;
					
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
						buffer_v.put(block.faceUpData(xx, yy, zz));
						buffer_t.put(block.faceUpTexData());
						size++;
					}
					if(!down){
						buffer_v.put(block.faceDownData(xx, yy, zz));
						buffer_t.put(block.faceDownTexData());
						size++;
					}
					if(!left){
						buffer_v.put(block.faceLeftData(xx, yy, zz));
						buffer_t.put(block.faceLeftTexData());
						size++;
					}
					if(!right){
						buffer_v.put(block.faceRightData(xx, yy, zz));
						buffer_t.put(block.faceRightTexData());
						size++;
					}
					if(!front){
						buffer_v.put(block.faceFrontData(xx, yy, zz));
						buffer_t.put(block.faceFrontTexData());
						size++;
					}
					if(!back){
						buffer_v.put(block.faceBackData(xx, yy, zz));
						buffer_t.put(block.faceBackTexData());
						size++;
					}
					
					bufferSize += size * 4;
				}
			}
		}
		buffer_v.flip();
		buffer_t.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
		glBufferData(GL_ARRAY_BUFFER, buffer_v, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
		glBufferData(GL_ARRAY_BUFFER, buffer_t, GL_DYNAMIC_DRAW);
	}
	
	public Block getHighestBlock(int x, int z){
		int h = 0;
		while(hasBlockAbove(x, h, z) == true && h < MAX_BUILD_HEIGHT){
			h++;
		}
		return getBlock(x, h, z);
	}
	public boolean hasBlockAbove(int x, int y, int z){
		if(y+1 >= MAX_BUILD_HEIGHT || this.blocks[x][y+1][z] == null)return false;
		return this.blocks[x][y+1][z].type != BlockType.AIR;
	}
	public boolean hasBlockUnder(int x, int y, int z){
		if(y-1 < 0 || this.blocks[x][y-1][z] == null)return false;
		return this.blocks[x][y-1][z].type != BlockType.AIR;
	}
	public Block getBlock(int x, int y, int z){
		if(x < 0 || x >= SIZE || y < 0 || y >= MAX_BUILD_HEIGHT || z < 0 || z >= SIZE) return null;
		return blocks[x][y][z];
	}
	public void addBlock(int x, int y, int z, Block b){
		if(x < 0 || x >= SIZE || y < 0 || y >= MAX_BUILD_HEIGHT || z < 0 || z >= SIZE) return;
		blocks[x][y][z] = b;
		
		if(buffer_v != null){
			updateChunk();
			int xx = this.x;
			int zz = this.z;
			if(x == 0){
				if(world.getChunk(xx-1, zz) != null){
					world.getChunk(xx-1, zz).updateChunk();
				}
			}
			if(x == SIZE-1){
				if(world.getChunk(xx+1, zz) != null){
					world.getChunk(xx+1, zz).updateChunk();
				}
			}
			if(z == 0){
				if(world.getChunk(xx, zz-1) != null){
					world.getChunk(xx, zz-1).updateChunk();
				}
			}
			if(z == SIZE-1){
				if(world.getChunk(xx, zz+1) != null){
					world.getChunk(xx, zz+1).updateChunk();
				}
			}
		}
	}
	public void removeBlock(int x, int y, int z){
		if(x < 0 || x >= SIZE || y < 0 || y >= MAX_BUILD_HEIGHT || z < 0 || z >= SIZE) return;
		blocks[x][y][z] = null;
		
		if(buffer_v != null){
			updateChunk();
			int xx = this.x;
			int zz = this.z;
			if(x == 0){
				if(world.getChunk(xx-1, zz) != null){
					world.getChunk(xx-1, zz).updateChunk();
				}
			}
			if(x == SIZE-1){
				if(world.getChunk(xx+1, zz) != null){
					world.getChunk(xx+1, zz).updateChunk();
				}
			}
			if(z == 0){
				if(world.getChunk(xx, zz-1) != null){
					world.getChunk(xx, zz-1).updateChunk();
				}
			}
			if(z == SIZE-1){
				if(world.getChunk(xx, zz+1) != null){
					world.getChunk(xx, zz+1).updateChunk();
				}
			}
		}
	}
	
	public void update(){
		
	}
	
	public void render(){
		Shader.CHUNK.bind();
		Shader.CHUNK.setUniformi("sampler", 0);
		TextureManager.envTexture.bind(0);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glDrawArrays(GL_QUADS, 0, bufferSize);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		Shader.unbind();
		Texture.unbind();
		if(Camera.debug){
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glLineWidth(1);
			glDisable(GL_CULL_FACE);
			glBegin(GL_QUADS);
			glVertex3f(x*SIZE, 			0, 			z*SIZE);
			glVertex3f(x*SIZE+SIZE, 	0, 			z*SIZE);
			glVertex3f(x*SIZE+SIZE, 	MAX_BUILD_HEIGHT, 	z*SIZE);
			glVertex3f(x*SIZE, 			MAX_BUILD_HEIGHT, 	z*SIZE);
				
			glVertex3f(x*SIZE, 			0, 			z*SIZE+SIZE);
			glVertex3f(x*SIZE+SIZE, 	0, 			z*SIZE+SIZE);
			glVertex3f(x*SIZE+SIZE, 	MAX_BUILD_HEIGHT, 	z*SIZE+SIZE);
			glVertex3f(x*SIZE, 			MAX_BUILD_HEIGHT, 	z*SIZE+SIZE);
				
			glVertex3f(x*SIZE, 			0, 			z*SIZE);
			glVertex3f(x*SIZE+SIZE, 	0, 			z*SIZE);
			glVertex3f(x*SIZE+SIZE, 	0,			z*SIZE+SIZE);
			glVertex3f(x*SIZE, 			0, 			z*SIZE+SIZE);
				
			glVertex3f(x*SIZE, 			MAX_BUILD_HEIGHT, 	z*SIZE);
			glVertex3f(x*SIZE+SIZE, 	MAX_BUILD_HEIGHT, 	z*SIZE);
			glVertex3f(x*SIZE+SIZE, 	MAX_BUILD_HEIGHT,	z*SIZE+SIZE);
			glVertex3f(x*SIZE, 			MAX_BUILD_HEIGHT, 	z*SIZE+SIZE);
			glEnd();
			glEnable(GL_CULL_FACE);
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}
		
	}
}