package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;

public class AIOCook extends Script {

	long startTime;
	int startXp;
	int startLogs;

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.COOKING);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (!Bot.hasAnimatedIn(5000)) {
				if (!Bot.getInventory().contains(Integer.valueOf(args[1]), 1)) {
					stage = "I need more fish";
					Bot.clickClosestWorldObject("bank booth", "use");
					Thread.sleep(1000);
					Bot.bankAll();
					Thread.sleep(1000);
					Bot.withdrawItem(Integer.valueOf(args[1]), Bot.getBank().getSlotByItem(Integer.valueOf(args[1].replace("_", " "))));
					Thread.sleep(1000);
				} else {
					stage = "This is hot";
					Bot.itemOnObject(Integer.valueOf(args[1]), Bot.getClosestWorldObject("range"));
					Thread.sleep(1000);
					Bot.clickButton(13718);
					Thread.sleep(1000);
					Bot.sendIntegerInput(50);
					Thread.sleep(5000);
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void onRepaint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(205, 113, 2);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.setColor(Color.red);
		g2d.fillRoundRect(0, 0, 200, 108, 15, 15);
		g2d.setColor(new Color(0, 255, 0));
		g2d.drawRoundRect(0, 0, 200, 108, 15, 15);
		g2d.setFont(new Font("System", 1, 20));
		g2d.drawString("Mm fishies", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		g2d.drawString("Cook xp p/h: " + Bot.getFormattedXpPerHour(Bot.COOKING, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g2d.setFont(new Font("System", 1, 10));
		g2d.drawString("Stage: " + stage, 15, 95);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
