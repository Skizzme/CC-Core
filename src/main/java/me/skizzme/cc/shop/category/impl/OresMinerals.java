package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static net.minecraft.item.Items.*;

public class OresMinerals extends Category {
    public OresMinerals() {
        super("Ores and Minerals", Items.DIAMOND);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>(List.of(
                IRON_BLOCK,
                GOLD_BLOCK,
                DIAMOND_BLOCK,
                EMERALD_BLOCK,
                NETHERITE_BLOCK,
                REDSTONE_BLOCK,
                LAPIS_BLOCK,
                AMETHYST_BLOCK,
                BUDDING_AMETHYST,
                IRON_INGOT,
                GOLD_INGOT,
                DIAMOND,
                EMERALD,
                NETHERITE_INGOT,
                REDSTONE,
                LAPIS_LAZULI,
                SMALL_AMETHYST_BUD,
                MEDIUM_AMETHYST_BUD,
                COAL_BLOCK,
                COAL
        ));

        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }
}
