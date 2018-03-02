package com.cjburkey.autopacker.asm;

import java.util.Map;
import com.cjburkey.autopacker.BurkeyAutopackSystem;
import com.cjburkey.autopacker.update.InitUpdateSystem;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.DependsOn;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({ "com.cjburkey.autopacker" })
@MCVersion("1.12.2")
@Name("BurkeyAutopackSystem")
@DependsOn("forge")
public class UpdateSystemLoader implements IFMLLoadingPlugin {
	
	public UpdateSystemLoader() {
		BurkeyAutopackSystem.log("Loading modpack update checking system.");
		InitUpdateSystem.onLoad();
		BurkeyAutopackSystem.log("Loaded modpack update checking system.");
		while (!InitUpdateSystem.done()) {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		InitUpdateSystem.onDone(false);
	}
	
	// -- USELESS -- //

	public String[] getASMTransformerClass() {
		return new String[0];
	}

	public String getModContainerClass() {
		return "com.cjburkey.autopacker.asm.UpdateSystemDummy";
	}

	public String getSetupClass() {
		return null;
	}

	public void injectData(Map<String, Object> data) {
		
	}

	public String getAccessTransformerClass() {
		return null;
	}
	
}