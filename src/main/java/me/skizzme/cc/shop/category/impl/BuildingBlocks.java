package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import java.util.ArrayList;

public class BuildingBlocks extends Category {
    public BuildingBlocks() {
        super("Building Blocks", Items.STONE_BRICKS);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        Registries.ITEM_GROUP.get(ItemGroups.BUILDING_BLOCKS).getDisplayStacks().forEach((i) -> {
            if (i.getName().getString().contains("shulker")) {
                return;
            }
            items.add(i.getItem());
        });
        return items;
    }
}
