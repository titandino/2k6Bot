package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bot.Bot;
import bot.utils.Area;
import bot.utils.Tile;

public class FlaxPicker extends Script {

	long startTime;
	int flaxPicked;
	int startFlax;
	Area Flax = new Area(new Tile(2700, 3400, 0), new Tile(2751, 3451, 0));

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startFlax = Bot.getBank().numberOf(1779);
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (!Bot.isAnimating()) {
				if (Bot.getInventory().freeSlots() <= 0) {
					while (Flax.within(Bot.getMyPlayerPos())) {
						Bot.walkTo(2724, 3475);
						Thread.sleep(5000);
					} 
					if (!Flax.within(Bot.getMyPlayerPos())){
					stage = "Banking";
					Bot.clickNearestBank();
					Thread.sleep(3000);
					Bot.depositAllBySlot(1);
					flaxPicked = Bot.getBank().numberOf(1779) - startFlax;
					}
				} else {
					stage = "Picking Cotton";
					Bot.clickClosestWorldObject("flax", 2);
					Thread.sleep(1000);
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
		g2d.drawString("Devin's Nig Picker", 15, 15);
		g2d.setFont(new Font("Arial", 1, 12));
		g2d.drawString("Flax p/h: " + Bot.getFormattedLootPerHour(flaxPicked, 0, startTime), 15, 35);
		g2d.drawString("Total Flax: " + Bot.getBank().numberOf(1779), 15, 47);
		g2d.setColor(Color.GREEN);
		g2d.drawString("Time Running: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 62);
		g2d.drawString("Stage: " + stage, 15, 74);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
