package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;

public class PureEss extends Script {
	
	int startMining;
	long timeStarted;
	
	String stage = "Starting";
	
	@Override
	public boolean onStart() {
		startMining = Bot.clientt.currentExp[14];
		timeStarted = System.currentTimeMillis();
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			while (Bot.getInventory().freeSlots() == 0) {
				stage = "Banking";
				Bot.getClosestWorldObject(2213);
				Thread.sleep(2000);
				Bot.depositAllBySlot(1);
				Thread.sleep(500);
				
			
			}
			Bot.findAndPickupItems("bones", "rune");
			if (!Bot.myPlayerInCombat()) {
				stage = "Mining";
				Bot.attackNPC(174);
				Thread.sleep(3000);
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void onRepaint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(225, 113, 2);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRoundRect(0, 0, 225, 108, 15, 15);
		g2d.setColor(new Color(0, 255, 255, 150));
		g2d.drawRoundRect(0, 0, 225, 108, 15, 15);
		g2d.setFont(new Font("Arial", 1, 12));
		g2d.drawString("Pure Essence Only, No Nigger Essence", 15, 15);
		g2d.drawString("Mining XP p/h: " + Bot.getFormattedXpPerHour(Bot.MINING, startMining, timeStarted), 15, 39);		
		g2d.drawString("Stage: " + stage, 15, 87);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
