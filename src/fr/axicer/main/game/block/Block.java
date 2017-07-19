package fr.axicer.main.game.block;

import org.lwjgl.util.vector.Vector2f;

import fr.axicer.main.game.Chunk;
import fr.axicer.main.util.datas.Vector2D;
import fr.axicer.main.util.render.TextureManager;

public class Block{
	
	public int x, y, z;
	public BlockType type;
	public BlockMetadata data;
	public Chunk chunk;

	public Block(BlockType type, BlockMetadata data, int x, int y, int z) {
		super();
		setType(type);
		setData(data);
		setPos(x, y, z);
	}
	public Block(BlockType type) {
		super();
		setType(type);
		setData(null);
		setPos(0, 0, 0);
	}
	public Block(BlockType type, BlockMetadata data) {
		this(type);
		setData(data);
	}
	public Block(BlockType type, int x, int y, int z) {
		this(type);
		setPos(x, y, z);
	}
	public Block(BlockMetadata data) {
		super();
		setType(BlockType.AIR);
		setData(data);
		setPos(0, 0, 0);
	}
	public Block(BlockMetadata data, int x, int y, int z) {
		this(data);
		setPos(x, y, z);
	}
	public Block(int x, int y, int z) {
		super();
		setType(BlockType.AIR);
		setData(null);
		setPos(x, y, z);
	}
	public Block() {
		setType(BlockType.AIR);
		setData(null);
		setPos(0, 0, 0);
	}
	public Block setData(BlockMetadata data) {
		this.data = data;
		return this;
	}

	public Block setPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	public Block setType(BlockType type) {
		this.type = type;
		return this;
	}
	public Block setChunk(Chunk chunk) {
		this.chunk = chunk;
		return this;
	}

	public void update() {

	}

	public float[] faceFrontTexData(){
		float cellX = (1.0f / TextureManager.envWidth) * 16.0f;
		float cellY = (1.0f / TextureManager.envHeight) * 16.0f;
		Vector2f uv = type.getFront();
		return new float[]{
				uv.x * cellX,			uv.y * cellY + cellY,
				uv.x * cellX + cellX,	uv.y * cellY + cellY,
				uv.x * cellX + cellX,	uv.y * cellY,
				uv.x * cellX,			uv.y * cellY,
			};
	}
	public float[] faceFrontData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x+s,y,	z,
			x,	y,	z,
			x,	y+s,z,
			x+s,y+s,z
		};
	}
	public float[] faceBackTexData(){
		float cellX = (1.0f / TextureManager.envWidth) * 16.0f;
		float cellY = (1.0f / TextureManager.envHeight) * 16.0f;
		Vector2f uv = type.getBack();
		return new float[]{
				uv.x * cellX,			uv.y * cellY + cellY,
				uv.x * cellX + cellX,	uv.y * cellY + cellY,
				uv.x * cellX + cellX,	uv.y * cellY,
				uv.x * cellX,			uv.y * cellY,
			};
	}
	public float[] faceBackData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x,	y,	z+s,
			x+s,y,	z+s,
			x+s,y+s,z+s,
			x,	y+s,z+s
		};
	}
	public float[] faceDownTexData(){
		float cellX = (1.0f / TextureManager.envWidth) * 16.0f;
		float cellY = (1.0f / TextureManager.envHeight) * 16.0f;
		Vector2f uv = type.getDown();
		return new float[]{
				uv.x * cellX,			uv.y * cellY,
				uv.x * cellX + cellX,	uv.y * cellY,
				uv.x * cellX + cellX,	uv.y * cellY + cellY,
				uv.x * cellX,			uv.y * cellY + cellY
			};
	}
	public float[] faceDownData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x,	y,	z,
			x+s,y,	z,
			x+s,y,	z+s,
			x,	y,	z+s
		};
	}
	public float[] faceUpTexData(){
		float cellX = (1.0f / TextureManager.envWidth) * 16.0f;
		float cellY = (1.0f / TextureManager.envHeight) * 16.0f;
		Vector2f uv = type.getUp();
		return new float[]{
				uv.x * cellX,			uv.y * cellY,
				uv.x * cellX + cellX,	uv.y * cellY,
				uv.x * cellX + cellX,	uv.y * cellY + cellY,
				uv.x * cellX,			uv.y * cellY + cellY
			};
	}
	public float[] faceUpData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x+s,y+s,z,
			x,	y+s,z,
			x,	y+s,z+s,
			x+s,y+s,z+s
		};
	}
	public float[] faceLeftTexData(){
		float cellX = (1.0f / TextureManager.envWidth) * 16.0f;
		float cellY = (1.0f / TextureManager.envHeight) * 16.0f;
		Vector2f uv = type.getLeft();
		return new float[]{
				uv.x * cellX,			uv.y * cellY,
				uv.x * cellX,			uv.y * cellY + cellY,
				uv.x * cellX + cellX,	uv.y * cellY + cellY,
				uv.x * cellX + cellX,	uv.y * cellY,
			};
	}
	public float[] faceLeftData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x,	y+s,z,
			x,	y,	z,
			x,	y,	z+s,
			x,	y+s,z+s
		};
	}
	public float[] faceRightTexData(){
		float cellX = (1.0f / TextureManager.envWidth) * 16.0f;
		float cellY = (1.0f / TextureManager.envHeight) * 16.0f;
		Vector2f uv = type.getRight();
		return new float[]{
				uv.x * cellX + cellX,	uv.y * cellY + cellY,
				uv.x * cellX + cellX,	uv.y * cellY,
				uv.x * cellX,			uv.y * cellY,
				uv.x * cellX,			uv.y * cellY + cellY,
			};
	}
	public float[] faceRightData(int x, int y, int z){
		float s = 1;
		return new float[]{
			x+s,y,	z,
			x+s,y+s,z,
			x+s,y+s,z+s,
			x+s,y,	z+s
		};
	}
}