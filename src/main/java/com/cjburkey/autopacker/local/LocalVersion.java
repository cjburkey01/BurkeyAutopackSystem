package com.cjburkey.autopacker.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.cjburkey.autopacker.BurkeyAutopackSystem;
import com.cjburkey.autopacker.LocalPackData;
import com.cjburkey.autopacker.xml.XMLHelper;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

public class LocalVersion {
	
	public static final File getMcDir() {
		return (File) FMLInjectionData.data()[6];
	}
	
	public static final File getVersionFile() {
		return new File(getMcDir(), "/burkeyautopacker/localVersion.xml");
	}
	
	public static final LocalPackData getLocalPackInformation() throws Exception {
		if (!getVersionFile().exists()) {
			return null;
		}
		Document doc = XMLHelper.getDocumentFromString(XMLHelper.getTextFromFile(getVersionFile()));
		Element e = doc.getDocumentElement();
		String packName = e.getAttribute("name");
		String author = e.getAttribute("author");
		String url = e.getAttribute("href");
		String versionName = ((Element) e.getElementsByTagName("version").item(0)).getTextContent().trim();
		return new LocalPackData(url, packName, author, versionName);
	}
	
	public static final void writeLocalPackInformation(LocalPackData data) throws Exception {
		if (data != null) {
			if (getVersionFile().exists()) {
				getVersionFile().delete();
			} else if (!getVersionFile().getParentFile().exists()) {
				getVersionFile().getParentFile().mkdirs();
			}
			FileWriter writer = new FileWriter(getVersionFile());
			writer.write(data.toXml());
			writer.close();
		} else {
			throw new Exception("Data is null.");
		}
	}
	
}