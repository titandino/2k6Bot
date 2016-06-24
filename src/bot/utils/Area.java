package bot.utils;

public class Area {

	public Tile bottomLeft, topRight;
	
	public Area(Tile bottomLeft, Tile topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
	}
	
	public boolean within(Tile tile) {
		return !((tile.getX() >= bottomLeft.getX()) && (tile.getY() >= bottomLeft.getY()) && 
				(tile.getX() <= topRight.getX()) && (tile.getY() <= topRight.getY()));
	}
	
}
