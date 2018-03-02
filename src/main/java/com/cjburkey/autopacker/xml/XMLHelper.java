package com.cjburkey.autopacker.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class XMLHelper {
	
	public static final Document getDocumentFromUrl(String url) throws Exception {
		return getDocumentFromString(getTextFromUrl(url));
	}
	
	public static final Document getDocumentFromString(String in) throws Exception {
		InputStream stream = getStringToStream(in);
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
		return doc;
	}
	
	public static final String getTextFromUrl(String url) throws Exception {
		return getTextFromStream(new URL(url).openStream());
	}
	
	public static final String getTextFromFile(File f) throws Exception {
		return getTextFromStream(new FileInputStream(f));
	}
	
	public static final String getTextFromStream(InputStream is) throws Exception {
		Scanner scanner = new Scanner(is);
		StringBuilder data = new StringBuilder();
		while (scanner.hasNextLine()) {
			data.append(scanner.nextLine());
			data.append('\n');
		}
		scanner.close();
		return data.toString();
	}
	
	public static final InputStream getStringToStream(String in) throws Exception {
		return new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8.name()));
	}
	
}