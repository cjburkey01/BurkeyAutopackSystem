package com.cjburkey.autopacker.update.gui;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import com.alee.laf.WebLookAndFeel;
import com.pagosoft.plaf.PlafOptions;

public class LaF {
	
	public static final LaF[] lafs = new LaF[] {
		new LaF("WebLaF", "com.alee.laf.WebLookAndFeel", () -> WebLookAndFeel.install()),
		new LaF("LiquidInf", "com.birosoft.liquid.LiquidLookAndFeel", () -> { try { UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel"); } catch (Exception e) {  } }),
		new LaF("PgsLaF", "com.pagosoft.plaf.PlafOptions", () -> { PlafOptions.setAsLookAndFeel(); PlafOptions.updateAllUIs(); }),
		new LaF("Nimbus", NimbusLookAndFeel.class.getName(), () -> { try { UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName()); } catch (Exception e) {  } }),
		//new LaF("Motif", MotifLookAndFeel.class.getName(), () -> { try { UIManager.setLookAndFeel(MotifLookAndFeel.class.getName()); } catch (Exception e) {  } }),
	};
	
	public static final boolean setLaF(int id) throws Exception {
		if (id < lafs.length) {
			LaF l = lafs[id];
			if (Class.forName(l.getClassName()) != null) {
				l.run();
				return true;
			}
		}
		return false;
	}
	
	private String name;
	private String className;
	private Runnable activate;
	
	public LaF(String n, String cls, Runnable r) {
		name = n;
		className = cls;
		activate = r;
	}
	
	public String getName() {
		return name;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void run() {
		activate.run();
	}
	
	public String toString() {
		return name;
	}
	
}