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
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.UUID;

public class MessageCommand {

    private static final HashMap<UUID, UUID> LAST_RECEIVED = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("pm")
                        .requires(Permissions.require(CCCore.PERM_ID + ".pm"))
                        .then(CommandManager.argument("target", EntityArgumentType.player())
                                .then(
                                        CommandManager
                                                .argument("message", StringArgumentType.greedyString())
                                                .executes(MessageCommand::runMessage)
                                )
                        )
        );
        dispatcher.register(
                CommandManager.literal("r")
                        .requires(Permissions.require(CCCore.PERM_ID + ".pm"))
                        .then(
                                CommandManager
                                        .argument("message", StringArgumentType.greedyString())
                                        .executes(MessageCommand::runRespond)
                        )
        );
    }

    private static int runMessage(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String message = StringArgumentType.getString(context, "message");
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        ServerPlayerEntity sender = context.getSource().getPlayer();

        LAST_RECEIVED.put(target.getUuid(), sender.getUuid());

        sendMessage(sender, target, message);

        return 1;
    }

    private static int runRespond(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String message = StringArgumentType.getString(context, "message");
        ServerPlayerEntity sender = context.getSource().getPlayer();


        if (!LAST_RECEIVED.containsKey(sender.getUuid())) {
            sender.sendMessage(TextUtils.formatted("&cYou have no one to respond to..."));
            return 1;
        }

        ServerPlayerEntity target = context.getSource().getServer().getPlayerManager().getPlayer(LAST_RECEIVED.get(sender.getUuid()));

        sendMessage(sender, target, message);

        return 1;
    }

    private static void sendMessage(ServerPlayerEntity sender, ServerPlayerEntity target, String message) {
        target.sendMessage(
                Text.empty()
                        .append(TextUtils.formatted("&8["))
                        .append(Text.empty().append(sender.getName()).formatted(Formatting.GRAY))
                        .append(TextUtils.formatted(" &8>> &ame&8] "))
                        .append(TextUtils.formatted("&7" + message))
        );

        sender.sendMessage(
                Text.empty()
                        .append(TextUtils.formatted("&8[&ame &8>> "))
                        .append(Text.empty().append(target.getName()).formatted(Formatting.GRAY))
                        .append(TextUtils.formatted("&8] "))
                        .append(TextUtils.formatted("&7" + message))
        );
    }
}
