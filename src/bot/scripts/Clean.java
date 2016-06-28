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
					for (int i = 0; i <= 27; i++) {
						Bot.clickItem(dirty, i);
						Thread.sleep(100);
					}
				}
				
				else {
					Bot.clickNearestBank();
					Thread.sleep(1500);
					Bot.bankAll();
					Bot.withdrawItem(dirty, Bot.getBank().getSlotByItem(dirty));
					Bot.walkTo(Bot.getMyPlayerPos());
					Thread.sleep(500);
				}
			

			} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onRepaint(Graphics g) {
		
	}
}
