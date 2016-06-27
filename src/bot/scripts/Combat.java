package bot.scripts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import bot.Bot;

public class Combat extends Script {

	Stage currentStage = Stage.STARTING;
	
	public static HashMap<String, String[]> LOOT = new HashMap<String, String[]>();
	
	int[] food = new int[] { 361, 373, 379, 385 };
	
	static {
		LOOT.put("chaos druid", new String[] {"nature rune", "law rune", "herb", "lantadyme", "air rune", "dragon"});
		LOOT.put("hill giant", new String[] {"law rune", "bones", "cosmic rune"});
		LOOT.put("guard", new String[] {"rune", "arrow", "grapes", "bones"});
		LOOT.put("chicken", new String[] {"feather", "bones"});
	}

	int startAtk;
	int startStr;
	int startDef;
	int startPray;
	long timeStarted;

	String stage = "Starting";

	private enum Stage {
		STARTING, KILLING, LOOTING;
	}

	@Override
	public boolean onStart() {
		startAtk = Bot.clientt.currentExp[0];
		startDef = Bot.clientt.currentExp[1];
		startStr = Bot.clientt.currentExp[2];
		startPray = Bot.clientt.currentExp[5];
		timeStarted = System.currentTimeMillis();
		return true;
	}

	@Override
	public void run() {
		super.run();
		try {
			if (hasFood() != -1) {
				if (Bot.getHealthPercent() < 50.0) {
					Bot.clickItem(hasFood());
					Thread.sleep(1000);
				}
			}
			if (Bot.getHealthPercent() < 25.0) {
				Bot.clickButton(1174);
				Thread.sleep(1000);
			}
			if (Bot.getInventory().freeSlots() == 0) {
				stage = "Banking";
				Bot.clickClosestWorldObject("bank booth");
				Thread.sleep(1000);
				Bot.bankAll();
			} else {
				String[] loot = LOOT.get(args[1].toLowerCase().replace("_", " "));
				if (loot != null)
					Bot.findAndPickupItems(loot);
				buryBones();

				if (!Bot.myPlayerInCombat()) {
					stage = "Attacking "+args[1];
					Bot.attackNPC(args[1].replace("_", " "));
					Thread.sleep(3000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int hasFood() {
		for (int i = 0;i < food.length;i++) {
			if (Bot.getInventory().contains(food[i], 1))
				return food[i];
		}
		return -1;
	}

	public void buryBones() throws InterruptedException {
		if (Bot.getInventory().contains(526, 1)) {
			Bot.clickItem(526);
			Thread.sleep(1000);
		}
		if (Bot.getInventory().contains(532, 1)) {
			Bot.clickItem(532);
			Thread.sleep(1000);
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
		g2d.drawString("Trent's Combat", 15, 19);
		g2d.setFont(new Font("System", 1, 12));
		g2d.drawString("Attack p/h: " + Bot.getFormattedXpPerHour(Bot.ATTACK, startAtk, timeStarted), 15, 39);
		g2d.drawString("Strength p/h: " + Bot.getFormattedXpPerHour(Bot.STRENGTH, startStr, timeStarted), 15, 51);
		g2d.drawString("Defence p/h: " + Bot.getFormattedXpPerHour(Bot.DEFENCE, startDef, timeStarted), 15, 63);
		g2d.drawString("Prayer p/h: " + Bot.getFormattedXpPerHour(Bot.PRAYER, startPray, timeStarted), 15, 75);
		g2d.drawString("Stage: " + stage, 15, 87);
		g2d.drawString("Health: " + Bot.getLevel(Bot.HITPOINTS)+"/"+Bot.getLevelForXp(Bot.HITPOINTS)+" ("+(int) Bot.getHealthPercent()+"%)", 15, 99);
		g.drawImage(bufferedImage, 0, 0, null);
	}
}