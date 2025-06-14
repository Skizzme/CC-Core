package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.Advertiser;
import me.skizzme.cc.util.CommandUtils;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class AdvertiserCommand {

    private static final HashMap<ServerPlayerEntity, Entity> plays = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("advertiser")
                        .requires(Permissions.require(CCCore.PERM_ID + ".advertiser"))
                        .then(
                                CommandManager.argument("action", StringArgumentType.string())
                                        .suggests(CommandUtils.suggestions(new String[]  { "run", "stop", "start" })::build)
                                        .executes(AdvertiserCommand::run)
                        )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String arg = StringArgumentType.getString(context, "action");
        MinecraftServer s = context.getSource().getServer();

        if (arg.equals("run")) {
            Advertiser.runAd(s);
        } else if (arg.equals("stop")) {
            Advertiser.enabled = false;
            context.getSource().sendMessage(TextUtils.formatted("&7Success. Ads wont be sent until the server restarts"));
        } else if (arg.equals("start")) {
            Advertiser.enabled = true;
            context.getSource().sendMessage(TextUtils.formatted("&7Success. Ads will continue to run."));
        }

        return 1;
    }
}
