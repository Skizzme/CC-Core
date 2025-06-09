package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.shop.Shop;
import net.kyori.adventure.text.minimessage.translation.Argument;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class CVoteCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("cvote")
                        .requires(Permissions.require(CCCore.PERM_ID + ".admin.cvote", 4))
                        .requires((s) -> s.hasPermissionLevel(4))
                        .then(
                                CommandManager.argument("target", EntityArgumentType.player())
                                        .executes(CVoteCommand::run)
                        )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
            String command = "padmin givekey %player% 1 Vote Crate".replaceAll("%player%", target.getGameProfile().getName());
            context.getSource().getServer().getCommandManager().executeWithPrefix(context.getSource(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
