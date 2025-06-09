package me.skizzme.cc.shop;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import me.skizzme.cc.shop.category.Category;
import me.skizzme.cc.shop.category.impl.*;
import me.skizzme.cc.util.GuiUtils;
import me.skizzme.cc.util.ItemBuilder;
import me.skizzme.cc.util.TextUtils;
import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.impactdev.impactor.api.economy.transactions.details.EconomyResultType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.math.BigDecimal;

public class Shop {

    public static void display(ServerPlayerEntity player) {
        Category[] categories = {
                new BuildingBlocks(),
                new ColorfulBlocks(),
                new NatureBlocks(),
                new FoodCrops(),
                new OresMinerals(),
                new MiscItems(),
                new Berries(),
                new EvolutionItems(),
                new Consumables(),
                new HeldItems(),
                new Pokeballs(),
                new Archaeology()
        };

        GooeyButton fillButton = GooeyButton.builder()
                .display(new ItemBuilder(Items.GRAY_STAINED_GLASS_PANE).hideAllTooltip().build())
                .build();

        GooeyButton closeButton = GooeyButton.builder()
                .display(new ItemBuilder(Items.BARRIER).name("&cClose").build())
                .onClick((ac) -> {
                    UIManager.closeUI(player);
                    ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);;
                })
                .build();

        ChestTemplate.Builder builder = ChestTemplate.builder(5);

        int lineLength = 7;
        int line = 0;
        int linePos = 0;
        for (int i = 0; i < categories.length; i++) {

            Category c = categories[i];

            ItemStack stack = new ItemBuilder(c.getDisplay())
                            .name(Text.empty().append(c.getName()).formatted(Formatting.GRAY))
                            .lore(new String[]{ "&7Click to view" })
                            .build();

            GooeyButton button = GooeyButton.builder()
                    .display(stack)
                    .onClick((ac) -> {
                        c.display(ac.getPlayer());
                        ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);;
                    })
                    .build();

            builder.set(line + 1, linePos + 1 + (3 - lineLength / 2), button);
            linePos++;
            if (linePos >= lineLength) {
                lineLength -= 2;
                linePos = 0;
                line++;
            }
        }


        builder.fill(fillButton);
        builder.set(3, 4, closeButton);
        GooeyPage page = GooeyPage.builder()
                .template(builder.build())
                .title(TextUtils.gradientTextRGB(Color.red, Color.blue, "Shop"))
                .build();

        UIManager.openUIForcefully(player, page);
    }

    public static void purchaseItem(Runnable previousPage, ServerPlayerEntity player, Item item, float itemPrice, int amount, boolean buy) {
        ChestTemplate.Builder builder = ChestTemplate.builder(4);

        for (int i = 0; i < 4; i++) {
            final int amountOption = (int) Math.pow(4, i);

            GooeyButton add = GooeyButton.builder()
                    .display(new ItemBuilder(Items.GREEN_STAINED_GLASS_PANE)
                            .name("&aAdd " + amountOption)
                            .build()
                    )
                    .onClick((ac) -> {
                        purchaseItem(previousPage, player, item, itemPrice, amount + amountOption, buy);
                        ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                    })
                    .build();

            GooeyButton remove = GooeyButton.builder()
                    .display(new ItemBuilder(Items.RED_STAINED_GLASS_PANE)
                            .name("&cRemove  " + amountOption)
                            .build()
                    )
                    .onClick((ac) -> {
                        purchaseItem(previousPage, player, item, itemPrice, Math.max(amount - amountOption, 1), buy);
                        ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                    })
                    .build();

            builder.set(1, 5+i, add);
            builder.set(1, 3-i, remove);
        }

        builder.set(2, 4, GooeyButton.builder()
                .display(new ItemBuilder(Items.BARRIER)
                        .name("&4Back")
                        .build()
                )
                .onClick((ac) -> {
                    previousPage.run();
                    ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                })
                .build()
        );
        builder.set(1, 4, GooeyButton.builder()
                .display(new ItemBuilder(Items.BOOK)
                        .name("&aConfirm")
                        .build()
                )
                .onClick((ac) ->
                {
                    try {
                        Account a = EconomyService.instance().account(ac.getPlayer().getUuid()).get();
                        if (buy) {
                            EconomyTransaction result = a.withdraw(BigDecimal.valueOf(itemPrice * amount));

                            if (result.result() == EconomyResultType.SUCCESS) {
                                ac.getPlayer().giveItemStack(ItemBuilder.plain(item, amount));
                                ac.getPlayer().playSoundToPlayer(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.AMBIENT, 0.4f, 2.0f);
                                ac.getPlayer().sendMessage(
                                        Text.empty()
                                                .append(TextUtils.formatted("&7Successfully purchased &a" + amount + "x &8"))
                                                .append(item.getName())
                                                .formatted(Formatting.DARK_GRAY)
                                                .append(TextUtils.formatted("&7 for &a$" + itemPrice * amount))
                                );
                            } else if (result.result() == EconomyResultType.NOT_ENOUGH_FUNDS) {
                                ac.getPlayer().sendMessage(TextUtils.formatted("&cNot enough funds"));
                                ac.getPlayer().playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.AMBIENT, 0.4f, 1.0f);
                            }
                        } else {
                            // TODO
//                            ac.getPlayer().getInventory().removeStack()
                        }
                    } catch (Exception e) {

                    }
                })
                .build()
        );
        builder.fill(GuiUtils.background());
        GooeyPage page = GooeyPage.builder()
                .template(builder.build())
                .title(Text.empty()
                        .append(buy ? TextUtils.formatted("&9+" + amount + " &8") : TextUtils.formatted("&c-" + amount + " &8"))
                        .append(item.getName())
                        .append(TextUtils.formatted(" - &a$" + itemPrice * amount))
                )
                .build();
        UIManager.openUIForcefully(player, page);
    }
}
