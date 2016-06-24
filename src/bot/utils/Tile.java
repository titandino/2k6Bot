package bot.utils;

public class Tile {
	
	private int x, y, z;
	
	public Tile(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public boolean equals(int x, int y) {
		return this.getX() == x && this.getY() == y;
	}
	
	@Override
	public String toString() {
		return "("+this.x+", "+this.y+","+this.z+")";
	}

}
