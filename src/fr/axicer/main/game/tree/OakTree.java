package fr.axicer.main.game.tree;

import java.util.Random;

import fr.axicer.main.game.World;
import fr.axicer.main.game.block.LogBlock;
import fr.axicer.main.game.block.OakLeavesBlock;

public class OakTree {
	
	public static int LOG_HEIGHT = 8;
	//public static int LEAVES_DIAMETER = 9;
	
	public static void addTree(World world, int x, int y, int z){
		
		int leavediameter = new Random().nextInt(5)+5;
		
		for(int i = 0 ; i < LOG_HEIGHT ; i++){
			world.addBlock(x, y + i, z, new LogBlock());
		}
		
		for(int i = 0 ; i < leavediameter ; i++){
			for(int j = 0 ; j < leavediameter ; j++){
				for(int k = 0 ; k < leavediameter ; k++){
					
					double ii = i-(double)leavediameter/2;
					double jj = j-(double)leavediameter/2;
					double kk = k-(double)leavediameter/2;
					
					double l = Math.sqrt(ii*ii + jj*jj + kk*kk);
					
					if(l < (double)leavediameter/2){
						world.addBlock(x+(int)ii,y+(int)jj+LOG_HEIGHT,z+(int)kk, new OakLeavesBlock());
					}
				}
			}
		}
	}
}
