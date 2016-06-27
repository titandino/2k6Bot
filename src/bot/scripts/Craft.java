package bot.scripts;

import java.awt.*;

import bot.Bot;

public class Craft extends Script {
	
	int uncut;
	int chisel = 1755;

	@Override
	public boolean onStart() {
		uncut = Integer.valueOf(args[1].replace("_", " "));
		return true;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
				if (Bot.getInventory().contains(uncut, 1)) {
					Bot.itemOnItem(chisel, uncut);
					Thread.sleep(800);
				}
				
				else if (!Bot.getInventory().contains(uncut, 1)) {
					Bot.clickClosestWorldObject("bank booth", 2);
					Thread.sleep(2000);
					Bot.bankAll(1755);
					Bot.withdrawItem(uncut, Bot.getBank().getSlotByItem(uncut));
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
