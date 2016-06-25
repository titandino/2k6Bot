package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import bot.utils.*;

import com.Client;

import bot.Bot;

public class Pickpocket extends Script {

	long startTime;
	int startXp;
	Area mine = new Area(new Tile(2624, 3129, 0), new Tile(2650, 3153, 0));

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.THIEVING);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (Bot.getInventory().freeSlots() >= 0 && !Bot.isAnimating()) {
				Bot.clickNPC(args[1].replace("_", " "), 2);
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
		g2d.drawString("C9 thiiief", 15, 15);
		g2d.setFont(new Font("Arial", 1, 12));
		g2d.drawString("Thieving xp p/h: " + Bot.getFormattedXpPerHour(Bot.THIEVING, startXp, startTime), 15, 47);
		g2d.setColor(Color.GREEN);
		g2d.drawString("Time Running: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 62);
		g2d.drawString("Stage: " + stage, 15, 74);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}

