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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.world.chunk.ChunkLoader;

public class ShopCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("shop")
                        .requires(Permissions.require(CCCore.PERM_ID + ".shop"))
                        .executes(ShopCommand::run)
                        .then(CommandManager
                                .argument("action", StringArgumentType.word())
                                .suggests((ctx, builder) -> {
                                    String[] suggestions = {"reload", "log"};
                                    for (String s : suggestions) {
                                        if (Permissions.check(ctx.getSource(), CCCore.PERM_ID + ".shop." + s)) {
                                            builder.suggest(s);
                                        }
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
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (sub.equals("reload")) {
            context.getSource().sendMessage(TextUtils.formatted("&7Reloading..."));
            long st = System.nanoTime();
            Shop.loadConfig();
            long et = System.nanoTime();
            context.getSource().sendMessage(TextUtils.formatted("&aSuccessfully reloaded the shop config in " + Math.round((float) (et-st) / 1e6f * 100) / 100f + "ms"));
        }
        else if (sub.equals("log")) {
            if (Shop.TRANSACTION_NOTIFS.contains(player)) {
                Shop.TRANSACTION_NOTIFS.remove(player);
            } else {
                Shop.TRANSACTION_NOTIFS.add(player);
            }
            player.sendMessage(TextUtils.formatted("&7You will " + (Shop.TRANSACTION_NOTIFS.contains(player) ? "&cno longer " : "&a now") + " &7be notified of shop transactions."));
        }
        return 1;
    }
}
