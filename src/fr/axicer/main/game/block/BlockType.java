package fr.axicer.main.game.block;

import org.lwjgl.util.vector.Vector2f;

public enum BlockType {
	
	AIR("air",new Vector2f(0,0), new Vector2f(0,0), new Vector2f(0,0), new Vector2f(0,0), new Vector2f(0,0), new Vector2f(0,0)),
	STONE("stone",new Vector2f(1,0), new Vector2f(1,0), new Vector2f(1,0), new Vector2f(1,0), new Vector2f(1,0), new Vector2f(1,0)),
	GRASS("grass",new Vector2f(2,0), new Vector2f(3,0), new Vector2f(2,1), new Vector2f(2,1), new Vector2f(2,1), new Vector2f(2,1)),
	DIRT("dirt",new Vector2f(3,0), new Vector2f(3,0), new Vector2f(3,0), new Vector2f(3,0), new Vector2f(3,0), new Vector2f(3,0)),
	BEDROCK("bedrock",new Vector2f(4,0), new Vector2f(4,0), new Vector2f(4,0), new Vector2f(4,0), new Vector2f(4,0), new Vector2f(4,0)),
	LOG("log",new Vector2f(5,1), new Vector2f(5,1), new Vector2f(5,0), new Vector2f(5,0), new Vector2f(5,0), new Vector2f(5,0)),
	LEAVES("leaves", new Vector2f(6,0), new Vector2f(6,0), new Vector2f(6,0), new Vector2f(6,0), new Vector2f(6,0), new Vector2f(6,0));
	
	private String id;
	private Vector2f Up,Down,Left,Right,Front,Back;
	
	private BlockType(String ID, Vector2f Up, Vector2f Down, Vector2f Left, Vector2f Right, Vector2f Front, Vector2f Back) {
		this.id = ID;
		this.Up = Up;
		this.Down = Down;
		this.Left = Left;
		this.Right = Right;
		this.Front = Front;
		this.Back = Back;
	}
	
	public String getID(){
		return this.id;
	}
	public Vector2f getUp() {
		return Up;
	}
	public Vector2f getDown() {
		return Down;
	}
	public Vector2f getLeft() {
		return Left;
	}
	public Vector2f getRight() {
		return Right;
	}
	public Vector2f getFront() {
		return Front;
	}
	public Vector2f getBack() {
		return Back;
	}
}
