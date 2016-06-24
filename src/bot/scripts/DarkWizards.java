package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;

public class DarkWizards extends Script {
	
	int startAtk;
	int startStr;
	int startDef;
	int startPray;
	long timeStarted;
	
	String stage = "Starting";
	
	@Override
	public boolean onStart() {
		startAtk = Bot.clientt.currentExp[0];
		startDef = Bot.clientt.currentExp[1];
		startStr = Bot.clientt.currentExp[2];
		startPray = Bot.clientt.currentExp[5];
		timeStarted = System.currentTimeMillis();
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			while (Bot.getInventory().contains(526, 1)) {
				stage = "Burying bones";
				Bot.clickItem(526);
				Thread.sleep(5000);
			}
			Bot.findAndPickupItems("bones", "rune");
			if (!Bot.myPlayerInCombat()) {
				stage = "Piledriving wizards";
				Bot.attackNPC(174);
				Thread.sleep(3000);
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void onRepaint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(205, 113, 2);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRoundRect(0, 0, 200, 108, 15, 15);
		g2d.setColor(new Color(0, 255, 255, 150));
		g2d.drawRoundRect(0, 0, 200, 108, 15, 15);
		g2d.setFont(new Font("Arial", 1, 12));
		g2d.drawString("Trent's Wizard Penetrator", 15, 15);
		g2d.drawString("Attack p/h: " + Bot.getFormattedXpPerHour(Bot.ATTACK, startAtk, timeStarted), 15, 39);
		g2d.drawString("Strength p/h: " + Bot.getFormattedXpPerHour(Bot.STRENGTH, startStr, timeStarted), 15, 51);
		g2d.drawString("Defence p/h: " + Bot.getFormattedXpPerHour(Bot.DEFENCE, startDef, timeStarted), 15, 63);
		g2d.drawString("Prayer p/h: " + Bot.getFormattedXpPerHour(Bot.PRAYER, startPray, timeStarted), 15, 75);
		g2d.drawString("Stage: " + stage, 15, 87);
		g2d.drawString("In combat: " + Bot.myPlayerInCombat(), 15, 99);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
