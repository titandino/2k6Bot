package bot.scripts;

import java.awt.*;

import bot.Bot;

public class Clean extends Script {
	
	int dirty;

	@Override
	public boolean onStart() {
		dirty = Integer.valueOf(args[1].replace("_", " "));
		return true;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
				if (Bot.getInventory().contains(dirty, 1)) {
					Bot.clickItem(dirty, Bot.getInventory().getSlotByItem(dirty));
				}
				
				else if (!Bot.getInventory().contains(dirty, 1)) {
					Bot.clickClosestWorldObject("bank booth", 2);
					Thread.sleep(500);
					Bot.bankAll();
					Bot.withdrawItem(dirty, Bot.getBank().getSlotByItem(dirty));
					Thread.sleep(500);
					Bot.walkTo(Bot.getMyPlayerPos());
				}
			

			} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onRepaint(Graphics g) {
		
	}
}
