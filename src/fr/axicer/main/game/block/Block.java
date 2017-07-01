package fr.axicer.main.game.block;

import fr.axicer.main.game.Chunk;
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

	public float[] faceFrontTexData(int x, int y, int z){
		return new float[]{
				(float) ((type.getFront().x+0f)/TextureManager.envWidth), (float) ((type.getFront().y+0f)/TextureManager.envHeight),
				(float) ((type.getFront().x+1f)/TextureManager.envWidth), (float) ((type.getFront().y+0f)/TextureManager.envHeight),
				(float) ((type.getFront().x+1f)/TextureManager.envWidth), (float) ((type.getFront().y+1f)/TextureManager.envHeight),
				(float) ((type.getFront().x+0f)/TextureManager.envWidth), (float) ((type.getFront().y+1f)/TextureManager.envHeight)
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
	public float[] faceBackTexData(int x, int y, int z){
		return new float[]{
				(float) ((type.getBack().x+0f)/TextureManager.envWidth), (float) ((type.getBack().y+0f)/TextureManager.envHeight),
				(float) ((type.getBack().x+1f)/TextureManager.envWidth), (float) ((type.getBack().y+0f)/TextureManager.envHeight),
				(float) ((type.getBack().x+1f)/TextureManager.envWidth), (float) ((type.getBack().y+1f)/TextureManager.envHeight),
				(float) ((type.getBack().x+0f)/TextureManager.envWidth), (float) ((type.getBack().y+1f)/TextureManager.envHeight)
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
	public float[] faceDownTexData(int x, int y, int z){
		return new float[]{
			(float) ((type.getDown().x+0f)/TextureManager.envWidth), (float) ((type.getDown().y+0f)/TextureManager.envHeight),
			(float) ((type.getDown().x+1f)/TextureManager.envWidth), (float) ((type.getDown().y+0f)/TextureManager.envHeight),
			(float) ((type.getDown().x+1f)/TextureManager.envWidth), (float) ((type.getDown().y+1f)/TextureManager.envHeight),
			(float) ((type.getDown().x+0f)/TextureManager.envWidth), (float) ((type.getDown().y+1f)/TextureManager.envHeight)
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
	public float[] faceUpTexData(int x, int y, int z){
		return new float[]{
			(float) ((type.getUp().x+0f)/TextureManager.envWidth), (float) ((type.getUp().y+0f)/TextureManager.envHeight),
			(float) ((type.getUp().x+1f)/TextureManager.envWidth), (float) ((type.getUp().y+0f)/TextureManager.envHeight),
			(float) ((type.getUp().x+1f)/TextureManager.envWidth), (float) ((type.getUp().y+1f)/TextureManager.envHeight),
			(float) ((type.getUp().x+0f)/TextureManager.envWidth), (float) ((type.getUp().y+1f)/TextureManager.envHeight)
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
	public float[] faceLeftTexData(int x, int y, int z){
		return new float[]{
			(float) ((type.getLeft().x+0f)/TextureManager.envWidth), (float) ((type.getLeft().y+0f)/TextureManager.envHeight),
			(float) ((type.getLeft().x+1f)/TextureManager.envWidth), (float) ((type.getLeft().y+0f)/TextureManager.envHeight),
			(float) ((type.getLeft().x+1f)/TextureManager.envWidth), (float) ((type.getLeft().y+1f)/TextureManager.envHeight),
			(float) ((type.getLeft().x+0f)/TextureManager.envWidth), (float) ((type.getLeft().y+1f)/TextureManager.envHeight)
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
	public float[] faceRightTexData(int x, int y, int z){
		return new float[]{
			(float) ((type.getRight().x+0f)/TextureManager.envWidth), (float) ((type.getRight().y+0f)/TextureManager.envHeight),
			(float) ((type.getRight().x+1f)/TextureManager.envWidth), (float) ((type.getRight().y+0f)/TextureManager.envHeight),
			(float) ((type.getRight().x+1f)/TextureManager.envWidth), (float) ((type.getRight().y+1f)/TextureManager.envHeight),
			(float) ((type.getRight().x+0f)/TextureManager.envWidth), (float) ((type.getRight().y+1f)/TextureManager.envHeight)
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