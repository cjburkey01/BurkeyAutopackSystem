package com.cjburkey.autopacker.update;

import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.alee.utils.SwingUtils;
import com.cjburkey.autopacker.BurkeyAutopackSystem;
import com.cjburkey.autopacker.local.LocalVersion;
import com.cjburkey.autopacker.update.core.XMLUpdateSystem;
import com.cjburkey.autopacker.update.gui.LaF;

public class InitUpdateSystem {
	
	private static boolean done = false;
	private static boolean loaded = false;
	
	private static final Thread guiThread = new Thread(() -> SwingUtils.invokeLater(() -> {
		BurkeyAutopackSystem.log("Loading gui...");
		loadLookAndFeel();
		UpdateGUIManager.showLoadingWindow();
		loaded = true;
	}));
	
	private static final Thread mainThread = new Thread(() -> {
		try {
			waitForLoad();
			if (done) {
				return;
			}
			BurkeyAutopackSystem.log("Loaded gui.");
			XMLUpdateSystem.init();
			if (XMLUpdateSystem.loaded) {
				loaded = false;
				new Thread(() -> SwingUtils.invokeLater(() -> {
					UpdateGUIManager.showProgressWindow();
					loaded = true;
				})).start();
				waitForLoad();
				Updater.performUpdates(XMLUpdateSystem.updates);
				UpdateGUIManager.hideProgressWindow();
				BurkeyAutopackSystem.log("The updater has finished, writing local data...");
				LocalVersion.writeLocalPackInformation(XMLUpdateSystem.getNewData());
				BurkeyAutopackSystem.log("Wrote local data.");
				SwingUtils.invokeLater(() -> {
					JOptionPane.showMessageDialog(UpdateGUIManager.getProgressFrame(), "The update(s) for your modpack have been successfully installed. Your game will close automatically to apply the changes.\nThe main game window may show before the game closes, this is completely normal.", "Success!", JOptionPane.INFORMATION_MESSAGE);
					done = true;
				});
				BurkeyAutopackSystem.NEED_SHUTDOWN = true;
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			done = true;
			return;
		}
	});
	
	private static final void waitForLoad() throws InterruptedException {
		while (!loaded) {
			Thread.sleep(500);
		}
	}
	
	public static final void onLoad() {
		guiThread.start();
		mainThread.start();
	}
	
	public static final void onDone(boolean kill) {
		UpdateGUIManager.hideLoadingWindow();
		BurkeyAutopackSystem.doShutdown();
	}
	
	public static final boolean done() {
		return done;
	}
	
	public static final void forceFinish() {
		done = true;
	}
	
	private static final void loadLookAndFeel() {
		BurkeyAutopackSystem.log("Setting look and feel, this can take some time...");
		try {
			if (!LaF.setLaF(BurkeyAutopackSystem.THEME)) {
				BurkeyAutopackSystem.log("Couldn't set custom LaF.");
				throw new Exception("Not set");
			} else {
				BurkeyAutopackSystem.log("Set LaF: " + LaF.lafs[BurkeyAutopackSystem.THEME].getName());
			}
		} catch (Exception er) {
			BurkeyAutopackSystem.log("Failed to find any custom LaFs, using system look and feel default.");
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e1) {
				BurkeyAutopackSystem.log("Failed to find default look and feel, using cross-platform default.");
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e2) {
					e2.printStackTrace();
					done = true;
					return;
				}
			}
		}
		BurkeyAutopackSystem.log("Set look and feel.");
	}
	
}