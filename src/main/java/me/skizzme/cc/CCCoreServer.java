package me.skizzme.cc;

import net.fabricmc.api.DedicatedServerModInitializer;

public class CCCoreServer implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		CCCore.LOGGER.info("serverinit");
	}
}