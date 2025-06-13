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
import net.minecraft.text.Text;

public class MessageCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("mes")
                        .requires(Permissions.require(CCCore.PERM_ID + ".msg"))
                        .then(CommandManager.argument("target", EntityArgumentType.player())
                                .then(
                                        CommandManager
                                        .argument("message", StringArgumentType.greedyString())
                                        .executes(MessageCommand::runMessage)
                                )
                        )
        );
    }

    private static int runMessage(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String message = StringArgumentType.getString(context, "message");
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        ServerPlayerEntity sender = context.getSource().getPlayer();

        target.sendMessage(
                Text.empty()
                        .append(TextUtils.formatted("&8["))
                        .append(sender.getName() + " &8>> &ame&8] ")
                        .append(TextUtils.formatted(message))
        );

        sender.sendMessage(
                Text.empty()
                        .append(TextUtils.formatted("&8[&ame &8>> "))
                        .append(target.getName())
                        .append(TextUtils.formatted("&8] "))
                        .append(TextUtils.formatted(message))
        );

        return 1;
    }
}
