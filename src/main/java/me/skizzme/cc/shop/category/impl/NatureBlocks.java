package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import java.util.ArrayList;

public class NatureBlocks extends Category {
    public NatureBlocks() {
        super("Nature Blocks", Items.GRASS_BLOCK);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        Registries.ITEM_GROUP.get(ItemGroups.NATURAL).getDisplayStacks().forEach((i) -> {
            if (i.getItem() == Items.BEDROCK || i.getName().getString().toLowerCase().contains("ore") ||
                    i.getItem() == Items.SCULK_SHRIEKER || i.getItem() == Items.TURTLE_EGG || i.getItem() == Items.SNIFFER_EGG ||
                    i.getItem() == Items.BEE_NEST || i.getItem() == Items.HONEYCOMB_BLOCK
            )
                return;
            items.add(i.getItem());
        });
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }
}
