package me.skizzme.cc;

import me.skizzme.cc.listeners.TransactionListener;
import me.skizzme.cc.shop.Shop;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.impactdev.impactor.api.economy.events.EconomyTransactionEvent;
import net.impactdev.impactor.api.events.ImpactorEventBus;

public class CCCoreServer implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		CCCore.LOGGER.info("serverinit");

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			Shop.loadConfig();
		});

		ImpactorEventBus.bus().subscribe(EconomyTransactionEvent.Pre.class, new TransactionListener());
	}
}