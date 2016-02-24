package fr.axicer.main.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import fr.axicer.main.Main;
import fr.axicer.main.game.entities.player.Player;
import fr.axicer.main.game.inputs.Inputs;
import fr.axicer.main.math.Vector3f;

public class Game {
	
	private World world;
	private Player player;
	
	public Game() {
		world = new World();
		player = new Player(new Vector3f(16, 40, 16));
		world.addEntity(player);
	}
	public void update(){
		if(Inputs.getKeyDown(Keyboard.KEY_F3))Main.debug = !Main.debug;
		world.update();
		getPlayer().update();
	}
	public void render(){
		world.render();
		getPlayer().render();
	}
	public void renderGUI(){
		world.renderGUI();
		
		GL11.glRectf(Display.getWidth()/2-2, Display.getHeight()/2-2, Display.getWidth()/2+2, Display.getHeight()/2+2);
	}
	public World getWorld() {
		return world;
	}
	public Player getPlayer() {
		return player;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
