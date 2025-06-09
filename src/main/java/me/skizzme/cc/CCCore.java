package me.skizzme.cc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContextBuilder;
import me.skizzme.cc.command.ShopCommand;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

public class CCCore implements ModInitializer {
	public static final String MOD_ID = "cc-core";
	public static final String PERM_ID = "cc.core";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register(this::registerCommands);

		System.out.println("TEST");

		ArrayList<ItemConvertible> items = new ArrayList<>();
		for (Map.Entry<RegistryKey<ItemGroup>, ItemGroup> s : Registries.ITEM_GROUP.getEntrySet()) {
			System.out.println(s);
		}
	}

	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registry, CommandManager.RegistrationEnvironment env) {
		ShopCommand.register(dispatcher);
	}
}