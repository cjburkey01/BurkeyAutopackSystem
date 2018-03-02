package com.cjburkey.autopacker.update;

import com.cjburkey.autopacker.update.gui.FrameConsole;
import com.cjburkey.autopacker.update.gui.FrameProgress;

public class UpdateGUIManager {
	
	private static FrameConsole loadingWindow;
	private static FrameProgress progressDisplay;
	
	public static final void showLoadingWindow() {
		if (loadingWindow == null) {
			loadingWindow = new FrameConsole();
		}
		loadingWindow.display();
	}
	
	public static final void hideLoadingWindow() {
		if (loadingWindow != null) {
			loadingWindow.destroy();
		}
	}
	
	public static final void showProgressWindow() {
		if (progressDisplay == null) {
			progressDisplay = new FrameProgress();
		}
		progressDisplay.display();
	}
	
	public static final void hideProgressWindow() {
		if (progressDisplay != null) {
			progressDisplay.destroy();
		}
	}
	
	public static final void show(String msg) {
		if (loadingWindow != null) {
			loadingWindow.println(msg);
		}
	}
	
	public static final FrameProgress getProgressFrame() {
		return progressDisplay;
	}
	
}