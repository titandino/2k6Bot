package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;

public class Alch extends Script {

	long startTime;
	int startXp;

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.MAGIC);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			Bot.alchItem(Integer.valueOf(args[1]));
			Thread.sleep(1000);
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
		g2d.drawString("Trent's Alcher", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		g2d.drawString("Magic xp p/h: " + Bot.getFormattedXpPerHour(Bot.MAGIC, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
