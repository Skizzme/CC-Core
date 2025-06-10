package me.skizzme.cc;

import me.skizzme.cc.shop.Shop;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CCCoreServer implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		CCCore.LOGGER.info("serverinit");
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			Shop.loadConfig();
		});
	}
}