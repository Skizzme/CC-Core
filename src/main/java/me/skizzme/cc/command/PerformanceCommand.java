package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.util.StringUtils;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PerformanceCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("performance")
                        .requires(Permissions.require(CCCore.PERM_ID + ".performance"))
                        .executes(PerformanceCommand::run)
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String[][] stats = {
                {"Used / Free Memory", StringUtils.formatBytes(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " / " + StringUtils.formatBytes(Runtime.getRuntime().freeMemory())},
                {"Total Memory", StringUtils.formatBytes(Runtime.getRuntime().totalMemory())},
                {"Max Memory", StringUtils.formatBytes(Runtime.getRuntime().maxMemory())},
        };

        ServerCommandSource source = context.getSource();

        source.sendMessage(Text.of(""));
        source.sendMessage(TextUtils.formatted("&7Performance Statistics:"));
        for (String[] stat : stats) {
            source.sendMessage(TextUtils.formatted("&7" + stat[0] + ": &a" + stat[1]));
        }
        source.sendMessage(Text.of(""));

        return 1;
    }
}