package me.skizzme.cc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import me.skizzme.cc.command.CVoteCommand;
import me.skizzme.cc.command.MoneyCommand;
import me.skizzme.cc.command.ShopCommand;
import me.skizzme.cc.shop.Shop;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;

public class CCCore implements ModInitializer {
	public static final String MOD_ID = "cc-core";
	public static final String PERM_ID = "cc.core";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final NumberFormat MONEY_FORMAT = NumberFormat.getCurrencyInstance();
	public static final NumberFormat INT_FORMAT = NumberFormat.getIntegerInstance();

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register(this::registerCommands);
	}

	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registry, CommandManager.RegistrationEnvironment env) {
		ShopCommand.register(dispatcher);
		CVoteCommand.register(dispatcher);
		MoneyCommand.register(dispatcher);
		LOGGER.info("Registered commands");
	}
}