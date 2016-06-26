package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import bot.utils.*;

import com.Client;

import bot.Bot;

public class Mining extends Script {

	long startTime;
	int startXp;
	int oresMined;
	Area mine = new Area(new Tile(2624, 3129, 0), new Tile(2650, 3153, 0));

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
			if (Client.myPlayer.anim == -1) {
				if (Bot.getInventory().freeSlots() <= 0) {
					while (mine.within(Bot.getMyPlayerPos())) {
						Bot.walkTo(2618, 3107);
						Thread.sleep(5000);
					} 
					if (!mine.within(Bot.getMyPlayerPos())){
					stage = "Banking";
					Bot.clickClosestWorldObject("bank booth", "use");
					Thread.sleep(3000);
					Bot.depositAllBySlot(1);
					oresMined += 27;
					}
				} else {
					stage = "Hulk Smashing";
					Bot.clickClosestWorldObject(Integer.valueOf(args[1].replace("_", " ")), "mine");
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
