package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ActCommand {

    private static final HashMap<ServerPlayerEntity, Entity> plays = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("act")
                        .requires(Permissions.require(CCCore.PERM_ID + ".act"))
                        .then(
                                CommandManager.argument("target", EntityArgumentType.entity())
                                        .executes(ActCommand::run)
                        )
        );
        ServerTickEvents.START_SERVER_TICK.register(tick -> {
            for (Map.Entry<ServerPlayerEntity, Entity> entry : plays.entrySet()) {
                ServerPlayerEntity player = entry.getKey();
                Entity viewer = entry.getValue();

                viewer.teleport(player.getServerWorld(), player.getX(), player.getY(), player.getZ(), new HashSet<>(), player.getYaw(), player.getPitch());
            }
        });
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        Entity viewer = EntityArgumentType.getEntity(context, "target");
        if (plays.containsKey(player)) {
            plays.remove(player);
        } else {
            plays.put(player, viewer);
        }

        return 1;
    }
}
