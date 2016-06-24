package bot.utils;

import java.util.Random;

public class Utils {
	
	private static final Random RANDOM = new Random();
	
	public static final int distance(Tile t1, Tile t2) {
		return (int) Math.sqrt(((t1.getX()-t2.getX())*(t1.getX()-t2.getX()))+((t1.getY()-t2.getY())*(t1.getY()-t2.getY())));
	}
	
	public static final int getRandom(int maxValue) {
		return (int) (Math.random() * (maxValue + 1));
	}

	public static final int random(int min, int max) {
		final int n = Math.abs(max - min);
		return Math.min(min, max) + (n == 0 ? 0 : random(n));
	}

	public static final double random(double min, double max) {
		final double n = Math.abs(max - min);
		return Math.min(min, max) + (n == 0 ? 0 : random((int) n));
	}
	
	public static final int random(int maxValue) {
		if (maxValue <= 0)
			return 0;
		return RANDOM.nextInt(maxValue);
	}

}
