package bot.scripts;

import java.awt.*;

import bot.Bot;

public class Craft extends Script {
	
	int chisel = 1755;
	int currGem;
	int diamond = 1617; 
	int ruby = 1619; 
	int emerald = 1621; 
	int sapphire = 1623; 
    int topaz = 1629; 
	int jade = 1627; 
	int opal = 1625; 
	
	@Override
	public boolean onStart() {
		return true;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			
			if ((Bot.getBank().contains(diamond, 1) && Bot.getLevel(Bot.CRAFTING) >= 43)
					|| Bot.getInventory().contains(diamond, 1))
				currGem = diamond;
			else if ((Bot.getBank().contains(ruby, 1) && Bot.getLevel(Bot.CRAFTING) >= 34)
					|| Bot.getInventory().contains(ruby, 1))
				currGem = ruby;
			else if ((Bot.getBank().contains(emerald, 1) && Bot.getLevel(Bot.CRAFTING) >= 27)
					|| Bot.getInventory().contains(emerald, 1))
				currGem = emerald;
			else if ((Bot.getBank().contains(sapphire, 1) && Bot.getLevel(Bot.CRAFTING) >= 20)
					|| Bot.getInventory().contains(sapphire, 1))
				currGem = sapphire;
			else if ((Bot.getBank().contains(topaz, 1) && Bot.getLevel(Bot.CRAFTING) >= 16)
					|| Bot.getInventory().contains(topaz, 1))
				currGem = topaz;
			else if ((Bot.getBank().contains(jade, 1) && Bot.getLevel(Bot.CRAFTING) >= 13)
					|| Bot.getInventory().contains(jade, 1))
				currGem = jade;
			else if (Bot.getBank().contains(opal, 1) || Bot.getInventory().contains(opal, 1))
				currGem = opal;
			
				if (Bot.getInventory().contains(currGem, 1)) {
					Bot.itemOnItem(chisel, currGem);
					Thread.sleep(800);
				}
				
				else if (!Bot.getInventory().contains(currGem, 1)) {
					Bot.clickNearestBank();
					Thread.sleep(2000);
					Bot.bankAll(chisel);
					Bot.withdrawItem(currGem, Bot.getBank().getSlotByItem(currGem));
					Thread.sleep(3000);
					Bot.walkTo(Bot.getMyPlayerPos());
					Thread.sleep(1000);
				}
			

			} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onRepaint(Graphics g) {
		
	}
}
