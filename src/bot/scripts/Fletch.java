package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;

public class Fletch extends Script {

	long startTime;
	int startXp;

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.FLETCHING);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (!Bot.isAnimating()) {
				if (!Bot.getInventory().contains(args[1].replace("_", " "), 1)) {
					stage = "Banking";
					Bot.clickNearestBank();
					Thread.sleep(1500);
					Bot.depositAllBySlot(1);
					Thread.sleep(1000);
					Bot.withdrawItem(Bot.getBank().getItem(Bot.getBank().getSlotByItem(args[1].replace("_", " "))), Bot.getBank().getSlotByItem(args[1].replace("_", " ")));
					Thread.sleep(1000);
				} else {
					stage = "lathe.exe";
					Bot.itemOnItem("knife", args[1].replace("_", " "));
					Thread.sleep(1000);
					if (args[2].equalsIgnoreCase("shortbow"))
						Bot.clickButton(8871);
					else
						Bot.clickButton(8875);
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
		g2d.setColor(Color.darkGray);
		g2d.fillRoundRect(0, 0, 200, 108, 15, 15);
		g2d.setColor(new Color(0, 255, 0));
		g2d.drawRoundRect(0, 0, 200, 108, 15, 15);
		g2d.setFont(new Font("System", 1, 20));
		g2d.drawString("Trent's AIO Fletch", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		g2d.drawString("Fletch xp p/h: " + Bot.getFormattedXpPerHour(Bot.FLETCHING, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g2d.setFont(new Font("System", 1, 10));
		g2d.drawString("Stage: " + stage, 15, 95);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
