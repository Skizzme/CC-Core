package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.shop.Shop;
import net.kyori.adventure.text.minimessage.translation.Argument;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

public class CVoteCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("cvote")
                        .requires((s) -> s.hasPermission(4))
                        .then(
                                Commands.argument("target", EntityArgument.player())
                                        .executes(CVoteCommand::run)
                        )
        );
    }

    private static int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try {
            ServerPlayer target = EntityArgument.getPlayer(context, "target");
            String command = "padmin givekey %player% 1 Vote Crate".replaceAll("%player%", target.getGameProfile().getName());
            context.getSource().getServer().getCommands().performPrefixedCommand(context.getSource(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
