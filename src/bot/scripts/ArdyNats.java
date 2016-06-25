package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;

public class ArdyNats extends Script {

	long startTime;
	int startXp;
	int startNats;

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.THIEVING);
		startNats = Bot.getInventory().numberOf(561);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (!Bot.isAnimating()) {
				Bot.clickClosestWorldObject("chest", 2);
				Thread.sleep(1000);
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void onRepaint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(210, 120, 2);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRoundRect(0, 0, 210, 120, 15, 15);
		g2d.setColor(new Color(0, 255, 255, 150));
		g2d.drawRoundRect(0, 0, 210, 120, 15, 15);
		g2d.setFont(new Font("Arial", 1, 16));
		g2d.drawString("Wicked Fast Nats Man", 15, 15);
		g2d.setFont(new Font("Arial", 1, 12));
		g2d.drawString("Thieving xp p/h: " + Bot.getFormattedXpPerHour(Bot.THIEVING, startXp, startTime), 15, 47);
		g2d.drawString("Nats p/hr: " + Bot.getFormattedLootPerHour(Bot.getInventory().numberOf(561), startNats, startTime), 15, 59);
		g2d.setColor(Color.GREEN);
		g2d.drawString("Time Running: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 74);
		g2d.drawString("Stage: " + stage, 15, 86);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
