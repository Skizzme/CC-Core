package me.skizzme.cc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import me.skizzme.cc.chunkgen.ChunkGenerator;
import me.skizzme.cc.command.*;
import me.skizzme.cc.listeners.TransactionListener;
import me.skizzme.cc.shop.Shop;
import me.skizzme.cc.stats.Statistics;
import me.skizzme.cc.util.TextUtils;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.impactdev.impactor.api.economy.events.EconomyTransactionEvent;
import net.impactdev.impactor.api.events.ImpactorEventBus;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.text.NumberFormat;

public class CCCore implements DedicatedServerModInitializer {
	public static final String MOD_ID = "cc-core";
	public static final String PERM_ID = "cc.core";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final NumberFormat MONEY_FORMAT = NumberFormat.getCurrencyInstance();
	public static final NumberFormat INT_FORMAT = NumberFormat.getIntegerInstance();
	public static final Text PREFIX = Text.empty()
			.append(TextUtils.formatted("&8["))
			.append(TextUtils.gradientTextRGB(
					new Color(45, 73, 156),
					Color.white,
					"Cobblemon Cove"
			))
			.append(TextUtils.formatted("&8] "));
	private static MinecraftServer server;
	private static CCCore INSTANCE;

	@Override
	public void onInitializeServer() {
		INSTANCE = this;
		Advertiser.register();
		CommandRegistrationCallback.EVENT.register(this::registerCommands);
		ServerChunkEvents.CHUNK_GENERATE.register((world, chunk) -> {
			Statistics.updateStat("totalChunkGen", 1.0);
		});

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			CCCore.server = server;
			Shop.loadConfig();
			ChunkGenerator.load(server);
//			ChunkGenerator.chunkLoader(server);
			for (ServerWorld world : server.getWorlds()) {
				System.out.println("TEST: " + world.getRegistryKey().getRegistry() + ", " + world.getRegistryKey().getValue());
			}
//			VoteCommand.register(server.getCommandManager().getDispatcher());
//			MessageCommand.register(server.getCommandManager().getDispatcher());

			System.out.println("TEST ENFORCE SECURE PROFILE: " + server.shouldEnforceSecureProfile());
		});

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {

		});

		ImpactorEventBus.bus().subscribe(EconomyTransactionEvent.Pre.class, new TransactionListener());
	}

	public static MinecraftServer getServer() {
		return server;
	}

	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registry, CommandManager.RegistrationEnvironment env) {
		ShopCommand.register(dispatcher);
		CVoteCommand.register(dispatcher);
		MoneyCommand.register(dispatcher);
		ChunkGenCommand.register(dispatcher);
		SudoCommand.register(dispatcher);
		ActCommand.register(dispatcher);
		DiscordCommand.register(dispatcher);
		AdvertiserCommand.register(dispatcher);
		ApplyCommand.register(dispatcher);
		BroadcastCommand.register(dispatcher);
		PerformanceCommand.register(dispatcher);
		RestartCommand.register(dispatcher);
		VoteCommand.register(dispatcher);
		MessageCommand.register(dispatcher);

		LOGGER.info("Registered commands");
	}

	public static void broadcast(Text message) {
		for (ServerPlayerEntity p : CCCore.getServer().getPlayerManager().getPlayerList()) {
			p.sendMessage(message);
		}
		CCCore.getServer().sendMessage(Text.empty().append("[BROADCAST] ").append(message));
	}

	public static void broadcastPrefix(String message) {
		Text broadcast = Text.empty().append(CCCore.PREFIX).append(TextUtils.formatted(message));
		broadcast(broadcast);
	}

	public static CCCore getInstance() {
		return INSTANCE;
	}
}