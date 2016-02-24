package fr.axicer.main.game;

import java.util.ArrayList;
import java.util.List;

import fr.axicer.main.game.entities.Entity;

public class EntityManager {
	private List<Entity> entities = new ArrayList<>();
	
	public void add(Entity e, World world){
		e.init(world);
		entities.add(e);
	}
	
	public void update(){
		for(Entity e : entities){
			e.update();
		}
	}
	public void render(){
		for(Entity e : entities){
			e.render();
		}
	}
}
