package me.skizzme.cc.shop;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.ButtonClick;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.shop.category.Category;
import me.skizzme.cc.shop.category.impl.*;
import me.skizzme.cc.util.*;
import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.impactdev.impactor.api.economy.transactions.details.EconomyResultType;
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
import java.util.HashSet;
import java.util.Map;

public class Shop {

    private static JsonObject prices;
    private static final String PRICE_CONFIG_PATH = "shop_prices.json";
    public static final HashSet<ServerPlayerEntity> TRANSACTION_NOTIFS = new HashSet<>();

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
        itemObject.addProperty("price", price);
        itemObject.addProperty("sell_percent", 0.1);
        obj.add(key, itemObject);
    }

    public static ItemInfo getItemInfo(ItemConvertible item) {
        JsonElement result = prices.get(item.asItem().getTranslationKey());
        if (result == null || result.isJsonNull()) {
//            prices.addProperty(item.asItem().getTranslationKey(), 1.0);
            addItemToObject(prices, item.asItem().getTranslationKey(), 1.0f);
            ConfigUtils.writeFile(PRICE_CONFIG_PATH, prices);
            return getItemInfo(item);
        }
        JsonObject obj = result.getAsJsonObject();
        return new ItemInfo(obj.get("price").getAsFloat(), obj.get("sell_percent").getAsFloat());
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

    public static void itemTransaction(Runnable previousPage, ServerPlayerEntity player, Item item, ItemInfo itemInfo, int amount, boolean buy) {
        ChestTemplate.Builder builder = ChestTemplate.builder(4);

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
                        itemTransaction(previousPage, player, item, itemInfo, newAmount, buy);
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

                        itemTransaction(previousPage, player, item, itemInfo, Math.max(amount - amountOption, 1), buy);
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
                                        "&7Total Price: &a" + CCCore.MONEY_FORMAT.format(amount * itemInfo.getPrice()),
                                        "&7Item Price: &e" + CCCore.MONEY_FORMAT.format(itemInfo.getPrice()),
                                        ""
                                })
                                .build()
                        )
                        .onClick((ac) ->
                        {
                            completeTransaction(buy, ac.getPlayer(), item, itemInfo, amount);
                            itemTransaction(previousPage, player, item, itemInfo, amount, buy);
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
                        .append(TextUtils.formatted(" &8- &a" + CCCore.MONEY_FORMAT.format(itemInfo.getPrice() * amount)))
                )
                .build();

        UIManager.openUIForcefully(player, page);
    }
    
    public static void completeTransaction(boolean buy, ServerPlayerEntity player, Item item, ItemInfo itemInfo, int amount) {
        try {
            Account a = EconomyService.instance().account(player.getUuid()).get();
            if (buy) {
                EconomyTransaction result = a.withdraw(BigDecimal.valueOf(itemInfo.getPrice() * amount));

                if (result.result() == EconomyResultType.SUCCESS) {
                    player.giveItemStack(ItemBuilder.plain(item, amount));
                    player.playSoundToPlayer(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.AMBIENT, 0.4f, 2.0f);
                    player.sendMessage(
                            Text.empty()
                                    .append(TextUtils.formatted("&7Successfully &9purchased &a" + CCCore.INT_FORMAT.format(amount) + "x &8"))
                                    .append(item.getName())
                                    .formatted(Formatting.DARK_GRAY)
                                    .append(TextUtils.formatted("&7 for &a" + CCCore.MONEY_FORMAT.format(itemInfo.getPrice() * amount)))
                    );
                } else if (result.result() == EconomyResultType.NOT_ENOUGH_FUNDS) {
                    player.sendMessage(TextUtils.formatted("&cNot enough funds"));
                    player.playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.AMBIENT, 0.4f, 1.0f);
                }
            } else {
                // TODO
//                            player.getInventory().removeStack()
                int removed = Inventories.remove(player.getInventory(), (s) -> s.getItem() == item, amount, false);
                if (removed == 0) {
                    return;
                }
                float deposit = itemInfo.getPrice() * itemInfo.getSellPercent() * removed;
                EconomyTransaction result = a.deposit(BigDecimal.valueOf(deposit));
                player.sendMessage(
                        Text.empty()
                                .append(TextUtils.formatted("&7Successfully &csold &a" + CCCore.INT_FORMAT.format(removed) + "x &8"))
                                .append(item.getName())
                                .formatted(Formatting.DARK_GRAY)
                                .append(TextUtils.formatted("&7 for &a" + CCCore.MONEY_FORMAT.format(deposit)))
                );
                player.getInventory().updateItems();
//                itemTransaction(previousPage, player, item, itemInfo, amount, buy);
            }
        } catch (Exception e) {

        }
        logTransaction(buy, player, item, itemInfo, amount);
    }

    public static void logTransaction(boolean buy, ServerPlayerEntity player, Item item, ItemInfo itemInfo, int amount) {
        float price = amount * (buy ? itemInfo.getPrice() : (itemInfo.getPrice() * itemInfo.getSellPercent()));
        FileUtils.appendFile("shop_transactions.txt", "[" + TimeUtils.getTimeDefault() + "] " + player.getGameProfile().getName() + " " + (buy ? "purchased" : "sold") + " " + amount + " of " + item.getTranslationKey() + " for " + price + "\n");

        Text msg = Text.empty()
                .append(CCCore.PREFIX)
                .append(" ")
                .append(Text.empty().append(player.getName()).formatted(Formatting.GREEN))
                .append(TextUtils.formatted(buy ? " &7purchased " : " &7sold "))
                .append(TextUtils.formatted("&9" + amount +" &7of "))
                .append(Text.empty().append(item.getName()).formatted(Formatting.DARK_GRAY))
                .append(TextUtils.formatted("&7 for &a" + CCCore.MONEY_FORMAT.format(price)));

        for (ServerPlayerEntity notif : TRANSACTION_NOTIFS) {
            notif.sendMessage(msg);
        }
    }

    public static class ItemInfo {
        private float price, sell_percent;

        public ItemInfo(float price, float sell_percent) {
            this.price = price;
            this.sell_percent = sell_percent;
        }

        public float getPrice() {
            return price;
        }

        public float getSellPercent() {
            return sell_percent;
        }
    }
}