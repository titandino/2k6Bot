package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;

public class GnomeMagics extends Script {

	long startTime;
	int startXp;

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.WOODCUTTING);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (!Bot.isAnimating()) {
				if (Bot.getInventory().freeSlots() <= 0) {
					if (Bot.clientt.plane < 1) {
						stage = "Banking";
						Bot.clickClosestWorldObject("bank booth", "use");
						Thread.sleep(3000);
						Bot.depositAllBySlot(1);
						Thread.sleep(1000);
					} else {
						stage = "Climbing tree";
						Bot.clickClosestWorldObject(1742);
						Thread.sleep(1000);
					}
				} else {
					if (Bot.clientt.plane == 0) {
						stage = "chainsaw.exe";
						Bot.clickClosestWorldObject("magic tree", "chop down");
						Thread.sleep(3000);
					} else {
						stage = "Jumping out of tree";
						Bot.clickClosestWorldObject(1744);
						Thread.sleep(1000);
					}
				}
			}
		} catch (Exception e) {

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
		g2d.drawString("Trent's Magics", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		//g2d.drawString("Logs p/h: " + Bot.getFormattedLootPerHour(Bot.getBank().numberOf("log"), startLogs, startTime), 15, 40);
		g2d.drawString("Wc xp p/h: " + Bot.getFormattedXpPerHour(Bot.WOODCUTTING, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g2d.setFont(new Font("System", 1, 10));
		g2d.drawString("Stage: " + stage, 15, 95);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
