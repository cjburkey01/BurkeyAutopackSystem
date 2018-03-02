package com.cjburkey.autopacker.update.core;

import java.net.MalformedURLException;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.cjburkey.autopacker.BurkeyAutopackSystem;
import com.cjburkey.autopacker.LocalPackData;
import com.cjburkey.autopacker.ModPack;
import com.cjburkey.autopacker.PackVersion;
import com.cjburkey.autopacker.local.LocalVersion;
import com.cjburkey.autopacker.update.InitUpdateSystem;
import com.cjburkey.autopacker.update.UpdateManager;
import com.cjburkey.autopacker.xml.XMLHelper;

public class XMLUpdateSystem {
	
	private static String xml;
	
	private static ModPack pack;
	private static LocalPackData localPack;
	
	public static boolean loaded = false;
	public static PackVersion[] updates;
	
	public static final void init() throws Exception {
		BurkeyAutopackSystem.log("Checking for modpack installation...");
		verifyLocalVersion();
		if (InitUpdateSystem.done()) {
			BurkeyAutopackSystem.log("No modpack set, skipping initialization.");
			return;
		}
		BurkeyAutopackSystem.log("Fetching remote XML data...");
		xml = XMLHelper.getTextFromUrl(localPack.getUrl().toString());
		BurkeyAutopackSystem.log("Fetched remote data.");
		if (pack == null) {
			BurkeyAutopackSystem.log("Parsing XML...");
			pack = getModpackFromDocument(XMLHelper.getDocumentFromString(xml));
		} else {
			BurkeyAutopackSystem.log("XML already loaded, skipping parse.");
		}
		if (!isUpdateAvailable()) {
			BurkeyAutopackSystem.log("No updates available.");
			InitUpdateSystem.forceFinish();
			return;
		}
		BurkeyAutopackSystem.log("Loading updates.");
		updates = UpdateManager.requiredVersions(localPack, pack.getCurrentVersion());
		loaded = true;
	}
	
	public static final LocalPackData getNewData() throws Exception {
		return new LocalPackData(localPack.getUrl().toString(), localPack.getName(), localPack.getAuthor(), pack.getCurrentVersionName());
	}
	
	private static final void verifyLocalVersion() throws Exception {
		BurkeyAutopackSystem.log("Verifying local data...");
		localPack = LocalVersion.getLocalPackInformation();
		if (localPack == null) {
			BurkeyAutopackSystem.log("No previous modpack installation found.");
			String url = loadNewModpack(0);
			if (!InitUpdateSystem.done()) {
				pack = getModpackFromDocument(XMLHelper.getDocumentFromUrl(url));
				localPack = new LocalPackData(url, pack.getName(), pack.getAuthor(), null);
				BurkeyAutopackSystem.log("Set modpack, writing local data.");
				LocalVersion.writeLocalPackInformation(localPack);
				BurkeyAutopackSystem.log("Reloading pack...");
				verifyLocalVersion();
				return;
			}
		} else {
			BurkeyAutopackSystem.log("Local version: " + localPack.getVersion());
		}
	}
	
	private static final ModPack getModpackFromDocument(Document doc) throws Exception {
		Element e = doc.getDocumentElement();
		if (e == null) {
			throw new Exception("Root modpack xml element not found.");
		}
		if (!e.getTagName().trim().toLowerCase().equals("pack")) {
			throw new Exception("Root modpack xml element not named \"pack\".");
		}
		BurkeyAutopackSystem.log("Found root element.");
		String packName = e.getAttribute("name");
		String author = e.getAttribute("author");
		if (packName == null || author == null || (packName = packName.trim()).isEmpty() || (author = author.trim()).isEmpty()) {
			throw new Exception("Pack \"name\" attribute and/or pack \"author\" attribute not found.");
		}
		BurkeyAutopackSystem.log("Determined name and author of pack.");
		NodeList children = e.getElementsByTagName("version");
		if (children.getLength() < 1 || children.item(0) == null) {
			throw new Exception("Modpack version element not found in pack xml file.");
		}
		Element versionTag = (Element) children.item(0);
		String versionUrl = versionTag.getAttribute("href");
		if (versionUrl == null || (versionUrl = versionUrl.trim()).isEmpty()) {
			throw new Exception("Modpack version \"href\" attribute not defined in pack xml file.");
		}
		BurkeyAutopackSystem.log("Fetching latest version.");
		ModPack mp = new ModPack(packName, author, versionUrl);
		BurkeyAutopackSystem.log("Latest version: \"" + mp.getCurrentVersionName() + "\".");
		BurkeyAutopackSystem.log("Remote: " + mp);
		return mp;
	}
	
	private static final String loadNewModpack(int attempt) {
		if (attempt < 1) {
			BurkeyAutopackSystem.log("Requesting new modpack from user...");
			String packUrl = JOptionPane.showInputDialog("Please enter the modpack updater url:");
			if (packUrl == null || packUrl.trim().isEmpty() || packUrl.trim().equalsIgnoreCase("http://") || packUrl.trim().equalsIgnoreCase("https://")) {
				BurkeyAutopackSystem.log("Not filled in.");
				return loadNewModpack(attempt + 1);
			}
			return packUrl;
		} else {
			BurkeyAutopackSystem.log("Skipping modpack selection...");
			InitUpdateSystem.forceFinish();
			return null;
		}
	}
	
	private static final boolean isUpdateAvailable() throws Exception {
		if (localPack.getVersion() == null) {
			return true;
		}
		return !pack.getCurrentVersionName().trim().equalsIgnoreCase(localPack.getVersion());
	}
	
}