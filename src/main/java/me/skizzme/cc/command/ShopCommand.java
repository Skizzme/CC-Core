package me.skizzme.cc.command;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.CobblemonItems;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.shop.Shop;
import me.skizzme.cc.shop.category.Category;
import me.skizzme.cc.shop.category.impl.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ShopCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("shop")
                        .requires(Permissions.require(CCCore.PERM_ID + ".shop"))
                        .executes(ShopCommand::run)
        );
        CCCore.LOGGER.info("CC-Core Registered Shop Command");
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Shop.display(context.getSource().getPlayerOrThrow());
        return 1;
    }
}
