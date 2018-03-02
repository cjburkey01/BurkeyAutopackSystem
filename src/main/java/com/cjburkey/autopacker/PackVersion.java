package com.cjburkey.autopacker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.cjburkey.autopacker.action.Actions;
import com.cjburkey.autopacker.action.IAction;
import com.cjburkey.autopacker.xml.XMLHelper;

public class PackVersion {
	
	private final URL url;
	private Document doc;
	private String name;
	private URL previousUrl;
	
	public PackVersion(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	
	public URL getUrl() {
		return url;
	}
	
	public String getVersionName() throws Exception {
		if (name == null) {
			Element root = getDocumentRoot();
			if (root == null || !root.getTagName().trim().toLowerCase().equals("version")) {
				throw new Exception("Version information root element not found.");
			}
			name = root.getAttribute("name");
			if (name == null || (name = name.trim()).isEmpty()) {
				throw new Exception("Version name not found.");
			}
		}
		return name;
	}
	
	public boolean hasPreviousVersion() throws Exception {
		return getPreviousVersion() != null;
	}
	
	public URL getPreviousVersion() throws Exception {
		if (previousUrl == null) {
			Element previous = (Element) getDocumentRoot().getElementsByTagName("previous").item(0);
			if (previous == null) {
				return null;
			}
			String href = previous.getAttribute("href");
			if (href == null || (href = href.trim()).isEmpty()) {
				return null;
			}
			previousUrl = new URL(href);
		}
		return previousUrl;
	}
	
	public IAction[] getActions() throws Exception {
		List<IAction> actions = new ArrayList<>();
		NodeList actionNodes = getDocumentRoot().getElementsByTagName("action");
		for (int i = 0; i < actionNodes.getLength(); i ++) {
			Node node = actionNodes.item(i);
			if (node == null || !(node instanceof Element)) {
				continue;
			}
			Element e = (Element) node;
			if(!e.hasAttribute("type")) {
				continue;
			}
			String type = e.getAttribute("type");
			IAction out = Actions.getAction(type);
			out.loadData(e);
			actions.add(out);
		}
		return actions.toArray(new IAction[actions.size()]);
	}
	
	public Element getDocumentRoot() throws Exception {
		if (doc == null) {
			doc = XMLHelper.getDocumentFromUrl(url.toString());
		}
		return doc.getDocumentElement();
	}
	
}