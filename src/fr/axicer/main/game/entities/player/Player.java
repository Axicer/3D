package fr.axicer.main.game.entities.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;
import fr.axicer.main.Main;
import fr.axicer.main.game.block.Block;
import fr.axicer.main.game.block.LogBlock;
import fr.axicer.main.game.entities.Entity;
import fr.axicer.main.game.inputs.Inputs;
import fr.axicer.main.math.Vector3f;

public class Player extends Entity{

	private PlayerRaycast raycast;
	private Block selectedBlock;
	private Vector3f selectedPosition;
	private double xa,ya,za;
	private float xDir,yDir,zDir;
	public float speed = 0.01f;
	
	public Player(Vector3f position) {
		super(position);
		raycast = new PlayerRaycast(this);
	}

	public void update() {
		raycast.update();
		
		collision = !Main.debug;
		
		xDir = 0;
		yDir = 0;
		zDir = 0;
		
		rotation.x -= Mouse.getDY()*0.5;
		rotation.y += Mouse.getDX()*0.5;
		
		if(rotation.x > 90)rotation.x = 90;
		if(rotation.x < -90)rotation.x = -90;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			zDir = -speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			zDir = speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			xDir = -speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			xDir = speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			if(Main.debug){
				yDir = speed;
			}else{
				if(grounded){
					yDir = 0.3f;
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			if(Main.debug){
				yDir = -speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			if(Main.fov < 75.0f){
				Main.fov += 1;
			}
			if(Main.debug){
				speed = 0.1f;
			}else{
				speed = 0.015f;
			}
		}else{
			if(Main.fov > 70.0f){
				Main.fov -= 1;
			}
			if(Main.debug){
				speed = 0.05f;
			}else{
				speed = 0.01f;
			}
		}
		
		xa += xDir*Math.cos(Math.toRadians(rotation.y)) - zDir * Math.sin(Math.toRadians(rotation.y));
		ya += yDir;
		za += zDir*Math.cos(Math.toRadians(rotation.y)) + xDir * Math.sin(Math.toRadians(rotation.y));
		
		if(Main.debug){
			gravity = false;
		}else{
			gravity = true;
		}
		
		move((float)xa, (float)ya, (float)za , this);
		
		xa *= 0.9f;
		ya *= 0.9f;
		za *= 0.9f;
		
		if(raycast.getBlock(world) != null){
			float x =  raycast.getBlock(world).x;
			float y =  raycast.getBlock(world).y;
			float z =  raycast.getBlock(world).z;
			selectedBlock = world.getBlock((int)x, (int)y, (int)z);
			selectedPosition = new Vector3f(x, y, z);
		}else{
			selectedBlock = null;
			selectedPosition = null;
		}
		if(selectedBlock != null){
			int x = (int) selectedPosition.x;
			int y = (int) selectedPosition.y;
			int z = (int) selectedPosition.z;
			float rx = selectedPosition.x;
			float ry = selectedPosition.y;
			float rz = selectedPosition.z;
			if(Inputs.getMouseDown(0)){
				world.removeBlock(x, y, z);
			}
			if(Inputs.getMouseDown(1)){
				float vx = rx - x;
				float vy = ry - y;
				float vz = rz - z;
				
				Vector3f check = new Vector3f(vx, vy, vz).check();
				
				int xp = (int) check.x;
				int yp = (int) check.y;
				int zp = (int) check.z;
				
				world.addBlock(x+xp, y+yp, z+zp, new LogBlock());
			}
		}
	}

	public void render() {
		if(selectedBlock != null){
			renderSelection(selectedPosition);
		}
	}
	
	private void renderSelection(Vector3f pos){
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
	}	}