package bot.scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

import bot.Bot;

public class Combat extends Script {
	
	int[] drops = new int[] { 314, 995 };
	
	Stage currentStage = Stage.STARTING;
	
	int startAtk;
	int startStr;
	int startDef;
	int startPray;
	long timeStarted;
	
	String stage = "Starting";
	
	private enum Stage {
		STARTING,
		KILLING,
		LOOTING;
	}

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
			if (args.length > 1)
				Bot.findAndPickupItems(args);
			if (!Bot.myPlayerInCombat()) {
				stage = "Attacking shit";
				Bot.attackNPC(args[1].replace("_", " "));
				Thread.sleep(3000);
			}
		} catch(Exception e) {
			e.printStackTrace();
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
		g2d.drawString("Trent's Monster Fucker", 15, 15);
		g2d.drawString("Attack p/h: " + Bot.getFormattedXpPerHour(Bot.ATTACK, startAtk, timeStarted), 15, 39);
		g2d.drawString("Strength p/h: " + Bot.getFormattedXpPerHour(Bot.STRENGTH, startStr, timeStarted), 15, 51);
		g2d.drawString("Defence p/h: " + Bot.getFormattedXpPerHour(Bot.DEFENCE, startDef, timeStarted), 15, 63);
		g2d.drawString("Prayer p/h: " + Bot.getFormattedXpPerHour(Bot.PRAYER, startPray, timeStarted), 15, 75);
		g2d.drawString("Stage: " + stage, 15, 87);
		g2d.drawString("In combat: " + Bot.myPlayerInCombat(), 15, 99);
		g.drawImage(bufferedImage, 0, 0, null);
	}
}