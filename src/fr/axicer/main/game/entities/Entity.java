package fr.axicer.main.game.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.axicer.main.game.World;
import fr.axicer.main.util.datas.Vector3D;

public abstract class Entity {
	public Vector3D position;
	public Vector2f rotation;
	public World world;
	public boolean gravity = true;
	public float gravityFactor = 0;
	public boolean grounded = false;
	public boolean collision = true;
	
	
	public Entity(World world) {
		this(world, new Vector3D(), new Vector2f());
	}
	public Entity(World world, Vector3D position) {
		this(world, position, new Vector2f());
	}
	public Entity(World world, Vector3D position, Vector2f rotation) {
		this.position = position;
		this.rotation = rotation;
		this.world = world;
	}
	
	public void move(float xa, float ya, float za){
		if(gravity){
			gravityFactor += World.GRAVITY ;
			if(grounded){
				gravityFactor = 0;
			}
			ya -= gravityFactor;
		}
		
		if(collision){
			int xStep = (int) Math.abs(xa*100);
			for(int i = 0 ; i < xStep ; i++){
				if(!isColliding(xa/xStep, 0, 0)){
					position.x += xa / xStep;
				}else{
					xa = 0;
				}
			}
			int yStep = (int) Math.abs(ya*100);
			for(int i = 0 ; i < yStep ; i++){
				if(!isColliding(0, ya/yStep, 0)){
					position.y += ya / yStep;
				}else{
					ya = 0;
				}
			}
			int zStep = (int) Math.abs(za*100);
			for(int i = 0 ; i < zStep ; i++){
				if(!isColliding(0, 0, za/zStep)){
					position.z += za / zStep;
				}else{
					za = 0;
				}
			}
		}else{
			position.x += xa;
			position.y += ya;
			position.z += za;
		}
	}
	
	public boolean isColliding(float xa, float ya, float za){
		float r = 0.3f;
		
		int x0 = (int)(position.x + xa - r);
		int x1 = (int)(position.x + xa + r);
		int y0 = (int)(position.y + ya - r);
		int y1 = (int)(position.y + ya + r);
		int z0 = (int)(position.z + za - r);
		int z1 = (int)(position.z + za + r);
		int xmid = (int) (position.x);
		int yb = (int) (position.y +ya - r - 0.01);
		int zmid = (int) (position.z);
		
		if(world.getBlock(xmid, (int) yb, zmid) != null){
			grounded = true;
		}else{
			grounded = false;
		}
		
		if(world.getBlock(x0, y0, z0) != null) return true;
		if(world.getBlock(x1, y0, z0) != null) return true;
		if(world.getBlock(x1, y1, z0) != null) return true;
		if(world.getBlock(x0, y1, z0) != null) return true;
		
		if(world.getBlock(x0, y0, z1) != null) return true;
		if(world.getBlock(x1, y0, z1) != null) return true;
		if(world.getBlock(x1, y1, z1) != null) return true;
		if(world.getBlock(x0, y1, z1) != null) return true;
		
		return false;
	}
	public Vector3D getDirection(){
		Vector3D r = new Vector3D();
		
		Vector2f rot = new Vector2f(this.rotation);
		
		float cosY = (float) Math.cos(Math.toRadians(rot.y - 90));
		float sinY = (float) Math.sin(Math.toRadians(rot.y - 90));
		float cosP = (float) Math.cos(Math.toRadians(-rot.x));
		float sinP = (float) Math.sin(Math.toRadians(-rot.x));
		
		r.x = cosY*cosP;
		r.y = sinP;
		r.z = sinY*cosP;
		
		r.normalize();
		
		return r;
	}
	public void init(World world){
		this.world = world;
	}
	public abstract void update();
	public abstract void render();
}
