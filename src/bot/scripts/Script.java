package bot.scripts;

import java.awt.Graphics;

import bot.Bot;

public abstract class Script implements Runnable {

	public boolean started = false;
	public String[] args;

	@Override
	public void run() {
		if (!started) {
			while (!started) {
				started = onStart();
			}
		}
		try {
			if (Bot.getPlayerByName("mod ali") != null)
				Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public abstract boolean onStart();

	public abstract void onRepaint(Graphics g);
}
