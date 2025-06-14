package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class BroadcastCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("broadcast")
                        .requires(Permissions.require(CCCore.PERM_ID + ".broadcast"))
                        .executes(BroadcastCommand::run)
                        .then(CommandManager
                                .argument("message", StringArgumentType.greedyString())
                                .executes(BroadcastCommand::run)
                        )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String message = StringArgumentType.getString(context, "message");

        CCCore.broadcast(Text.empty().append(CCCore.PREFIX).append(TextUtils.formatted("&7" + message)));
//        context.getSource().getServer().shouldEnforceSecureProfile()

        return 1;
    }
}
