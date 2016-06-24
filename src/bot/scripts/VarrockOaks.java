package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.Client;

import bot.Bot;
import bot.utils.WorldObject;

public class VarrockOaks extends Script {
	
	long startTime;
	int startXp;
	int logsCut;
	
	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		logsCut = 0;
		startXp = Bot.getXp(Bot.WOODCUTTING);
		return true;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			if (Client.myPlayer.anim == -1) {
				if (Bot.getInventory().freeSlots() <= 0) {
					stage = "Banking";
					WorldObject object = Bot.getClosestWorldObject("bank booth");
					if (object != null) {
						Bot.clickObject(object.getId(), object.getX(), object.getY());
						Thread.sleep(1200);
						Bot.bankAllItems();
					}
				}
				if (Bot.getXp(Bot.WOODCUTTING) < 2600) {
					//stage = "Getting level 15";
					WorldObject object = Bot.getClosestWorldObject("tree");
					if (object != null) {
						stage = "Cutting "+object;
						Bot.clickObject(object.getId(), object.getX(), object.getY());
						Thread.sleep(3000);
					}
				} else {
					//stage = "chainsaw.exe";
					WorldObject object = Bot.getClosestWorldObject("oak");
					if (object != null) {
						stage = "Cutting "+object;
						Bot.clickObject(object.getId(), object.getX(), object.getY());
						Thread.sleep(3000);
					}
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
		g2d.drawString("Trent's Deforester", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		g2d.drawString("Oaks p/h: " + Bot.getFormattedLootPerHour(logsCut, 0, startTime), 15, 45);
		g2d.drawString("Wc xp p/h: " + Bot.getFormattedXpPerHour(Bot.WOODCUTTING, startXp, startTime), 15, 65);
		g2d.setFont(new Font("System", 1, 10));
		g2d.drawString("Stage: " + stage, 15, 95);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
