package me.skizzme.cc.shop;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.ButtonClick;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import ca.landonjw.gooeylibs2.api.template.types.InventoryTemplate;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.listeners.TransactionListener;
import me.skizzme.cc.shop.category.Category;
import me.skizzme.cc.shop.category.impl.*;
import me.skizzme.cc.util.ConfigUtils;
import me.skizzme.cc.util.GuiUtils;
import me.skizzme.cc.util.ItemBuilder;
import me.skizzme.cc.util.TextUtils;
import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.events.EconomyTransactionEvent;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.impactdev.impactor.api.economy.transactions.details.EconomyResultType;
import net.impactdev.impactor.api.events.ImpactorEventBus;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public class Shop {

    private static JsonObject prices;
    private static final String PRICE_CONFIG_PATH = "shop_prices.json";

    private static final Category[] categories = {
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

    public static void loadConfig() {
        CCCore.LOGGER.info("Loading shop prices");
        try {
            prices = ConfigUtils.readFileThrowing(PRICE_CONFIG_PATH);
            CCCore.LOGGER.info("Loaded " + prices.size() + " shop prices");
            if (updateConfig()) {
                CCCore.LOGGER.info("Updated shop prices to new version");
            }
        } catch (IOException e) {
            CCCore.LOGGER.info("Shop prices not found, initializing default prices");
            prices = new JsonObject();
            for (Category c : categories) {
                ArrayList<ItemConvertible> items = c.getItems();
                if (items == null) continue;

                for (ItemConvertible i : items) {
                    addItemToObject(prices, i.asItem().getTranslationKey(), 1.0f);
                }
            }
            ConfigUtils.writeFile(PRICE_CONFIG_PATH, prices);
            CCCore.LOGGER.info("Initialized " + prices.size() + " shop prices");
        }
    }

    private static boolean updateConfig() {
        JsonObject updatedPrices = new JsonObject();
        boolean shouldUpdate = false;
        for (Map.Entry<String, JsonElement> e : prices.entrySet()) {
            String key = e.getKey();
            JsonElement value = e.getValue();
            if (!value.isJsonObject()) {
                shouldUpdate = true;
                addItemToObject(updatedPrices, key, value.getAsFloat());
            }
        }
        if (shouldUpdate) {
            prices = updatedPrices;
            ConfigUtils.writeFile(PRICE_CONFIG_PATH, prices);
        }
        return shouldUpdate;
    }

    private static void addItemToObject(JsonObject obj, String key, float price) {
        JsonObject itemObject = new JsonObject();
        itemObject.addProperty("prices", price);
        itemObject.addProperty("sell_percent", 0.1);
        obj.add(key, itemObject);
    }

    public static float getItemPrice(ItemConvertible item) {
        JsonElement result = prices.get(item.asItem().getTranslationKey());
        if (result == null || result.isJsonNull()) {
//            prices.addProperty(item.asItem().getTranslationKey(), 1.0);
            addItemToObject(prices, item.asItem().getTranslationKey(), 1.0f);
            ConfigUtils.writeFile(PRICE_CONFIG_PATH, prices);
            return getItemPrice(item);
        }
        return result.getAsFloat();
    }

    public static void display(ServerPlayerEntity player) {

        GooeyButton closeButton = GooeyButton.builder()
                .display(new ItemBuilder(Items.BARRIER).name("&cClose").build())
                .onClick((ac) -> {
                    if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK) {
                        return;
                    }
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
                        if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK) {
                            return;
                        }
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


        builder.fill(GuiUtils.background());
        builder.set(3, 4, closeButton);
        GooeyPage page = GooeyPage.builder()
                .template(builder.build())
                .title(TextUtils.gradientTextRGB(Color.red, Color.blue, "Shop"))
                .build();

        UIManager.openUIForcefully(player, page);
    }

    public static void itemTransaction(Runnable previousPage, ServerPlayerEntity player, Item item, float itemPrice, int amount, boolean buy) {
        ChestTemplate.Builder builder = ChestTemplate.builder(4);
        float sellPercent = 0.5f;

        for (int i = 0; i < 4; i++) {
            final int amountOption = (int) Math.pow(4, i);

            GooeyButton add = GooeyButton.builder()
                    .display(new ItemBuilder(Items.GREEN_STAINED_GLASS_PANE)
                            .name("&aAdd " + amountOption)
                            .count(amountOption)
                            .build()
                    )
                    .onClick((ac) -> {
                        if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK) {
                            return;
                        }
                        int newAmount = amount + amountOption;
                        if (!buy) {
                            newAmount = Math.min(newAmount, player.getInventory().count(item));
                        }
                        itemTransaction(previousPage, player, item, itemPrice, newAmount, buy);
                        ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                    })
                    .build();

            GooeyButton remove = GooeyButton.builder()
                    .display(new ItemBuilder(Items.RED_STAINED_GLASS_PANE)
                            .name("&cRemove  " + amountOption)
                            .count(amountOption)
                            .build()
                    )
                    .onClick((ac) -> {
                        if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK) {
                            return;
                        }

                        itemTransaction(previousPage, player, item, itemPrice, Math.max(amount - amountOption, 1), buy);
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
                    if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK) {
                        return;
                    }
                    previousPage.run();
                    ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                })
                .build()
        );
        builder.set(1, 4, GooeyButton.builder()
                .display(new ItemBuilder(item)
                        .name("&aConfirm")
                        .lore(new String[] {
                                "",
                                "&7Amount: " + (buy ? "&9" : "&c") + CCCore.INT_FORMAT.format(amount),
                                "&7Total Price: &a" + CCCore.MONEY_FORMAT.format(amount * itemPrice),
                                "&7Item Price: &e" + CCCore.MONEY_FORMAT.format(itemPrice),
                                ""
                        })
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
                                                .append(TextUtils.formatted("&7Successfully &9purchased &a" + CCCore.INT_FORMAT.format(amount) + "x &8"))
                                                .append(item.getName())
                                                .formatted(Formatting.DARK_GRAY)
                                                .append(TextUtils.formatted("&7 for &a" + CCCore.MONEY_FORMAT.format(itemPrice * amount)))
                                );
                            } else if (result.result() == EconomyResultType.NOT_ENOUGH_FUNDS) {
                                ac.getPlayer().sendMessage(TextUtils.formatted("&cNot enough funds"));
                                ac.getPlayer().playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.AMBIENT, 0.4f, 1.0f);
                            }
                        } else {
                            // TODO
//                            ac.getPlayer().getInventory().removeStack()
                            int removed = Inventories.remove(ac.getPlayer().getInventory(), (s) -> s.getItem() == item, amount, false);
                            if (removed == 0) {
                                return;
                            }
                            float deposit = itemPrice * sellPercent * removed;
                            EconomyTransaction result = a.deposit(BigDecimal.valueOf(deposit));
                            ac.getPlayer().sendMessage(
                                    Text.empty()
                                            .append(TextUtils.formatted("&7Successfully &csold &a" + CCCore.INT_FORMAT.format(removed) + "x &8"))
                                            .append(item.getName())
                                            .formatted(Formatting.DARK_GRAY)
                                            .append(TextUtils.formatted("&7 for &a" + CCCore.MONEY_FORMAT.format(deposit)))
                            );
                            ac.getPlayer().getInventory().updateItems();
                            itemTransaction(previousPage, player, item, itemPrice, amount, buy);
                        }
                    } catch (Exception e) {

                    }
                })
                .build()
        );
        builder.fill(GuiUtils.background());
        GooeyPage page = GooeyPage.builder()
                .template(builder.build())
//                .title(Text.empty()
//                        .append(buy ? TextUtils.formatted("&9+" + CCCore.INT_FORMAT.format(amount) + " &8") : TextUtils.formatted("&c-" + CCCore.INT_FORMAT.format(amount) + " &8"))
//                        .append(item.getName())
//                        .append(TextUtils.formatted(" - &a" + CCCore.MONEY_FORMAT.format(itemPrice * amount)))
//                )
                .title(Text.empty()
                        .append((buy ? TextUtils.formatted("&9+" + CCCore.INT_FORMAT.format(amount)) : TextUtils.formatted("&c-" + CCCore.INT_FORMAT.format(amount))))
                        .append(TextUtils.formatted(" &8- &a" + CCCore.MONEY_FORMAT.format(itemPrice * amount)))
                )
                .build();

        UIManager.openUIForcefully(player, page);
    }
}
