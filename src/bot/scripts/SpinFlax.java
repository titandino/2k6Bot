package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;
import bot.utils.WorldObject;

public class SpinFlax extends Script {

	long startTime;
	int startXp;

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.CRAFTING);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (!Bot.getInventory().contains(1779, 1)) {
				if (Bot.clientt.plane != 0) {
					stage = "Firemanning it";
					Bot.clickWorldObject(new WorldObject(1746, 2715, 3470));
					Thread.sleep(1000);
				} else {
					stage = "Banking";
					Bot.clickClosestWorldObject("bank booth", "use");
					Thread.sleep(2000);
					Bot.depositAllBySlot(0);
					Thread.sleep(1000);
					Bot.withdrawItem(1779, Bot.getBank().getSlotByItem(1779));
					Thread.sleep(1000);
				}
			} else {
				if (Bot.clientt.plane != 1) {
					stage = "Climbin' it";
					Bot.clickWorldObject(new WorldObject(1747, 2715, 3470));
					Thread.sleep(1000);
				} else {
					stage = "Spinnin";
					if (!Bot.hasAnimatedIn(3000)) {
						Bot.clickClosestWorldObject(2644);
						Thread.sleep(1000);
						Bot.clickButton(8890);
						Thread.sleep(600);
						Bot.sendIntegerInput(50);
						Thread.sleep(5000);
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
		g2d.drawString("Trent's Spinner", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		g2d.drawString("Wc xp p/h: " + Bot.getFormattedXpPerHour(Bot.CRAFTING, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g2d.setFont(new Font("System", 1, 10));
		g2d.drawString("Stage: " + stage, 15, 95);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
