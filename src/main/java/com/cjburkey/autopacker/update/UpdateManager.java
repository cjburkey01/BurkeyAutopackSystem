package com.cjburkey.autopacker.update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import com.cjburkey.autopacker.BurkeyAutopackSystem;
import com.cjburkey.autopacker.LocalPackData;
import com.cjburkey.autopacker.PackVersion;
import com.cjburkey.autopacker.action.IAction;

public class UpdateManager {
	
	public static final IAction[] getUpdates(String latest) throws Exception {
		PackVersion v = new PackVersion(latest);
		return v.getActions();
	}
	
	public static final PackVersion[] requiredVersions(LocalPackData localVersion, PackVersion latestVersion) throws Exception {
		List<PackVersion> versions = new ArrayList<>();
		
		BurkeyAutopackSystem.log("Calculating version requirements...");
		PackVersion currentVersion = latestVersion;
		while (!currentVersion.getVersionName().trim().equals(localVersion.getVersion().trim())) {
			BurkeyAutopackSystem.log("  Found version to install: " + currentVersion.getVersionName());
			versions.add(currentVersion);
			if (!currentVersion.hasPreviousVersion()) {
				break;
			}
			currentVersion = new PackVersion(currentVersion.getPreviousVersion().toString());
		}
		BurkeyAutopackSystem.log("Calculated version requirements.");
		
		StringBuilder available = new StringBuilder();
		available.append('[');
		for (int i = 0; i < versions.size(); i ++) {
			available.append(versions.get(versions.size() - i - 1).getVersionName());
			if (i != versions.size() - 1) {
				available.append(',');
				available.append(' ');
			}
		}
		available.append(']');
		JOptionPane.showMessageDialog(null, "The following update(s) will be installed:\n" + available.toString(), "Update(s) Available", JOptionPane.INFORMATION_MESSAGE);
		
		if (versions.size() > 1) {
			String newest = versions.get(0).getVersionName();
			Collections.reverse(versions);
			BurkeyAutopackSystem.log("Downloading updates: " + versions.get(0).getVersionName() + " to " + newest);
		} else {
			BurkeyAutopackSystem.log("Downloading single update: " + versions.get(0).getVersionName());
		}
		return versions.toArray(new PackVersion[versions.size()]);
	}
	
}