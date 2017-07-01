package fr.axicer.main.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.axicer.main.game.block.Block;
import fr.axicer.main.game.entities.EntityManager;
import fr.axicer.main.game.generation.ChunkGenerator;
import fr.axicer.main.util.datas.Location;

public class World {
	
	public static float GRAVITY = 1;

	private List<Chunk> chunks;
	public Location spawnloc;
	public EntityManager entityManager;
	public Noise noise;
	
	public World() {
		chunks = new ArrayList<>();
		noise = new Noise(new Random().nextLong(), 20, 15);
		generateChunk(0, 0);
		generateChunk(1, 0);
		generateChunk(1, 1);
		generateChunk(0, 1);
		Block highest = getChunk(0, 0).getHighestBlock(0, 0);
		spawnloc = new Location(this, highest.x, highest.y, highest.z);
		entityManager = new EntityManager(this);
	}
	public Chunk generateChunk(int x, int z){
		Chunk c = new Chunk(x, z, this);
		ChunkGenerator.generate(c);
		c.createChunk();
		chunks.add(c);
		return c;
	}
	
	public void update(){
		entityManager.update();
	}
	public void render(){
		entityManager.render();
		for(Chunk c : chunks){
			c.render();
		}
	}
	
	public Chunk getChunk(int x, int z){
		for(Chunk c : chunks){
			if(c.x == x && c.z == z)return c;
		}
		return null;
	}
	public Block getBlock(int x, int y, int z){
		int cx = x/Chunk.SIZE;
		int cz = z/Chunk.SIZE;
		Chunk c = getChunk(cx, cz);
		if(c == null)return null;
		return c.getBlock(x%Chunk.SIZE, y, z%Chunk.SIZE);
	}
	public void addBlock(Block b, Location loc){
		addBlock(b, (int)loc.x, (int)loc.y, (int)loc.z);
	}
	public void addBlock(Block b, int x, int y, int z){
		int xc = x/Chunk.SIZE;
		int zc = z/Chunk.SIZE;
		
		if(xc < 0 || xc >= Chunk.SIZE || y < 0 || y >= Chunk.MAX_BUILD_HEIGHT || zc < 0 || zc >= Chunk.SIZE) return;
		
		Chunk chunk = getChunk(xc, zc);
		
		int xb = x%Chunk.SIZE;
		int zb = z%Chunk.SIZE;
		
		chunk.addBlock(xb, y, zb, b);
	}
	public void removeBlock(int x, int y, int z){
		int xc = x/Chunk.SIZE;
		int zc = z/Chunk.SIZE;
		
		if(xc < 0 || xc >= Chunk.SIZE || y < 0 || y >= Chunk.MAX_BUILD_HEIGHT || zc < 0 || zc >= Chunk.SIZE) return;
		
		Chunk chunk = getChunk(xc, zc);
		
		int xb = x%Chunk.SIZE;
		int zb = z%Chunk.SIZE;
		
		chunk.removeBlock(xb, y, zb);
	}
}
