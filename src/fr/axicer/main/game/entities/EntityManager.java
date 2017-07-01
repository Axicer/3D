package fr.axicer.main.game.entities;

import java.util.ArrayList;
import java.util.List;

import fr.axicer.main.game.World;
import fr.axicer.main.util.datas.Location;

public class EntityManager {
	
	private World world;
	private List<Entity> entities;
	
	public EntityManager(World world) {
		this.world = world;
		this.entities = new ArrayList<>();
	}
	
	public Entity spawnEntity(Entity e){
		this.entities.add(e);
		e.position = world.spawnloc.getPosition();
		return e;
	}
	public Entity spawnEntity(Entity e, Location l){
		this.entities.add(e);
		e.position = l.getPosition();
		return e;
	}
	public void deleteEntity(Entity e){
		if(!this.entities.contains(e))return;
		this.entities.remove(e);
	}
	
	public void update(){
		for(Entity e : entities)e.update();
	}
	public void render(){
		for(Entity e : entities)e.render();
	}
}
