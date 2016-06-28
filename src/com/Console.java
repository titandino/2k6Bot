package com;

import java.text.DateFormat;
import java.util.Date;

import bot.Bot;

public class Console {
	
	public int messageId;
	public String input;
	public final String[] messageHistory;
	public boolean isOpen;
	public final String defaultMessage;
	public boolean scroller;
	public int scrollIndex;
	public int scrollPos;
	public int scrollOffset;
	
	

	public String getTimeStamp() {
		String stamp = currentTime().replaceAll("AM", "").replaceAll("PM", "");
		return stamp;
	}
	
	public Console() {
		messageId = 1;
		input = "";
		messageHistory = new String[500];
		isOpen = false;
		defaultMessage = "This is the developer console. To close, press the ` key on your keyboard.";
		scroller = false;
	}

	public boolean handleOpenConsole(int key) {
		if (key == 96) {
			isOpen = !isOpen;
			return true;
		}
		return false;
	}

	public void printMessage(String message, int id) {
		if (getClient().backDialogID == -1) {
			Client.inputTaken = true;
		}

		for (int line = 499; line > 0; line--)
			messageHistory[line] = messageHistory[line - 1];

		messageId++;
		messageHistory[0] = getTimeStamp() + ": " + (id == 0 ? "--> " : "") + message;
	}

	public void handleInput(int j) {
		if (j == 8 && input.length() > 0 && input.length() <= 40)
			input = input.substring(0, input.length() - 1);

		if (j >= 32 && j <= 122 && input.length() < 40)
			input += (char) j;

		if ((j == 13 || j == 10) && input.length() > 0 && input.length() <= 40) {
			Bot.processCommand(input);
			printMessage(input, 0);
			input = "";
			Client.inputTaken = true;
		}
		if ((j == 9 || j == 11) && input.length() > 0 && input.length() <= 40) {
			Bot.processCommand(input);
			printMessage(input, 0);
			Client.inputTaken = true;
		}
	}

	public static String currentTime() {
		DateFormat date = DateFormat.getTimeInstance();
		return date.format(new Date());
	}

	public void drawConsole() {
		if (isOpen) {
			DrawingArea.drawAlphaFilledPixels(5000, 0, 512, 200, 0xFFFFFF, 0);
			DrawingArea.drawPixels(1, 200, 0, 0xFFFFFF, 512);
			getClient().smallText.drawText(0, "--> " + input + (Client.loopCycle % 20 < 10 ? "|" : ""), 200, 10);
			getClient().smallText.drawText(1, "Build: 317", 200, 460);// 505, 312 above the divider
		}
		drawMessages();
	}

	public void drawMessages() {
		if (isOpen) {
			if (messageId == 1) {
				printMessage(defaultMessage, 1);
				return;
			}
			int output_y = 0;
			int y_pos = 0;
			scrollOffset = 0;
			scrollPos = getClient().scrollbar_position;
			DrawingArea.setDrawingArea(195, 0, 510, 21);
			for (int line = 0; line < 500; line++) {
				y_pos = (185 - output_y * 16) + scrollPos;
				if (messageHistory[line] != null) {
					scrollIndex = line - 1;
					scroller = (scrollIndex - 1 > 14 ? true : false);
					getClient().smallText.drawText(0, messageHistory[line], y_pos, 0);
					scrollOffset++;
					output_y++;
				}
			}
			if (scroller)
				getClient().draw_scrollbar(494, 22, 0, 165, scrollOffset, 18, 17, 0);

			DrawingArea.setDrawingArea(512, 0, 334, 0);
		}
	}

	public static Client getClient() {
		return Bot.clientt;
	}

}