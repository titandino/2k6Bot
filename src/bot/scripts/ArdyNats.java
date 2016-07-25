package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;
import bot.utils.WorldObject;

public class ArdyNats extends Script {

	long startTime;
	int startXp;
	int startNats;
	int[] chests = { 2568, 2567 };

	int[] food = new int[] { 379, 361, 373, 385 };

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
			if (hasFood() != -1) {
				if (Bot.clientt.plane != 0) {
					stage = "Wickeding those nats";
					if (Bot.getHealthPercent() < 50.0) {
						Bot.clickItem(hasFood());
						Thread.sleep(1000);
					}
					if (!Bot.isAnimating()) {
						if (Bot.getClosestWorldObject(2567) != null) {
							Bot.clickObject(new WorldObject(2567, 2671, 3301), 2);
							Thread.sleep(1500);
						}
						if (Bot.getClosestWorldObject(2568) != null) {
							Bot.clickObject(new WorldObject(2568, 2671, 3299), 2);
							Thread.sleep(1500);
						}
					}
				} else {
					stage = "Walking upstairs";
					Bot.clickWorldObject(new WorldObject(1738, 2673, 3300));
					Thread.sleep(1000);
				}
			} else {
				if (Bot.clientt.plane != 0) {
					stage = "Walking downstairs";
					Bot.clickWorldObject(new WorldObject(1740, 2674, 3300));
					Thread.sleep(1000);
				} else {
					stage = "Banking";
					Bot.clickNearestBank();
					Thread.sleep(1000);
					int foodId = -1;
					for (int i = 0;i < food.length;i++) {
						if (Bot.getBank().contains(food[i], 1)) {
							foodId = food[i];
						}
					}
					Bot.withdrawItem(foodId, Bot.getBank().getSlotByItem(foodId));
				}
			}
		} catch (Exception e) {

		}
	}

	public int hasFood() {
		for (int i = 0; i < food.length; i++) {
			if (Bot.getInventory().contains(food[i], 1))
				return food[i];
		}
		return -1;
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
