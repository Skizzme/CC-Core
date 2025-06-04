package me.skizzme.cc;

import me.skizzme.cc.command.ShopCommand;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCCore implements ModInitializer {
	public static final String MOD_ID = "cc-core";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		this.registerCommands();
	}

	public void registerCommands() {
		CommandRegistrationCallback.EVENT.register(ShopCommand::register);
	}
}