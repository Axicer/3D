package fr.axicer.main.util.datas;

import fr.axicer.main.game.World;

public class Location extends Vector3D{
	
	private World w;
	
	public Location(World w, double x, double y, double z) {
		super(x, y, z);
		this.w = w;
	}
	public Location() {
		this(null, 0, 0, 0);
	}
	
	public void setWorld(World w){
		this.w = w;
	}
	public World getWorld(){
		return this.w;
	}
	
	public Vector3D getPosition(){
		return copy();
	}
}
