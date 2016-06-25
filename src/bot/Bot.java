package bot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import bot.scripts.Script;
import bot.task.TaskExecutor;
import bot.utils.Tile;
import bot.utils.Utils;
import bot.utils.WorldObject;

import com.Entity;
import com.InteractiveObject;
import com.Item;
import com.ItemDef;
import com.Jframe;
import com.NPC;
import com.NodeList;
import com.ObjectDef;
import com.RSInterface;
import com.Client;

public class Bot {
	public static Jframe clientt;
	public static Script currentScript = null;

	public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2, HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6, COOKING = 7, WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11, CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15,
			AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19, RUNECRAFTING = 20;

	public static RSInterface getInventory() {
		return RSInterface.interfaceCache[3214];
	}

	public static void main(String[] args) {
		Client.start(args);
	}

	public static void setClient(Jframe frame) {
		clientt = frame;
	}

	public static void addTask(Runnable r, int delay) {
		if (TaskExecutor.getEventExecutor().isShutdown())
			TaskExecutor.initializeEventExecutor();
		TaskExecutor.getEventExecutor().schedule(r, delay, TimeUnit.MILLISECONDS);
	}

	public static void addLogicTask(Runnable r, int delay) {
		TaskExecutor.getLogicExecutor().schedule(r, delay, TimeUnit.MILLISECONDS);
	}

	public static void addLoopingTask(Runnable r, int delay) {
		if (TaskExecutor.getEventExecutor().isShutdown())
			TaskExecutor.initializeEventExecutor();
		TaskExecutor.getEventExecutor().scheduleAtFixedRate(r, delay, delay, TimeUnit.MILLISECONDS);
	}

	public static void runSuperFastScript(Script r) {
		addLoopingTask(r, 2);
	}

	public static void runFastScript(Script r) {
		addLoopingTask(r, 75);
	}

	public static void runScript(Script r) {
		addLoopingTask(r, 600);
	}

