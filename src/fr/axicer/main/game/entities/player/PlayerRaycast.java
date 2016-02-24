package fr.axicer.main.game.entities.player;

import java.util.ArrayList;
import java.util.List;

import fr.axicer.main.game.World;
import fr.axicer.main.math.Vector3f;

public class PlayerRaycast {
	private Player player;
	private List<Vector3f> points;
	
	public PlayerRaycast(Player player) {
		this.player = player;
		this.points = new ArrayList<>();
		for(int i = 0 ; i < 10*10 ; i++){
			points.add(new Vector3f());
		}
	}
	
	public void update() {
		int i = 0;
		for(Vector3f vec : points){
			Vector3f pos = player.position.copy().addY(1.75f).add(player.getDirection().copy().mul(i/16.0f));
			vec.set(pos);
			i++;
		}
	}
	
	public Vector3f getBlock(World world){
		for(Vector3f v : points){
			float x = v.x;
			float y = v.y;
			float z = v.z;
			boolean block = world.getBlock((int)x, (int)y, (int)z) != null;
			
			if(block) return new Vector3f(x, y, z);
		}
		return null;
	}
}
