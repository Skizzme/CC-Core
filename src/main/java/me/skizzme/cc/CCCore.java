package me.skizzme.cc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import me.skizzme.cc.command.*;
import me.skizzme.cc.stats.Statistics;
import me.skizzme.cc.util.TextUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.text.NumberFormat;

public class CCCore implements ModInitializer {
	public static final String MOD_ID = "cc-core";
	public static final String PERM_ID = "cc.core";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final NumberFormat MONEY_FORMAT = NumberFormat.getCurrencyInstance();
	public static final NumberFormat INT_FORMAT = NumberFormat.getIntegerInstance();
	public static final Text PREFIX = Text.empty()
			.append(TextUtils.formatted("&7["))
			.append(TextUtils.gradientTextRGB(
					new Color(45, 73, 156),
					Color.white,
					"Cobblemon Cove"
			))
			.append(TextUtils.formatted("&7] "));

	@Override
	public void onInitialize() {
		Advertiser.register();
		CommandRegistrationCallback.EVENT.register(this::registerCommands);
		ServerChunkEvents.CHUNK_GENERATE.register((world, chunk) -> {
			Statistics.updateStat("totalChunkGen", 1.0);
		});
	}

	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registry, CommandManager.RegistrationEnvironment env) {
		ShopCommand.register(dispatcher);
		CVoteCommand.register(dispatcher);
		MoneyCommand.register(dispatcher);
		ChunkGenCommand.register(dispatcher);
		SudoCommand.register(dispatcher);
		ActCommand.register(dispatcher);
		DiscordCommand.register(dispatcher);
//		VoteCommand.register(dispatcher);
		LOGGER.info("Registered commands");
	}

	public static void broadcast(Text message) {
		for (ServerPlayerEntity p : CCCoreServer.getServer().getPlayerManager().getPlayerList()) {
			p.sendMessage(message);
		}
		CCCoreServer.getServer().sendMessage(Text.empty().append("[BROADCAST] ").append(message));
	}
}