package fr.axicer.main.game.inputs;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class Inputs {
	
	public static int NUM_KEYCODES = 256;
	public static int NUM_MOUSEBUTTONS = 5;
	
	private static ArrayList<Integer> currentKeys = new ArrayList<>();
	private static ArrayList<Integer> upKeys = new ArrayList<>();
	private static ArrayList<Integer> downKeys = new ArrayList<>();
	
	private static ArrayList<Integer> currentMouse = new ArrayList<>();
	private static ArrayList<Integer> upMouse = new ArrayList<>();
	private static ArrayList<Integer> downMouse = new ArrayList<>();
	
	public static void update(){
		upKeys.clear();
		for(int i = 0 ; i < NUM_KEYCODES ; i++){
			if(!getKey(i) && currentKeys.contains(i)){
				upKeys.add(i);
			}
		}
		
		downKeys.clear();
		for(int i = 0 ; i < NUM_KEYCODES ; i++){
			if(getKey(i) && !currentKeys.contains(i)){
				downKeys.add(i);
			}
		}
		
		currentKeys.clear();
		for(int i = 0 ; i < NUM_KEYCODES ; i++){
			if(getKey(i)){
				currentKeys.add(i);
			}
		}
		
		upMouse.clear();
		for(int i = 0 ; i < NUM_MOUSEBUTTONS ; i++){
			if(!getMouse(i) && currentMouse.contains(i)){
				upMouse.add(i);
			}
		}
		
		downMouse.clear();
		for(int i = 0 ; i < NUM_MOUSEBUTTONS ; i++){
			if(getMouse(i) && !currentMouse.contains(i)){
				downMouse.add(i);
			}
		}
		
		currentMouse.clear();
		for(int i = 0 ; i < NUM_MOUSEBUTTONS ; i++){
			if(getMouse(i)){
				currentMouse.add(i);
			}
		}
	}
	
	public static boolean getKey(int keycode){
		return Keyboard.isKeyDown(keycode);
	}
	public static boolean getKeyDown(int keycode){
		return downKeys.contains(keycode);
	}
	public static boolean getKeyUp(int keycode){
		return upKeys.contains(keycode);
	}
	public static boolean getMouse(int mousebutton){
		return Mouse.isButtonDown(mousebutton);
	}
	public static boolean getMouseDown(int mouseButton){
		return downMouse.contains(mouseButton);
	}
	public static boolean getMouseUp(int mouseButton){
		return upMouse.contains(mouseButton);
	}
	public static Vector2f getCenterPos(){
		return new Vector2f((float)Display.getWidth()/2.0f, (float) Display.getHeight()/2.0f);
	}
	public static Vector2f getMousePos(){
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
	public static void centerMouse(){
		setMousePosition(getCenterPos());
	}
	public static void setMousePosition(Vector2f pos){
		Mouse.setCursorPosition((int)pos.x, (int)pos.y);
	}
}
