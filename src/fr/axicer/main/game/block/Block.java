package fr.axicer.main.game.block;

import fr.axicer.main.math.Color4f;

public abstract class Block{
	
	public Color4f color;
	
	public Block(Color4f color) {
		this.color = color;
	}

	public float[] faceFrontData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x+s,y,z,	color.r*0.8f, color.g*0.8f, color.b*0.8f, color.a,
			x,y,z,		color.r*0.8f, color.g*0.8f, color.b*0.8f, color.a,
			x,y+s,z,	color.r*0.8f, color.g*0.8f, color.b*0.8f, color.a,
			x+s,y+s,z,	color.r*0.8f, color.g*0.8f, color.b*0.8f, color.a
		};
	}
	public float[] faceBackData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x,y,z+s,	color.r*0.8f, color.g*0.8f, color.b*0.8f, color.a,
			x+s,y,z+s,	color.r*0.8f, color.g*0.8f, color.b*0.8f, color.a,
			x+s,y+s,z+s,color.r*0.8f, color.g*0.8f, color.b*0.8f, color.a,
			x,y+s,z+s,	color.r*0.8f, color.g*0.8f, color.b*0.8f, color.a
		};
	}
	public float[] faceDownData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x,y,z,		color.r*0.6f, color.g*0.6f, color.b*0.6f, color.a,
			x+s,y,z,	color.r*0.6f, color.g*0.6f, color.b*0.6f, color.a,
			x+s,y,z+s,	color.r*0.6f, color.g*0.6f, color.b*0.6f, color.a,
			x,y,z+s,	color.r*0.6f, color.g*0.6f, color.b*0.6f, color.a
		};
	}
	public float[] faceUpData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x+s,y+s,z,	color.r*0.9f, color.g*0.9f, color.b*0.9f, color.a,
			x,y+s,z,	color.r*0.9f, color.g*0.9f, color.b*0.9f, color.a,
			x,y+s,z+s,	color.r*0.9f, color.g*0.9f, color.b*0.9f, color.a,
			x+s,y+s,z+s,color.r*0.9f, color.g*0.9f, color.b*0.9f, color.a
		};
	}
	public float[] faceLeftData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x,y+s,z,	color.r*0.7f, color.g*0.7f, color.b*0.7f, color.a,
			x,y,z,		color.r*0.7f, color.g*0.7f, color.b*0.7f, color.a,
			x,y,z+s,	color.r*0.7f, color.g*0.7f, color.b*0.7f, color.a,
			x,y+s,z+s,	color.r*0.7f, color.g*0.7f, color.b*0.7f, color.a
		};
	}
	public float[] faceRightData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x+s,y,z,	color.r*0.7f, color.g*0.7f, color.b*0.7f, color.a,
			x+s,y+s,z,	color.r*0.7f, color.g*0.7f, color.b*0.7f, color.a,
			x+s,y+s,z+s,color.r*0.7f, color.g*0.7f, color.b*0.7f, color.a,
			x+s,y,z+s,	color.r*0.7f, color.g*0.7f, color.b*0.7f, color.a
		};
	}
}