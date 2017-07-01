package fr.axicer.main.game.generation;

import fr.axicer.main.game.Chunk;
import fr.axicer.main.game.block.Block;
import fr.axicer.main.game.block.BlockType;

public class ChunkGenerator {
	
	public static void generate(Chunk c){
		for(int x = 0 ; x < Chunk.SIZE ; x++){
			for(int y = 0 ; y < Chunk.MAX_BUILD_HEIGHT ; y++){
				for(int z = 0 ; z < Chunk.SIZE ; z++){
					int xx = c.x * Chunk.SIZE + x;
					int yy = y;
					int zz = c.z * Chunk.SIZE + z;
					
					if(c.world.noise.getNoise(xx, zz) > yy-1){
						c.blocks[x][y][z] = new Block(BlockType.GRASS);
					}
				}
			}
		}
	}
}
