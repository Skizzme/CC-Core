package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.shop.Shop;
import net.impactdev.impactor.api.economy.EconomyService;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.math.BigDecimal;

public class MoneyCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("money")
                        .requires(Permissions.require(CCCore.PERM_ID + ".money"))
                        .then(
                                CommandManager.argument("amount", IntegerArgumentType.integer())
                                        .executes(MoneyCommand::run)
                        )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        try {
            EconomyService.instance().account(context.getSource().getPlayer().getUuid()).get().deposit(BigDecimal.valueOf(amount));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
