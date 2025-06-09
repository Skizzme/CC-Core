package me.skizzme.cc;

import com.mojang.brigadier.CommandDispatcher;
import me.skizzme.cc.command.CVoteCommand;
import me.skizzme.cc.command.ShopCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCCore implements ModInitializer {
	public static final String MOD_ID = "cc-core";
	public static final String PERM_ID = "cc.core";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register(this::registerCommands);
	}

	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registry, CommandManager.RegistrationEnvironment env) {
		ShopCommand.register(dispatcher);
		CVoteCommand.register(dispatcher);
		LOGGER.info("Registered commands");
	}
}