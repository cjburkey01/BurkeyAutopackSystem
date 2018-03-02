package com.cjburkey.autopacker.asm;

import java.util.Arrays;
import com.cjburkey.autopacker.BurkeyAutopackSystem;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.eventhandler.EventBus;

public class UpdateSystemDummy extends DummyModContainer {
	
	public UpdateSystemDummy() {
		super (new ModMetadata());
		
		ModMetadata meta = getMetadata();
		meta.authorList = Arrays.asList(new String[] { "CJ Burkey" });
		meta.name = BurkeyAutopackSystem.MOD_NAME + " CoreMod";
		meta.version = BurkeyAutopackSystem.MOD_VERSION;
		meta.modId = BurkeyAutopackSystem.MOD_ID + "core";
		meta.description = BurkeyAutopackSystem.MOD_NAME + " CoreMod.";
	}
	
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
	
}