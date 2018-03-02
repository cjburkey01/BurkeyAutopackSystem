package com.cjburkey.autopacker;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cjburkey.autopacker.update.UpdateGUIManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

@Mod(modid = BurkeyAutopackSystem.MOD_ID, name = BurkeyAutopackSystem.MOD_NAME, version = BurkeyAutopackSystem.MOD_VERSION, acceptedMinecraftVersions = "[" + BurkeyAutopackSystem.MC_VERSION + "]")
public class BurkeyAutopackSystem {
	
	public static final String MOD_NAME = "Burkey Autopack System";
	public static final String MOD_ID = "autopacker";
	public static final String MOD_VERSION = "1.0.3";
	public static final String MC_VERSION = "1.12.2";
	
	public static final int THEME = 0; // WebLaf, LiquidInf, PgsLaF, Nimbus, Motif
	
	public static final File DIR = (File) FMLInjectionData.data()[6];
	public static final Logger LOG = LogManager.getLogger(MOD_ID);
	public static boolean NEED_SHUTDOWN = false;
	
	public static final void log(Object msg) {
		LOG.info(msg);
		UpdateGUIManager.show((msg == null) ? "null" : msg.toString());
	}
	
	public static final void doShutdown() {
		log("Modpack might shut down your game now.");
		if (NEED_SHUTDOWN) {
			log(" - Shutdown required to fully update pack.");
			log("THE FOLLOWING CRASH IS NOT AN ERROR TO WORRY ABOUT! DO NOT COMPLAIN TO FORGE OR MOD DEVS!");
			//FMLCommonHandler.instance().exitJava(0, true);
			Minecraft.getMinecraft().shutdown();
		} else {
			log("No need to restart. Enjoy the pack!");
		}
	}
	
}