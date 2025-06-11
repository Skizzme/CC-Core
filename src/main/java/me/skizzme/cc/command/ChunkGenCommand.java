package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.stats.Statistics;
import me.skizzme.cc.util.TextUtils;
import net.impactdev.impactor.api.economy.EconomyService;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.math.BigDecimal;

public class ChunkGenCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("chunkgen")
                        .requires(Permissions.require(CCCore.PERM_ID + ".chunkgen"))
                        .executes(ChunkGenCommand::run)
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().sendMessage(
                TextUtils.formatted(
                        "&aStats over last minute:\n" +
                        "  &7Total Generated: &a" + Statistics.getMinuteStat("totalChunkGen").longValue() + "\n" +
                        "  &7Total Pre-Generated: &a" + Statistics.getMinuteStat("totalChunkPreGen").longValue()
                )
        );
        return 1;
    }
}
