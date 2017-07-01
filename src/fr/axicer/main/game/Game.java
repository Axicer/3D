package fr.axicer.main.game;

import fr.axicer.main.game.entities.player.Player;
import fr.axicer.main.util.render.Camera;

public class Game {
	
	public World world;
	public Player player;
	public Camera cam;
	
	int vboVertex;
	int vboColor;
	
	public Game() {
		world = new World();
		player = (Player)world.entityManager.spawnEntity(new Player(world));
		cam = new Camera(player);
	}
	
	public void update(){
		cam.update();
		world.update();
	}
	public void render(){
		cam.render();
		world.render();
	}
}
