package bot.scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.Client;

import bot.Bot;

public class AIOWoodcut extends Script {

	long startTime;
	int startXp;
	int startLogs;

	String stage = "Starting";

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startXp = Bot.getXp(Bot.WOODCUTTING);
		startLogs = Bot.getBank().numberOf("log");
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (Client.myPlayer.anim == -1) {
				if (Bot.getInventory().freeSlots() <= 0) {
					stage = "Banking";
					Bot.clickClosestWorldObject("bank booth", "use");
					Thread.sleep(3000);
					Bot.depositAllBySlot(1);
				} else {
					stage = "chainsaw.exe";
					Bot.clickClosestWorldObject(args[1].replace("_", " "), "chop down");
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
		g2d.drawString("Trent's AIO WC", 15, 20);
		g2d.setFont(new Font("System", 1, 16));
		//g2d.drawString("Logs p/h: " + Bot.getFormattedLootPerHour(Bot.getBank().numberOf("log"), startLogs, startTime), 15, 40);
		g2d.drawString("Wc xp p/h: " + Bot.getFormattedXpPerHour(Bot.WOODCUTTING, startXp, startTime), 15, 60);
		g2d.drawString("Time: " + Bot.getScriptTime(System.currentTimeMillis(), startTime), 15, 80);
		g2d.setFont(new Font("System", 1, 10));
		g2d.drawString("Stage: " + stage, 15, 95);
		g.drawImage(bufferedImage, 0, 0, null);
	}

}
