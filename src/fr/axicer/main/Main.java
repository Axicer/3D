package fr.axicer.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import fr.axicer.main.game.Game;
import fr.axicer.main.game.inputs.Inputs;
import fr.axicer.main.util.render.DisplayManager;
import fr.axicer.main.util.render.Shader;
import fr.axicer.main.util.render.SkyBox;
import fr.axicer.main.util.render.TextureManager;

public class Main {
	
	boolean running = false;
	public static final int FRAME_CAP = 100000;
	public static Game game;
	SkyBox skybox;
	
	public Main() {
		DisplayManager.create(720, 480, "TEST3D");
	}
	
	public void start(){
		running = true;
		String bottom = "/tex/cubemap/bottomSide.png";
		String up = "/tex/cubemap/topSide.png";
		String left = "/tex/cubemap/leftSide.png";
		String right = "/tex/cubemap/rightSide.png";
		String front = "/tex/cubemap/frontSide.png";
		String back = "/tex/cubemap/backSide.png";
		
		skybox = new SkyBox(new String[]{left,right,up,bottom,back,front});
		try {
			BufferedImage image = ImageIO.read(TextureManager.class.getResourceAsStream("/tex/env.png"));
			TextureManager.envTexture = TextureManager.loadTexture(image);
			TextureManager.envWidth = image.getWidth();
			TextureManager.envHeight = image.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Shader.CreateShaders();
		
		game = new Game();
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
		
		//render differents elements
		skybox.render(game.cam.player.position);
		game.render();
		
		/*glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glColor3f(1, 1, 1);
		GLU.gluOrtho2D(0, Display.getWidth(), 0, Display.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();*/
	}
	
	public void update(){
		Inputs.update();
		
		if(!Mouse.isGrabbed() && Mouse.isButtonDown(0))Mouse.setGrabbed(true);
		if(Mouse.isGrabbed() && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))Mouse.setGrabbed(false);
		if(!Mouse.isGrabbed())return;
		
		game.update();
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
}
