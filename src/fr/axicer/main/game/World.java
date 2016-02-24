package fr.axicer.main.game;

import java.util.Random;

import fr.axicer.main.game.block.Block;
import fr.axicer.main.game.entities.Entity;

public class World{
	
	public static final int WIDTH = 5;
	public static final int HEIGHT = 2;
	public static final int LENGTH = 5;
	public static final int GRAVITY = 1;
	
	public EntityManager entityManager;
	public Chunk[][][] chunks;
	public Noise noise;
	
	public World() {
		entityManager = new EntityManager();
		chunks = new Chunk[WIDTH][HEIGHT][LENGTH];
		noise = new Noise(new Random().nextLong(), 20, 15);
		
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					chunks[x][y][z] = new Chunk(x, y, z, this);
				}
			}
		}
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					chunks[x][y][z].addVegetation();
				}
			}
		}
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					chunks[x][y][z].createChunk();
				}
			}
		}
	}
	
	
	public void update(){
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					chunks[x][y][z].update();
				}
			}
		}
	}
	
	public void render(){
		for(int x = 0 ; x < WIDTH ; x++){
			for(int y = 0 ; y < HEIGHT ; y++){
				for(int z = 0 ; z < LENGTH ; z++){
					chunks[x][y][z].render();
				}
			}
		}
	}
	
	public void renderGUI(){}
	
	public Block getBlock(int x, int y, int z){
		int xc = x/Chunk.WIDTH;
		int yc = y/Chunk.HEIGHT;
		int zc = z/Chunk.LENGTH;
		
		if(xc < 0 || xc >= WIDTH || yc < 0 || yc >= HEIGHT || zc < 0 || zc >= LENGTH) return null;
		
		Chunk chunk = chunks[xc][yc][zc];
		
		int xb = x%Chunk.WIDTH;
		int yb = y%Chunk.HEIGHT;
		int zb = z%Chunk.LENGTH;
		
		return chunk.getBlock(xb, yb, zb);
	}
	public void addBlock(int x, int y, int z, Block b){
		int xc = x/Chunk.WIDTH;
		int yc = y/Chunk.HEIGHT;
		int zc = z/Chunk.LENGTH;
		
		if(xc < 0 || xc >= WIDTH || yc < 0 || yc >= HEIGHT || zc < 0 || zc >= LENGTH) return;
		
		Chunk chunk = chunks[xc][yc][zc];
		
		int xb = x%Chunk.WIDTH;
		int yb = y%Chunk.HEIGHT;
		int zb = z%Chunk.LENGTH;
		
		chunk.addBlock(xb, yb, zb, b);
	}
	public void removeBlock(int x, int y, int z){
		int xc = x/Chunk.WIDTH;
		int yc = y/Chunk.HEIGHT;
		int zc = z/Chunk.LENGTH;
		
		if(xc < 0 || xc >= WIDTH || yc < 0 || yc >= HEIGHT || zc < 0 || zc >= LENGTH) return;
		
		Chunk chunk = chunks[xc][yc][zc];
		
		int xb = x%Chunk.WIDTH;
		int yb = y%Chunk.HEIGHT;
		int zb = z%Chunk.LENGTH;
		
		chunk.removeBlock(xb, yb, zb);
	}
	public Chunk getChunk(int x, int y, int z){
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT || z < 0 || z >= LENGTH) return null;
		return chunks[x][y][z];
	}
	public void addEntity(Entity e){
		entityManager.add(e, this);
	}
}