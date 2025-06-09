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
            items.add(i.getItem());
        });
        return items;
    }
}