	public static void chooseScript(String script, String... cmd) {
		try {
			currentScript = (Script) Class.forName("bot.scripts." + script).getConstructor().newInstance();
			currentScript.args = cmd;
			runScript(currentScript);
			System.out.println("Started script: " + script);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void clearQuestInterface() {
		for (int k : questInterface) {
			RSInterface.interfaceCache[k].message = "";
		}
	}

	public static int[] questInterface = { 8145, 8147, 8148, 8149, 8150, 8151, 8152, 8153, 8154, 8155, 8156, 8157, 8158, 8159, 8160, 8161, 8162, 8163, 8164, 8165, 8166, 8167, 8168, 8169, 8170, 8171, 8172, 8173, 8174, 8175, 8176, 8177, 8178, 8179, 8180,
			8181, 8182, 8183, 8184, 8185, 8186, 8187, 8188, 8189, 8190, 8191, 8192, 8193, 8194, 8195, 12174, 12175, 12176, 12177, 12178, 12179, 12180, 12181, 12182, 12183, 12184, 12185, 12186, 12187, 12188, 12189, 12190, 12191, 12192, 12193, 12194,
			12195, 12196, 12197, 12198, 12199, 12200, 12201, 12202, 12203, 12204, 12205, 12206, 12207, 12208, 12209, 12210, 12211, 12212, 12213, 12214, 12215, 12216, 12217, 12218, 12219, 12220, 12221, 12222, 12223 };

	public static int getNpcIndex(int id) {
		for (NPC n : clientt.getNPCs()) {
			if (n != null && id == n.idx) {
				return n.idx;
			}
		}
		return -1;
	}

	public static void clickNPC(String name, int option) {
		NPC i = getClosestNPC(name);
		if (i == null || i.idx < 0)
			return;
		clientt.doWalkTo(2, 0, 1, 0, Client.myPlayer.smallY[0], 1, 0, i.smallY[0], Client.myPlayer.smallX[0], false, i.smallX[0]);
		if (option == 1) {
			clientt.stream.createFrame(155);
			clientt.stream.method431(i.idx);
		} else if (option == 2) {
			clientt.stream.createFrame(17);
			clientt.stream.method433(i.idx);
		} else if (option == 3) {
			clientt.stream.createFrame(21);
			clientt.stream.writeWord(i.idx);
		}
		clientt.writeStream();
	}

	public static void clickNPC(int name, int option) {
		NPC i = getClosestNPC(name);
		if (i == null || i.idx < 0)
			return;
		clientt.doWalkTo(2, 0, 1, 0, Client.myPlayer.smallY[0], 1, 0, i.smallY[0], Client.myPlayer.smallX[0], false, i.smallX[0]);
		if (option == 1) {
			clientt.stream.createFrame(155);
			clientt.stream.method431(i.idx);
		} else if (option == 2) {
			clientt.stream.createFrame(17);
			clientt.stream.method433(i.idx);
		} else if (option == 3) {
			clientt.stream.createFrame(21);
			clientt.stream.writeWord(i.idx);
		}
		clientt.writeStream();
	}

	public static void attackNPC(String name) {
		NPC i = getClosestOutOfCombatNPC(name);
		if (i == null || i.idx < 0)
			return;
		clientt.doWalkTo(2, 0, 1, 0, Client.myPlayer.smallY[0], 1, 0, i.smallY[0], Client.myPlayer.smallX[0], false, i.smallX[0]);
		clientt.stream.createFrame(72);
		clientt.stream.method432(i.idx);
		clientt.writeStream();
	}

	public static void attackNPC(int id) {
		NPC i = getClosestOutOfCombatNPC(id);
		if (i == null || i.idx < 0)
			return;
		clientt.doWalkTo(2, 0, 1, 0, Client.myPlayer.smallY[0], 1, 0, i.smallY[0], Client.myPlayer.smallX[0], false, i.smallX[0]);
		clientt.stream.createFrame(72);
		clientt.stream.method432(i.idx);
		clientt.writeStream();
	}

	public static ArrayList<NPC> getFilteredNPCs(int id) {
		ArrayList<NPC> allNpcs = clientt.getNPCs();
		if (allNpcs != null) {
			ArrayList<NPC> filtered = new ArrayList<NPC>();
			for (NPC n : allNpcs) {
				if (n != null && n.desc.npcId == id)
					filtered.add(n);
			}
			return filtered;
		}
		return new ArrayList<NPC>();
	}

	public static ArrayList<NPC> getFilteredNPCs(String id) {
		ArrayList<NPC> allNpcs = clientt.getNPCs();
		if (allNpcs != null) {
			ArrayList<NPC> filtered = new ArrayList<NPC>();
			for (NPC n : allNpcs) {
				if (n != null && n.desc.name.equalsIgnoreCase(id))
					filtered.add(n);
			}
			return filtered;
		}
		return new ArrayList<NPC>();
	}

	public static NPC getClosestNPC(String id) {
		Map<Integer, NPC> distanceMap = new TreeMap<Integer, NPC>();
		ArrayList<NPC> objects = getFilteredNPCs(id);
		for (NPC object : objects) {
			if (object != null) {
				int distance = calculatePathDistance(object.x + clientt.baseX, object.y + clientt.baseY);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return (NPC) distanceMap.get(sortedKeys.get(0));
	}

	public static NPC getClosestNPC(int id) {
		Map<Integer, NPC> distanceMap = new TreeMap<Integer, NPC>();
		ArrayList<NPC> objects = getFilteredNPCs(id);
		for (NPC object : objects) {
			if (object != null) {
				int distance = calculatePathDistance(object.x + clientt.baseX, object.y + clientt.baseY);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return (NPC) distanceMap.get(sortedKeys.get(0));
	}

	public static NPC getClosestOutOfCombatNPC(String id) {
		Map<Integer, NPC> distanceMap = new TreeMap<Integer, NPC>();
		ArrayList<NPC> objects = getFilteredNPCs(id);
		for (NPC object : objects) {
			if (object != null && !inCombat(object)) {
				int distance = calculatePathDistance((object.x >> 7) + clientt.baseX, (object.y >> 7) + clientt.baseY);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return (NPC) distanceMap.get(sortedKeys.get(0));
	}

	public static NPC getClosestOutOfCombatNPC(int id) {
		Map<Integer, NPC> distanceMap = new TreeMap<Integer, NPC>();
		ArrayList<NPC> objects = getFilteredNPCs(id);
		for (NPC object : objects) {
			if (object != null && !inCombat(object)) {
				int distance = calculatePathDistance((object.x >> 7) + clientt.baseX, (object.y >> 7) + clientt.baseY);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return (NPC) distanceMap.get(sortedKeys.get(0));
	}

	public static int getNotInCombatNpcName(String name) {
		for (NPC n : clientt.getNPCs()) {
			if (n != null && (name.equalsIgnoreCase(n.desc.name)) && (!inCombat(n))) {
				return n.idx;
			}
		}
		return -1;
	}

	public static int getNotInCombatNpcIndex(int id) {
		for (NPC n : clientt.getNPCs()) {
			if (n != null && (n.desc.npcId == id) && (!inCombat(n))) {
				return n.idx;
			}
		}
		return -1;
	}

	public static boolean inCombat(Entity paramEntity) {
		if (paramEntity.loopCycleStatus > Client.loopCycle || paramEntity.interactingEntity != -1) {
			return true;
		}
		return false;
	}

	public static Tile getMyPlayerPos() {
		return new Tile(getX(Client.myPlayer), getY(Client.myPlayer), clientt.plane);
	}

	public static Tile getMyLocalPlayerPos() {
		return new Tile(Client.myPlayer.x >> 7, Client.myPlayer.y >> 7, clientt.plane);
	}

	public static int getX(Entity paramEntity) {
		return (paramEntity.x >> 7) + clientt.baseX;
	}

	public static int getY(Entity paramEntity) {
		return (paramEntity.y >> 7) + clientt.baseY;
	}

	public static int calculatePathDistance(WorldObject object) {
		// return clientt.findPathDistance(objectRotation, objectSizeY, objectType, startY, objectSizeX, targetSurrounding, endY, startX, flag, endX);
		return clientt.findPathDistance(object.getRotation(), object.getSizeY(), 10, Client.myPlayer.smallY[0], object.getSizeX(), 0, object.getY() - clientt.baseY, Client.myPlayer.smallX[0], false, object.getX() - clientt.baseX);
	}

	public static int calculatePathDistance(int x, int y) {
		// return clientt.findPathDistance(objectRotation, objectSizeY, objectType, startY, objectSizeX, targetSurrounding, endY, startX, flag, endX);
		return clientt.findPathDistance(0, 0, 0, Client.myPlayer.smallY[0], 0, 1, y - clientt.baseY, Client.myPlayer.smallX[0], false, x - clientt.baseX);
	}

	public static void walkTo(WorldObject object) {
		boolean flag1 = clientt.doWalkTo(2, object.getRotation(), object.getSizeY(), 10, Client.myPlayer.smallY[0], object.getSizeX(), 0, object.getY() - clientt.baseY, Client.myPlayer.smallX[0], false, object.getX() - clientt.baseX);
		if (!flag1)
			flag1 = clientt.doWalkTo(2, object.getRotation(), object.getSizeY(), 10, Client.myPlayer.smallY[0], object.getSizeX(), 0, object.getY() - clientt.baseY, Client.myPlayer.smallX[0], false, object.getX() - clientt.baseX);
	}

	public static void walkTo(int x, int y) {
		boolean flag1 = clientt.doWalkTo(2, 0, 0, 0, Client.myPlayer.smallY[0], 0, 0, y - clientt.baseY, Client.myPlayer.smallX[0], false, x - clientt.baseX);
		if (!flag1)
			flag1 = clientt.doWalkTo(2, 0, 1, 0, Client.myPlayer.smallY[0], 1, 0, y - clientt.baseY, Client.myPlayer.smallX[0], false, x - clientt.baseX);
	}

	public static void walkToLocal(int x, int y) {
		boolean flag1 = clientt.doWalkTo(2, 0, 0, 0, Client.myPlayer.smallY[0], 0, 0, y, Client.myPlayer.smallX[0], false, x);
		if (!flag1)
			flag1 = clientt.doWalkTo(2, 0, 1, 0, Client.myPlayer.smallY[0], 0, 0, y, Client.myPlayer.smallX[0], false, x);
	}

	public static void pickupDrop(int itemId, int x, int y) {
		walkTo(x, y);
		clientt.stream.createFrame(236);
		clientt.stream.method431(y);
		clientt.stream.writeWord(itemId);
		clientt.stream.method431(x);
		clientt.writeStream();
	}

	public static Tile findGroundItem(String id) {
		return findGroundItem(id, 10);
	}

	public static Tile findGroundItem(String id, int distance) {
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				NodeList groundItems = Client.groundArray[clientt.plane][x][y];
				if (groundItems != null) {
					for (Item item = (Item) groundItems.peekLast(); item != null; item = (Item) groundItems.reverseGetNext()) {
						if (!ItemDef.forID(item.ID).name.toLowerCase().contains(id.toLowerCase()))
							continue;
						if (Utils.distance(getMyLocalPlayerPos(), new Tile(x, y, 0)) > distance)
							continue;
						return new Tile(x + clientt.baseX, y + clientt.baseY, item.ID);
					}
				}
			}
		}
		return null;
	}

	public static Tile findGroundItem(int id) {
		return findGroundItem(id, 10);
	}

	public static Tile findGroundItem(int id, int distance) {
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				NodeList groundItems = Client.groundArray[clientt.plane][x][y];
				if (groundItems != null) {
					for (Item item = (Item) groundItems.peekLast(); item != null; item = (Item) groundItems.reverseGetNext()) {
						if (item.ID != id)
							continue;
						if (Utils.distance(getMyLocalPlayerPos(), new Tile(x, y, 0)) > distance)
							continue;
						return new Tile(x + clientt.baseX, y + clientt.baseY, clientt.plane);
					}
				}
			}
		}
		return null;
	}

	public static int getMyHealth() {
		return Client.myPlayer.currentHealth;
	}

	public static double getMyHealthPercent() {
		if (Client.myPlayer.maxHealth == 0)
			return 100;
		return (Client.myPlayer.currentHealth / Client.myPlayer.maxHealth) * 100;
	}

	public static void clickClosestWorldObject(int name, String action) {
		clickWorldObject(getClosestWorldObject(name, action));
	}

	public static void clickClosestWorldObject(String name, String action) {
		clickWorldObject(getClosestWorldObject(name, action));
	}

	public static void clickClosestWorldObject(int name) {
		clickWorldObject(getClosestWorldObject(name));
	}

	public static void clickClosestWorldObject(String name) {
		clickWorldObject(getClosestWorldObject(name));
	}

	public static void clickWorldObject(WorldObject object) {
		if (object != null)
			Bot.clickObject(object);
	}

	public static boolean isAnimating() {
		return Client.myPlayer.anim != -1;
	}

	public static WorldObject getClosestWorldObject(String id) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		ArrayList<WorldObject> objects = getObjectsNearby(id);
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = calculatePathDistance(object);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return (WorldObject) distanceMap.get(sortedKeys.get(0));
	}

	public static WorldObject getClosestWorldObject(int id) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		ArrayList<WorldObject> objects = getObjectsNearby(id);
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = calculatePathDistance(object);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return (WorldObject) distanceMap.get(sortedKeys.get(0));
	}

	public static WorldObject getClosestWorldObject(String id, String option) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		ArrayList<WorldObject> objects = getObjectsNearby(id, option);
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = calculatePathDistance(object);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return (WorldObject) distanceMap.get(sortedKeys.get(0));
	}

	public static WorldObject getClosestWorldObject(int id, String option) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		ArrayList<WorldObject> objects = getObjectsNearby(id, option);
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = calculatePathDistance(object);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return (WorldObject) distanceMap.get(sortedKeys.get(0));
	}

	public static ArrayList<WorldObject> getObjectsNearby(int id, String option) {
		ArrayList<WorldObject> objects = new ArrayList<WorldObject>();
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				InteractiveObject obj = clientt.worldController.getInteractiveObject(x, y, clientt.plane);
				if (obj == null || id != (obj.uid >> 14 & 0x7fff))
					continue;
				ObjectDef def = ObjectDef.forID(obj.uid >> 14 & 0x7fff);
				if (def.containsOption(option))
					objects.add(new WorldObject(obj.uid >> 14 & 0x7fff, obj.rotation, def.sizeX, def.sizeY, x + clientt.baseX, y + clientt.baseY));
			}
		}
		return objects;
	}

