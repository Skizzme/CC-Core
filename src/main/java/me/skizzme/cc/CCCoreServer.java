package me.skizzme.cc;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCCoreServer implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		CCCore.LOGGER.info("serverinit");
	}
}