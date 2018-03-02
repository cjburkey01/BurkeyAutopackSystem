package com.cjburkey.autopacker;

import java.net.MalformedURLException;
import java.net.URL;

public class LocalPackData {
	
	private final URL url;
	private final String name;
	private final String author;
	private final String version;
	
	public LocalPackData(String url, String name, String author, String version) throws MalformedURLException {
		this.url = new URL(url);
		this.name = name;
		this.author = author;
		this.version = version;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getVersion() {
		return version;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public String toXml() {
		StringBuilder out = new StringBuilder();
		out.append("<pack name=\"");
		out.append(name);
		out.append("\" author=\"");
		out.append(author);
		out.append("\" href=\"");
		out.append(url.toString());
		out.append("\">\n");
		out.append("\t<version>");
		out.append(version);
		out.append("</version>\n");
		out.append("</pack>");
		return out.toString();
	}
	
}