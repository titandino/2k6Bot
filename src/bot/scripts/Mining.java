package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import bot.utils.*;

import com.Client;
import com.NPC;

import bot.Bot;

public class Mining extends Script {

	long startTime;
	int startXp;
	int oresMined;
	int[] rocks = new int[3];
	Area yanille = new Area(new Tile(2624, 3129, 0), new Tile(2650, 3153, 0));
	Area shilo = new Area(new Tile(2817, 2994, 0), new Tile(2828, 3005, 0));

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		oresMined = 0;
		startXp = Bot.getXp(Bot.MINING);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			rocks[1] = Integer.valueOf(args[1].replace("_", " "));
			if (args[2] != null)
				rocks[2] = Integer.valueOf(args[2].replace("_", " "));
			if (args[3] != null)
				rocks[3] = Integer.valueOf(args[2].replace("_", " "));
			
			NPC golem = Bot.getClosestNPCNoClip("rock golem");
			if (golem != null) {
				Bot.walkTo(2618, 3107);
				Thread.sleep(400);
			}
			else if (!Bot.isAnimating()) {
				if (Bot.getInventory().freeSlots() <= 0) {
					while (yanille.within(Bot.getMyPlayerPos())) {
						Bot.walkTo(2618, 3107);
						Thread.sleep(5000);
					} 
					while (shilo.within(Bot.getMyPlayerPos())) {
						Bot.walkTo(2840, 2965);
						Thread.sleep(5000);
					}
					
					stage = "Banking";
						if (Bot.getClosestWorldObject("bank booth") != null) {
							Bot.clickClosestWorldObject("bank booth", "use");
							Thread.sleep(3000);
						}
						else {
							Bot.clickNPCNoClip("banker", 2);
							Thread.sleep(3000);
						}
					Bot.bankAll();
					oresMined += 27;
					
				} else {
					stage = "Hulk Smashing";
					Bot.clickClosestWorldObject(rocks, 0);
					Thread.sleep(3000);
					}
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
		g2d.drawString("Devin's Ass Smasher	", 15, 15);
		g2d.setFont(new Font("Arial", 1, 12));
		g2d.drawString("Ores p/h: " + Bot.getFormattedLootPerHour(oresMined, 0, startTime), 15, 35);
		g2d.drawString("Mining xp p/h: " + Bot.getFormattedXpPerHour(Bot.MINING, startXp, startTime), 15, 47);
		g2d.setColor(Color.GREEN);
		g2d.drawString("Time Running: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 62);
		g2d.drawString("Stage: " + stage, 15, 74);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
