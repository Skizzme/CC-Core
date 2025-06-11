package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.chunkgen.ChunkGenerator;
import me.skizzme.cc.stats.Statistics;
import me.skizzme.cc.util.CommandUtils;
import me.skizzme.cc.util.TextUtils;
import net.impactdev.impactor.api.economy.EconomyService;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.math.BigDecimal;

public class ChunkGenCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("chunkgen")
                        .requires(Permissions.require(CCCore.PERM_ID + ".chunkgen"))
                        .executes(ChunkGenCommand::run)
                        .then(CommandManager.argument("action", StringArgumentType.word())
                                .suggests(CommandUtils.suggestions(new String[]{"reload", "reset", "stop", "start"})::build)
                                .executes(ChunkGenCommand::runArgs1)
                        )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().sendMessage(
                TextUtils.formatted(
                        "&aStats over last minute:\n" +
                        "  &7Total Generated: &a" + Statistics.getStat("totalChunkGen").longValue() + "\n" +
                        "  &7Total Pre-Generated: &a" + Statistics.getStat("totalChunkPreGen").longValue()
                )
        );

        context.getSource().sendMessage(TextUtils.formatted("\n&aCurrent Progress:"));
        for (ServerWorld w : context.getSource().getServer().getWorlds()) {
            ChunkGenerator.ChunkGenState state = w.getPersistentStateManager().get(ChunkGenerator.ChunkGenState.persistentType(), CCCore.MOD_ID);
            context.getSource().sendMessage(TextUtils.formatted("&a" + w.getRegistryKey().getValue() + "&7: Position: &a(" + state.chunkX + ", " + state.chunkZ + ")&7, Radius: &a" + state.radius));
        }

        return 1;
    }

    private static int runArgs1(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String action = StringArgumentType.getString(context, "action");
        if (action.equals("reload")) {
            ChunkGenerator.load(context.getSource().getServer());
            context.getSource().sendMessage(TextUtils.formatted("&aSuccessfully reloaded the ChunkGenerator config"));
        } else if (action.equals("reset")) {
            for (ServerWorld w : context.getSource().getServer().getWorlds()) {
                ChunkGenerator.ChunkGenState state = w.getPersistentStateManager().get(ChunkGenerator.ChunkGenState.persistentType(), CCCore.MOD_ID);
                state.dir = 0;
                state.chunkZ = 1;
                state.chunkX = 1;
                state.radius = 1;
                state.markDirty();
            }
        } else if (action.equals("stop")) {
            for (ServerWorld w : context.getSource().getServer().getWorlds()) {
                ChunkGenerator.ChunkGenState state = w.getPersistentStateManager().get(ChunkGenerator.ChunkGenState.persistentType(), CCCore.MOD_ID);
                state.enabled = false;
                state.markDirty();
            }
        } else if (action.equals("start")) {
            for (ServerWorld w : context.getSource().getServer().getWorlds()) {
                ChunkGenerator.ChunkGenState state = w.getPersistentStateManager().get(ChunkGenerator.ChunkGenState.persistentType(), CCCore.MOD_ID);
                state.enabled = true;
                state.markDirty();
            }

            ChunkGenerator.chunkLoader(context.getSource().getServer());
        }
        return 1;
    }
}