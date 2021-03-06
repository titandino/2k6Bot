package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;

public class Splash extends Script {
	
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
			Bot.spellOnNpcNoClip("man", 1153);
			Thread.sleep(1500);
			Bot.spellOnNpcNoClip("man", 1157);
			Thread.sleep(1500);
			Bot.spellOnNpcNoClip("man", 1161);
			Thread.sleep(1500);
		} catch (Exception e) {
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
		g2d.drawString("Trent's Speller", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		g2d.drawString("Magic xp p/h: " + Bot.getFormattedXpPerHour(Bot.MAGIC, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g.drawImage(bufferedImage, 0, 0, null);
	}
}
