package com.cjburkey.autopacker.update;

import javax.swing.JOptionPane;
import com.alee.utils.SwingUtils;
import com.cjburkey.autopacker.BurkeyAutopackSystem;
import com.cjburkey.autopacker.PackVersion;
import com.cjburkey.autopacker.action.IAction;
import bas.actions.ActionPrintln;

public class Updater {
	
	private static boolean done = false;
	
	public static final void performUpdates(PackVersion[] updates) throws Exception {
		for (int i = 0; i < updates.length; i ++) {
			BurkeyAutopackSystem.log("Update: " + updates[i]);
			UpdateGUIManager.getProgressFrame().setUpdateProgress((float) i / updates.length, "Update: " + updates[i].getVersionName());
			IAction[] actions = updates[i].getActions();
			for (int j = 0; j < actions.length; j ++) {
				UpdateGUIManager.getProgressFrame().setPerUpdateProgress((float) j / actions.length, "Action: " + actions[j]);
				if (actions[j] instanceof ActionPrintln) {
					actions[j].startAction();
				} else {
					BurkeyAutopackSystem.log("  " + actions[j]);
					actions[j].startAction();
					waitForAction(actions[j]);
					if (actions[j].didActionFail()) {
						BurkeyAutopackSystem.log("    Failed: " + actions[j]);
					} else {
						BurkeyAutopackSystem.log("    Done.");
					}
				}
			}
		}
		UpdateGUIManager.getProgressFrame().setUpdateProgress(1.0f, "Done");
		UpdateGUIManager.getProgressFrame().setPerUpdateProgress(1.0f, "Done");
		UpdateGUIManager.getProgressFrame().setPerActionProgress(0.0f, "Done");
		Thread.sleep(1000);
	}
	
	private static final void waitForAction(IAction a) throws Exception {
		while (!a.isActionDone() && !a.didActionFail()) {
			UpdateGUIManager.getProgressFrame().setPerActionProgress(a.getActionProgress(), (int) (a.getActionProgress() * 100.0f) + "%");
			Thread.sleep(5);
		}
	}
	
}