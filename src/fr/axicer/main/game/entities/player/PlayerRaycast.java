package fr.axicer.main.game.entities.player;

import java.util.ArrayList;
import java.util.List;

import fr.axicer.main.game.World;
import fr.axicer.main.util.datas.Vector3D;

public class PlayerRaycast {
	private Player player;
	private List<Vector3D> points;
	
	public PlayerRaycast(Player player) {
		this.player = player;
		this.points = new ArrayList<>();
		for(int i = 0 ; i < 10*10 ; i++){
			points.add(new Vector3D());
		}
	}
	
	public void update() {
		int i = 0;
		for(Vector3D vec : points){
			Vector3D pos = player.position.copy().addY(1.75f).add(player.getDirection().copy().mul(i/16.0f));
			vec.set(pos);
			i++;
		}
	}
	
	public Vector3D getBlock(World world){
		for(Vector3D v : points){
			double x = v.x;
			double y = v.y;
			double z = v.z;
			boolean block = world.getBlock((int)x, (int)y, (int)z) != null;
			
			if(block) return new Vector3D(x, y, z);
		}
		return null;
	}
}
