package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.Item;
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
            Item item = i.getItem();
            if (i.getName().getString().contains("shulker") ||
                    item == Items.COAL_BLOCK || item == Items.COPPER_BLOCK || item == Items.REDSTONE_BLOCK ||
                    item == Items.LAPIS_BLOCK || item == Items.DIAMOND_BLOCK || item == Items.GOLD_BLOCK || item == Items.EMERALD_BLOCK ||
                    item == Items.NETHERITE_BLOCK || item == Items.IRON_BLOCK
            ) {
                return;
            }
            items.add(i.getItem());
        });
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return false;
    }
}
