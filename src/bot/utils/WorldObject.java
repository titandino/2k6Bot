package bot.utils;

public class WorldObject extends Tile {
	
	private int id;
	private int sizeX;
	private int sizeY;
	private int rotation;
	
	public WorldObject(int id, int rotation, int sizeX, int sizeY, int x, int y) {
		super(x, y, 0);
		this.id = id;
		this.rotation = rotation;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public WorldObject(int id, int x, int y) {
		super(x, y, 0);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "["+id+"@("+this.getX()+", "+this.getY()+","+this.getZ()+")]";
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
}
