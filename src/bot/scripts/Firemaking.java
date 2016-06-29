package bot.scripts;

import java.awt.Graphics;

import bot.Bot;

public class Firemaking extends Script {

	
	@Override
	public boolean onStart() {
		return true;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			Bot.itemOnItem(590, 1511, 1, 2);
			Thread.sleep(5000);

			} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onRepaint(Graphics g) {
	}
}
