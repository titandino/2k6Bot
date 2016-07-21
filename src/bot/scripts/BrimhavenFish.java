package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.NPC;

import bot.Bot;
import bot.utils.Tile;

public class BrimhavenFish extends Script {

	long startTime;
	int startXp;
	int startFish;
	Tile fishTile;

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.FISHING);
		startFish = Bot.getBank().numberOf("raw");
		fishTile = Bot.getMyPlayerPos();
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			NPC spirit = Bot.getClosestNPCNoClip("river troll");
			if (spirit != null) {
				Bot.clickNearestBank();
				Thread.sleep(400);
			} 
			else {
				if (!Bot.isAnimating()) {
					if (Bot.getInventory().freeSlots() <= 0) {
						stage = "Banking";
						if (Bot.clickNearestBank()) {
							Thread.sleep(3000);
							Bot.bankAll(301, 303, 305, 307, 309, 311, 314);
							Thread.sleep(1000);
						} else {
							Bot.walkTo(Bot.getMyPlayerPos().translate(0, -10));
						}
					} else {
						stage = "avid_angler.exe";
						NPC spot = Bot.getClosestNPCNoClip(309);
						if (spot == null)
							Bot.walkTo(fishTile);
						Bot.clickNPCNoClip(309, args[1] != null ? Integer.valueOf(args[1]) : 1);
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
		g2d.drawString("Trent's AIO Fish", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		g2d.drawString("Fish p/h: " + Bot.getFormattedLootPerHour(Bot.getBank().numberOf("raw"), startFish, startTime), 15, 40);
		g2d.drawString("Fish xp p/h: " + Bot.getFormattedXpPerHour(Bot.FISHING, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g2d.setFont(new Font("System", 1, 10));
		g2d.drawString("Stage: " + stage, 15, 95);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
