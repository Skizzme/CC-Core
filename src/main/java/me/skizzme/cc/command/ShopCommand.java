package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.shop.Shop;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ShopCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("shop")
                        .requires(Permissions.require(CCCore.PERM_ID + ".shop"))
                        .executes(ShopCommand::run)
                        .then(CommandManager
                                .argument("action", StringArgumentType.word())
                                .requires(Permissions.require(CCCore.PERM_ID + ".shop.reload"))
                                .suggests((ctx, builder) -> {
                                    String[] suggestions = {"reload"};
                                    for (String s : suggestions) {
                                        builder.suggest(s);
                                    }
                                    return builder.buildFuture();
                                })
                                .executes(ShopCommand::runArg1)
                        )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Shop.display(context.getSource().getPlayerOrThrow());
        return 1;
    }

    private static int runArg1(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String sub = StringArgumentType.getString(context, "action").toLowerCase();
        if (sub.equals("reload")) {
            context.getSource().sendMessage(TextUtils.formatted("&7Reloading..."));
            long st = System.nanoTime();
            Shop.loadConfig();
            long et = System.nanoTime();
            context.getSource().sendMessage(TextUtils.formatted("&aSuccessfully reloaded the shop config in " + Math.round((float) (et-st) / 1e6f * 100) / 100f + "ms"));
        }
        return 1;
    }
}
