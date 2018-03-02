package com.cjburkey.autopacker;

import java.net.MalformedURLException;
import java.net.URL;

public class ModPack {
	
	private final String name;
	private final String author;
	private final PackVersion currentVersion;
	
	public ModPack(String name, String author, String versionUrl) throws MalformedURLException {
		this.name = name;
		this.author = author;
		currentVersion = new PackVersion(versionUrl);
	}
	
	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public URL getCurrentVersionUrl() {
		return currentVersion.getUrl();
	}
	
	public String getCurrentVersionName() throws Exception {
		return currentVersion.getVersionName();
	}
	
	public PackVersion getCurrentVersion() {
		return currentVersion;
	}
	
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("Modpack \"");
		out.append(name);
		out.append("\" by \"");
		out.append(author);
		out.append("\" latest version at: ");
		out.append(getCurrentVersionUrl().toString());
		return out.toString();
	}
	
}