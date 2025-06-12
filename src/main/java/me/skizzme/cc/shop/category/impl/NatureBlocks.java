package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import static net.minecraft.item.Items.*;

import java.util.ArrayList;

public class NatureBlocks extends Category {
    public NatureBlocks() {
        super("Nature Blocks", Items.GRASS_BLOCK);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        Registries.ITEM_GROUP.get(ItemGroups.NATURAL).getDisplayStacks().forEach((i) -> {
            Item item = i.getItem();
            if (item == BEDROCK || i.getName().getString().toLowerCase().contains("ore") || item == SCULK_SHRIEKER || item == TURTLE_EGG ||
                    item == SNIFFER_EGG || item == BEE_NEST || item == HONEYCOMB_BLOCK || item == RAW_IRON_BLOCK ||
                    item == RAW_COPPER_BLOCK || item == ANCIENT_DEBRIS || item == RAW_GOLD_BLOCK
            )
                return;
            items.add(item);
        });
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }
}