	public static ArrayList<WorldObject> getObjectsNearby(String id, String option) {
		ArrayList<WorldObject> objects = new ArrayList<WorldObject>();
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				InteractiveObject obj = clientt.worldController.getInteractiveObject(x, y, clientt.plane);
				if (obj == null)
					continue;
				ObjectDef def = ObjectDef.forID(obj.uid >> 14 & 0x7fff);
				if (def != null && !def.name.equalsIgnoreCase(id))
					continue;
				if (def.containsOption(option))
					objects.add(new WorldObject(obj.uid >> 14 & 0x7fff, obj.rotation, def.sizeX, def.sizeY, x + clientt.baseX, y + clientt.baseY));
			}
		}
		return objects;
	}

	public static ArrayList<WorldObject> getObjectsNearby(int id) {
		ArrayList<WorldObject> objects = new ArrayList<WorldObject>();
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				InteractiveObject obj = clientt.worldController.getInteractiveObject(x, y, clientt.plane);
				if (obj == null || id != (obj.uid >> 14 & 0x7fff))
					continue;
				ObjectDef def = ObjectDef.forID(obj.uid >> 14 & 0x7fff);
				objects.add(new WorldObject(obj.uid >> 14 & 0x7fff, obj.rotation, def.sizeX, def.sizeY, x + clientt.baseX, y + clientt.baseY));
			}
		}
		return objects;
	}

	public static ArrayList<WorldObject> getObjectsNearby(String id) {
		ArrayList<WorldObject> objects = new ArrayList<WorldObject>();
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				InteractiveObject obj = clientt.worldController.getInteractiveObject(x, y, clientt.plane);
				if (obj == null)
					continue;
				ObjectDef def = ObjectDef.forID(obj.uid >> 14 & 0x7fff);
				if (def != null && !def.name.equalsIgnoreCase(id))
					continue;
				objects.add(new WorldObject(obj.uid >> 14 & 0x7fff, obj.rotation, def.sizeX, def.sizeY, x + clientt.baseX, y + clientt.baseY));
			}
		}
		return objects;
	}

	public static String getScriptTime(long currTime, long startTime) {
		long ms = currTime - startTime;
		long totalSecs = ms / 1000;
		long hours = (totalSecs / 3600);
		long mins = (totalSecs / 60) % 60;
		long secs = totalSecs % 60;
		String hoursString = (hours == 0) ? "00" : ((hours < 10) ? "0" + hours : "" + hours);
		String minsString = (mins == 0) ? "00" : ((mins < 10) ? "0" + mins : "" + mins);
		String secsString = (secs == 0) ? "00" : ((secs < 10) ? "0" + secs : "" + secs);

		return hoursString + ":" + minsString + ":" + secsString;

	}
	
	public static void itemOnItem(int item1, int item2) {
		itemOnItem(item1, item2, getInventory().getSlotByItem(item1), getInventory().getSlotByItem(item2));
	}

	public static void itemOnItem(int primaryItemID, int secondaryItemID, int primarySlotID, int secondarySlotID) {
		clientt.stream.createFrame(53);
		clientt.stream.writeWord(secondarySlotID); // Item slot (secondary item)
		clientt.stream.method432(primarySlotID); // Item slot (primary item)
		clientt.stream.method433(secondaryItemID); // Item ID (secondary item)
		clientt.stream.writeWord(3214); // Inventory interface
		clientt.stream.method431(primaryItemID); // Item ID (primary item)
		clientt.stream.writeWord(3214); // Inventory Interface
		clientt.writeStream();
	}

	public static void itemOnObject(int itemId, WorldObject object) {
		walkTo(object);
		clientt.stream.createFrame(192);
		clientt.stream.writeWord(3214);
		clientt.stream.method431(object.getId());
		clientt.stream.method433(object.getY());
		clientt.stream.method431(Bot.getInventory().getSlotByItem(itemId));
		clientt.stream.method433(object.getX());
		clientt.stream.writeWord(itemId);
		clientt.writeStream();
	}

	public static void itemOnObject(int itemId, int objectId, int x, int y) {
		walkTo(x, y);
		clientt.stream.createFrame(192);
		clientt.stream.writeWord(3214);
		clientt.stream.method431(objectId);
		clientt.stream.method433(y);
		clientt.stream.method431(Bot.getInventory().getSlotByItem(itemId));
		clientt.stream.method433(x);
		clientt.stream.writeWord(itemId);
		clientt.writeStream();
	}

	public static void clickObject(int objectId, int x, int y) {
		walkTo(x, y);
		clientt.stream.createFrame(132);
		clientt.stream.method433(x);
		clientt.stream.writeWord(objectId);
		clientt.stream.method432(y);
		clientt.writeStream();
	}

	private static void clickObject(WorldObject object) {
		walkTo(object);
		clientt.stream.createFrame(132);
		clientt.stream.method433(object.getX());
		clientt.stream.writeWord(object.getId());
		clientt.stream.method432(object.getY());
		clientt.writeStream();
	}

	public static boolean myPlayerInCombat() {
		return inCombat(Client.myPlayer);
	}

	public static void clickItem(int itemId) {
		clickItem(itemId, getInventory().getSlotByItem(itemId));
	}

	public static void clickItem(int itemId, int slotId) {
		clientt.stream.createFrame(122);
		clientt.stream.method433(3214);
		clientt.stream.method432(slotId);
		clientt.stream.method431(itemId);
		clientt.writeStream();
	}

	public static int getXp(int idx) {
		return clientt.currentExp[idx];
	}

	public static void depositItem(int id) {
		depositItem(id, getInventory().getSlotByItem(id));
	}

	public static void depositAllBySlot(int slot) {
		depositItem(getInventory().getItem(slot), slot);
	}

	public static void findAndPickupItems(String... items) throws InterruptedException {
		for (int i = 0; i < items.length; i++) {
			while (Bot.findGroundItem(items[i]) != null) {
				if (Bot.getInventory().freeSlots() <= 0)
					break;
				Tile drop = Bot.findGroundItem(items[i]);
				Bot.pickupDrop(drop.getZ(), drop.getX(), drop.getY());
				Thread.sleep(1000);
			}
		}
	}

	public static void findAndPickupItems(int distance, String... items) throws InterruptedException {
		for (int i = 0; i < items.length; i++) {
			while (Bot.findGroundItem(items[i], distance) != null) {
				if (Bot.getInventory().freeSlots() <= 0)
					break;
				Tile drop = Bot.findGroundItem(items[i], distance);
				Bot.pickupDrop(drop.getZ(), drop.getX(), drop.getY());
				Thread.sleep(1000);
			}
		}
	}

	public static void findAndPickupItems(int... items) throws InterruptedException {
		for (int i = 0; i < items.length; i++) {
			while (Bot.findGroundItem(items[i]) != null) {
				if (Bot.getInventory().freeSlots() <= 0)
					break;
				Tile drop = Bot.findGroundItem(items[i]);
				Bot.pickupDrop(items[i], drop.getX(), drop.getY());
				Thread.sleep(1000);
			}
		}
	}

	public static void depositItem(int paramInt1, int paramInt2) {
		clientt.stream.createFrame(129);
		clientt.stream.method432(paramInt2);
		clientt.stream.writeWord(5064);
		clientt.stream.method432(paramInt1);
		clientt.writeStream();
	}

	public static void widthdrawItem10(int paramInt1, int paramInt2) {
		clientt.stream.createFrame(43);
		clientt.stream.method431(5382);
		clientt.stream.method432(paramInt1);
		clientt.stream.method432(paramInt2);
		clientt.writeStream();
	}

	public static void sendInfoMessage(String message) {
		clientt.pushMessage(message, 5, "");
	}

	public static boolean processCommand(String string) {
		String[] cmd = string.split(" ");
		if (cmd[0].equals("startscript")) {
			String[] args = new String[10];
			for (int i = 2; i < cmd.length; i++) {
				args[i - 1] = cmd[i].replace("_", " ");
			}
			chooseScript(cmd[1], args);
			return true;
		} else if (cmd[0].startsWith("walktoo")) {
			WorldObject o = getClosestWorldObject(cmd[1].replace("_", " "));
			if (o != null)
				walkTo(o);
			else
				sendInfoMessage("No object found.");
		} else if (cmd[0].startsWith("mypos")) {
			sendInfoMessage(getMyPlayerPos().toString());
		} else if (cmd[0].startsWith("invinfo")) {
			sendInfoMessage("" + Bot.getInventory().freeSlots());
			sendInfoMessage("" + Bot.getInventory().numberOf(314));
			sendInfoMessage("" + Bot.getInventory().getItem(1));
		} else if (cmd[0].startsWith("stop")) {
			TaskExecutor.getEventExecutor().shutdownNow();
			currentScript = null;
			return true;
		} else if (cmd[0].startsWith("gitem")) {
			Tile item = findGroundItem(Integer.valueOf(cmd[1]));
			if (item != null)
				sendInfoMessage("" + item.toString());
			else
				sendInfoMessage("No items found. " + Integer.valueOf(cmd[1]));
			return true;
		} else if (cmd[0].startsWith("fobject")) {
			WorldObject item = Bot.getClosestWorldObject(cmd[1].replace("_", " "));
			if (item != null)
				sendInfoMessage("" + item.toString() + ", distance: " + calculatePathDistance(item.getX(), item.getY()));
			else
				sendInfoMessage("No objects found. " + Integer.valueOf(cmd[1]));
			return true;
		}
		return false;
	}

	public static long millisElapsed(long startTime) {
		return System.currentTimeMillis() - startTime;
	}

	public static float secondsElapsed(long startTime) {
		return millisElapsed(startTime) / 1000;
	}

	public static float minutesElapsed(long startTime) {
		return secondsElapsed(startTime) / 60;
	}

	public static float hoursElapsed(long startTime) {
		return minutesElapsed(startTime) / 60;
	}

	public static int getXpPerHour(int skillId, int startingXp, long startTime) {
		return (int) ((Bot.getXp(skillId) - startingXp) / hoursElapsed(startTime));
	}

	public static String getFormattedXpPerHour(int skillId, int startingXp, long startTime) {
		return format(getXpPerHour(skillId, startingXp, startTime));
	}

	public static int getLootPerHour(int itemAmount, int startingAmount, long startTime) {

		return (int) ((itemAmount - startingAmount) / hoursElapsed(startTime));
	}

	public static String getFormattedLootPerHour(int itemAmount, int startingAmount, long startTime) {
		return format(getLootPerHour(itemAmount, startingAmount, startTime));
	}

	public static String format(int number) {
		return NumberFormat.getNumberInstance(Locale.US).format(number);
	}
}