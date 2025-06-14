package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.util.StringUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class RestartCommand {
    private static long executedAt = -1;
    private static long restartDelayMS = 5 * 60 * 1000;
    private static long[] messageDelaySeconds = { 5 * 60, 60, 30, 5, 4, 3, 2, 1 };
    private static int messageIndex = 0;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("srestart")
                        .requires(Permissions.require(CCCore.PERM_ID + ".srestart"))
                        .executes(RestartCommand::run)
        );
        dispatcher.register(
                CommandManager.literal("srestartcancel")
                        .requires(Permissions.require(CCCore.PERM_ID + ".srestart"))
                        .executes(RestartCommand::runCancel)
        );

        ServerTickEvents.START_SERVER_TICK.register(t -> {
            handleTick(t);
        });
    }

    private static void handleTick(MinecraftServer s) {
        if (executedAt == -1) {
            return;
        }

        long restartTimeMS = (executedAt + restartDelayMS);

        if ((restartTimeMS - System.currentTimeMillis()) <= messageDelaySeconds[messageIndex] * 1000) {
            String timeFormatted = StringUtils.formatElapsed(
                    messageDelaySeconds[messageIndex] * 1000,
                    new long[] { 24*60*60*1000, 60*60*1000, 60*1000, 1000 },
                    new String[] {" hours", " minutes", " seconds"},
                    false,
                    false
            );
            CCCore.broadcastPrefix("&cServer restarting in " + timeFormatted + "...");
            messageIndex++;
        }

        if (restartTimeMS - System.currentTimeMillis() <= 0) {
            s.stop(true);
        }
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        messageIndex = 0;
        executedAt = System.currentTimeMillis();

        return 1;
    }

    private static int runCancel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        messageIndex = 0;
        executedAt = -1;

        CCCore.broadcastPrefix("&cServer restart canceled!");

        return 1;
    }
}