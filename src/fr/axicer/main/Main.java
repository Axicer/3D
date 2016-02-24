package fr.axicer.main;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import fr.axicer.main.game.Game;
import fr.axicer.main.game.inputs.Inputs;
import fr.axicer.main.render.DisplayManager;
import fr.axicer.main.render.SkyBox;

public class Main {
	
	boolean running = false;
	public static final int FRAME_CAP = 100000;
	public static boolean debug = false;
	public static float fov = 70.0f;
	Game game;
	SkyBox skybox;
	
	public Main() {
		DisplayManager.create(720, 480, "TEST3D");
	}
	
	public void start(){
		running = true;
		game = new Game();
		String bottom = "/tex/cubemap/bottomSide.png";
		String up = "/tex/cubemap/topSide.png";
		String left = "/tex/cubemap/leftSide.png";
		String right = "/tex/cubemap/rightSide.png";
		String front = "/tex/cubemap/frontSide.png";
		String back = "/tex/cubemap/backSide.png";
		
		skybox = new SkyBox(new String[]{left,right,up,bottom,back,front});
		loop();
	}
	
	public void stop(){
		running = false;
	}
	
	public void exit(){
		DisplayManager.dispose();
		System.exit(0);
	}
	
	public void loop(){
		long lastTimeTick = System.nanoTime();
		long lastRenderTime = System.nanoTime();
		
		double tickTime = 1000000000.0/60.0;
		double renderTime = 1000000000.0/FRAME_CAP;
		
		int frames = 0;
		int ticks = 0;
		
		long timer = System.currentTimeMillis();
		
		while(running){
			if(DisplayManager.isClosed()) stop();
			
			if(System.nanoTime() - lastTimeTick > tickTime){
				update();
				lastTimeTick += tickTime;
				ticks++;
			}
			
			if(System.nanoTime() - lastRenderTime > renderTime){
				render();
				DisplayManager.update();
				lastRenderTime += renderTime;
				frames++;
			}
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("frames: "+frames+" FPS, ticks: "+ticks);
				frames = 0;
				ticks = 0;
			}else{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		exit();
	}
	
	public void render(){
		//clear buffers
		DisplayManager.clearBuffers();
		
		//check for resize
		if(Display.wasResized()) glViewport(0, 0, Display.getWidth(), Display.getHeight());

		//create perspective
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov, (float)Display.getWidth()/(float)Display.getHeight(), 0.1f, 1000000.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		//rotate view
		glRotatef(game.getPlayer().rotation.getX(), 1, 0, 0);
		glRotatef(game.getPlayer().rotation.getY(), 0, 1, 0);
		glTranslatef(-game.getPlayer().position.getX(), -game.getPlayer().position.getY()-1.75f, -game.getPlayer().position.getZ());
		
		//give a default color (black for no racism)
		GL11.glColor3f(0, 0, 0);
		//render differents elements
		skybox.render(game.getPlayer().position);
		game.render();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluOrtho2D(0, Display.getWidth(), 0, Display.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		renderGUI();
	}
	
	public void update(){
		Inputs.update();
		
		if(!Mouse.isGrabbed() && Mouse.isButtonDown(0))Mouse.setGrabbed(true);
		if(Mouse.isGrabbed() && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))Mouse.setGrabbed(false);
		if(!Mouse.isGrabbed())return;
		
		game.update();
	}
	
	public void renderGUI(){
		game.renderGUI();
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
}
