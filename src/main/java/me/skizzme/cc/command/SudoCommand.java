package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class SudoCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("sudo")
                        .requires(Permissions.require(CCCore.PERM_ID + ".sudo"))
                        .then(
                                CommandManager.argument("target", EntityArgumentType.player())
                                        .then(
                                                CommandManager.argument("command", StringArgumentType.greedyString())
                                                        .executes(SudoCommand::run)
                                        )
                        )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "target");
        String command = StringArgumentType.getString(context, "command");

        context.getSource().getServer().getCommandManager().executeWithPrefix(player.getCommandSource(), command);
        context.getSource().getPlayer().sendMessage(TextUtils.formatted("&7Sudo successful."));

        return 1;
    }
}
