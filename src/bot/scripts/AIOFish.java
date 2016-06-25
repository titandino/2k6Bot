package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.NPC;

import bot.Bot;
import bot.utils.Tile;

public class AIOFish extends Script {

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
			if (!Bot.isAnimating()) {
				if (Bot.getInventory().freeSlots() <= 0) {
					stage = "Banking";
					Bot.clickClosestWorldObject("bank booth", "use");
					Thread.sleep(3000);
					Bot.depositAllBySlot(1);
					Thread.sleep(1000);
				} else {
					stage = "avid_angler.exe";
					NPC spot = Bot.getClosestNPCNoClip(Integer.valueOf(args[1]));
					if (spot == null)
						Bot.walkTo(fishTile);
					Bot.clickNPCNoClip(Integer.valueOf(args[1]), args[2] != null ? Integer.valueOf(args[2]) : 1);
					Thread.sleep(3000);
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
