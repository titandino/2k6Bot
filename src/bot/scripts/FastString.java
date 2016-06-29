package bot.scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

import bot.Bot;

public class FastString extends Script {
	
	long startTime;
	int startXp;
	
	int unstrung;

	@Override
	public boolean onStart() {
		unstrung = Integer.valueOf(args[1]);
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.FLETCHING);
		return true;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
				if (Bot.getInventory().contains(unstrung, 1) && Bot.getInventory().contains(1777, 1)) {
					Bot.itemOnItem(1777, unstrung);
					Thread.sleep(200);
				} else {
					//Bot.clickNearestBank();
					//Thread.sleep(1500);
					Bot.depositAllBySlot(0);
					Thread.sleep(200);
					Bot.withdraw10(1777, Bot.getBank().getSlotByItem(1777));
					Thread.sleep(200);
					Bot.withdraw10(unstrung, Bot.getBank().getSlotByItem(unstrung));
					Thread.sleep(1000);
				}
			

			} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onRepaint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(205, 113, 2);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.setColor(Color.darkGray);
		g2d.fillRoundRect(0, 0, 200, 108, 15, 15);
		g2d.setColor(new Color(0, 255, 0));
		g2d.drawRoundRect(0, 0, 200, 108, 15, 15);
		g2d.setFont(new Font("System", 1, 20));
		g2d.drawString("Trent's Fast String", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		g2d.drawString("Fletch xp p/h: " + Bot.getFormattedXpPerHour(Bot.FLETCHING, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g.drawImage(bufferedImage, 0, 0, null);
	}
}
