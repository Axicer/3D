package fr.axicer.main.util.render;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import fr.axicer.main.game.block.Block;
import fr.axicer.main.game.block.BlockType;
import fr.axicer.main.game.entities.player.Player;
import fr.axicer.main.game.entities.player.PlayerRaycast;
import fr.axicer.main.game.inputs.Inputs;
import fr.axicer.main.util.datas.Vector3D;

public class Camera {
	
	public static boolean debug = false;
	public static float fov = 70.0f;
	public Player player;
	private double xa,ya,za;
	private PlayerRaycast raycast;
	private Block selectedBlock;
	private Vector3D selectedPosition;
	
	public Camera(Player p) {
		this.player = p;
		raycast = new PlayerRaycast(player);
	}
	
	public void setTarget(Player p){
		this.player = p;
	}
	
	public void update(){
		float xDir = 0,yDir = 0,zDir = 0f;
		
		player.rotation.x -= Mouse.getDY()*0.3;
		player.rotation.y += Mouse.getDX()*0.3;
		
		if(player.rotation.x > 90)player.rotation.x = 90;
		if(player.rotation.x < -90)player.rotation.x = -90;
		if(player.rotation.y < 0)player.rotation.y = 360;
		if(player.rotation.y > 360)player.rotation.y = 0;
		
		if(Inputs.getKeyDown(Keyboard.KEY_F1)){
			debug = !debug;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			zDir = -player.speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			zDir = player.speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			xDir = -player.speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			xDir = player.speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			if(debug){
				yDir = player.speed;
			}else{
				if(player.grounded){
					yDir = 0.3f;
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			if(debug){
				yDir = -player.speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			if(fov < 75.0f){
				fov += 1;
			}
			if(debug){
				player.speed = 1.5f;
			}else{
				player.speed = 1f;
			}
		}else{
			if(fov > 70.0f){
				fov -= 1;
			}
			if(debug){
				player.speed = 1f;
			}else{
				player.speed = 0.8f;
			}
		}
		
		xa = xDir*Math.cos(Math.toRadians(player.rotation.y)) - zDir * Math.sin(Math.toRadians(player.rotation.y));
		ya = yDir;
		za = zDir*Math.cos(Math.toRadians(player.rotation.y)) + xDir * Math.sin(Math.toRadians(player.rotation.y));
		
		if(debug){
			player.gravity = false;
			player.collision = false;
		}else{
			player.collision = true;
			player.gravity = true;
		}
		
		player.move((float)xa*0.1f, (float)ya*0.1f, (float)za*0.1f);
		
		xa *= 0.9f;
		ya *= 0.9f;
		za *= 0.9f;
		
		raycast.update();
		if(raycast.getBlock(player.world) != null){
			double x =  raycast.getBlock(player.world).x;
			double y =  raycast.getBlock(player.world).y;
			double z =  raycast.getBlock(player.world).z;
			selectedBlock = player.world.getBlock((int)x, (int)y, (int)z);
			selectedPosition = new Vector3D(x, y, z);
		}else{
			selectedBlock = null;
			selectedPosition = null;
		}
		if(selectedBlock != null){
			int x = (int) selectedPosition.x;
			int y = (int) selectedPosition.y;
			int z = (int) selectedPosition.z;
			double rx = selectedPosition.x;
			double ry = selectedPosition.y;
			double rz = selectedPosition.z;
			if(Inputs.getMouseDown(0)){
				player.world.removeBlock(x, y, z);
			}
			if(Inputs.getMouseDown(1)){
				double vx = rx - x;
				double vy = ry - y;
				double vz = rz - z;
				
				Vector3D check = new Vector3D(vx, vy, vz).check();
				
				int xp = (int) check.x;
				int yp = (int) check.y;
				int zp = (int) check.z;
				
				player.world.addBlock(new Block(BlockType.LOG), x+xp, y+yp, z+zp);
			}
		}
	}
	
	public void render(){
		//check for resize
		if(Display.wasResized()) glViewport(0, 0, Display.getWidth(), Display.getHeight());

		//create perspective
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov, (float)Display.getWidth()/(float)Display.getHeight(), 0.1f, 1000000.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glRotatef((float)player.rotation.x, 1f, 0, 0);
		glRotatef((float)player.rotation.y, 0, 1f, 0);
		glTranslatef((float)-player.position.x, (float)-player.position.y-1.75f, (float)-player.position.z);
		
		if(selectedBlock != null){
			renderSelection(selectedPosition);
		}
	}

	private void renderSelection(Vector3D pos){
		int x = (int)pos.x;
		int y = (int)pos.y;
		int z = (int)pos.z;
		float s = 1.001f;
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glLineWidth(1);
		glBegin(GL_QUADS);
			glVertex3f(x+s, y, z);
			glVertex3f(x, y, z);
			glVertex3f(x, y+s, z);
			glVertex3f(x+s, y+s, z);
			
			glVertex3f(x, y, z+s);
			glVertex3f(x+s, y, z+s);
			glVertex3f(x+s, y+s, z+s);
			glVertex3f(x, y+s, z+s);
			
			glVertex3f(x, y, z);
			glVertex3f(x+s, y, z);
			glVertex3f(x+s, y, z+s);
			glVertex3f(x, y, z+s);
			
			glVertex3f(x+s, y+s, z);
			glVertex3f(x, y+s, z);
			glVertex3f(x, y+s, z+s);
			glVertex3f(x+s, y+s, z+s);
			
			glVertex3f(x, y+s, z);
			glVertex3f(x, y, z);
			glVertex3f(x, y, z+s);
			glVertex3f(x, y+s, z+s);
			
			glVertex3f(x+s, y, z);
			glVertex3f(x+s, y+s, z);
			glVertex3f(x+s, y+s, z+s);
			glVertex3f(x+s, y, z+s);
		glEnd();
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
}
