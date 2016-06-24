package bot.scripts;

import java.awt.Graphics;

public abstract class Script implements Runnable {
	
	public boolean started = false;
	public String[] args;

	@Override
	public void run() {
		if (!started) {
			while(!started) {
				started = onStart();
			}
		}
	}
	
	public abstract boolean onStart();
	public abstract void onRepaint(Graphics g);
}
