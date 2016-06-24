package bot.scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

import bot.Bot;

public class RuneLooter extends Script {
	
	int earth;
	int water;
	int fire;
	int body;
	int mind;
	int chaos;
	long timeStarted;
	
	@Override
	public boolean onStart() {
		earth = Bot.getInventory().numberOf(557);
		water = Bot.getInventory().numberOf(555);
		fire = Bot.getInventory().numberOf(554);
		body = Bot.getInventory().numberOf(559);
		mind = Bot.getInventory().numberOf(558);
		chaos = Bot.getInventory().numberOf(562);
		timeStarted = System.currentTimeMillis();
		return true;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			Bot.findAndPickupItems("rune");

			} catch(Exception e) {
			e.printStackTrace();
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
		g2d.setFont(new Font("Arial", 1, 12));
		g2d.drawString("Devin's Magical Fuckin Magic Shit", 15, 15);
		g2d.drawString("Earth p/h: " + Bot.getFormattedLootPerHour(Bot.getInventory().numberOf(557), earth, timeStarted), 15, 40);
		g2d.drawString("Water p/h: " + Bot.getFormattedLootPerHour(Bot.getInventory().numberOf(555), water, timeStarted), 15, 52);
		g2d.drawString("Fire p/h: " + Bot.getFormattedLootPerHour(Bot.getInventory().numberOf(554), fire, timeStarted), 15, 64);
		g2d.drawString("Body p/h: " + Bot.getFormattedLootPerHour(Bot.getInventory().numberOf(559), body, timeStarted), 15, 76);
		g2d.drawString("Mind p/h: " + Bot.getFormattedLootPerHour(Bot.getInventory().numberOf(558), mind, timeStarted), 15, 88);
		g2d.drawString("Chaos p/h: " + Bot.getFormattedLootPerHour(Bot.getInventory().numberOf(562), chaos, timeStarted), 15, 100);
		g2d.setColor(Color.GREEN);
		g2d.drawString("Time running: " + Bot.getScriptTime(System.currentTimeMillis(), timeStarted) , 15, 115);
		g.drawImage(bufferedImage, 550, 350, null);
	}
}
