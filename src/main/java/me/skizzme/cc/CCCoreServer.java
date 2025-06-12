package me.skizzme.cc;

import me.skizzme.cc.chunkgen.ChunkGenerator;
import me.skizzme.cc.command.VoteCommand;
import me.skizzme.cc.listeners.TransactionListener;
import me.skizzme.cc.shop.Shop;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.impactdev.impactor.api.economy.events.EconomyTransactionEvent;
import net.impactdev.impactor.api.events.ImpactorEventBus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import java.awt.*;

public class CCCoreServer implements DedicatedServerModInitializer {

	private static MinecraftServer server;

	@Override
	public void onInitializeServer() {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			CCCoreServer.server = server;
			Shop.loadConfig();
			ChunkGenerator.load(server);
			ChunkGenerator.chunkLoader(server);
			for (ServerWorld world : server.getWorlds()) {
				System.out.println("TEST: " + world.getRegistryKey().getRegistry() + ", " + world.getRegistryKey().getValue());
			}
			VoteCommand.register(server.getCommandManager().getDispatcher());
		});

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {

		});

		ImpactorEventBus.bus().subscribe(EconomyTransactionEvent.Pre.class, new TransactionListener());
	}

	public static MinecraftServer getServer() {
		return server;
	}
}