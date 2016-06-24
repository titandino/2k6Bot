package bot.utils;

public class WorldObject extends Tile {
	
	private int id;
	
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
	
}
