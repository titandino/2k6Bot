package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.NPC;

import bot.Bot;
import bot.utils.Tile;
import bot.utils.WorldObject;

public class DuelMagics extends Script {

	long startTime;
	int startXp;

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.WOODCUTTING);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			NPC spirit = Bot.getClosestNPCNoClip("tree spirit");
			if (spirit != null) {
				Bot.walkTo(new Tile(3382, 3269));
				Thread.sleep(400);
			} else {
				if (!Bot.isAnimating()) {
					if (Bot.getInventory().freeSlots() <= 0) {
						stage = "Banking";
						NPC banker = Bot.getClosestNPCNoClip(958);
						if (banker == null) {
							Bot.walkTo(new Tile(3382, 3269));
							Thread.sleep(1000);
						} else {
							Bot.clickClosestWorldObject(3193);
							Thread.sleep(1000);
							Bot.clickWorldObject(new WorldObject(3194, 3381, 3269), 2);
							Thread.sleep(1000);
							Bot.depositAllBySlot(1);
							Thread.sleep(1000);	
						}
					} else {
						stage = "chainsaw.exe";
						Bot.clickClosestWorldObject("magic tree");
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
		g2d.drawString("Trent's Magics", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		//g2d.drawString("Logs p/h: " + Bot.getFormattedLootPerHour(Bot.getBank().numberOf("log"), startLogs, startTime), 15, 40);
		g2d.drawString("Wc xp p/h: " + Bot.getFormattedXpPerHour(Bot.WOODCUTTING, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g2d.setFont(new Font("System", 1, 10));
		g2d.drawString("Stage: " + stage, 15, 95);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
