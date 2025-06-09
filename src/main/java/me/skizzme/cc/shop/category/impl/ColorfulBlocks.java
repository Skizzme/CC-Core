package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.Map;

public class ColorfulBlocks extends Category {
    public ColorfulBlocks() {
        super("Colorful Blocks", Items.RED_CONCRETE);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        Registries.ITEM_GROUP.get(ItemGroups.COLORED_BLOCKS).getDisplayStacks().forEach((i) -> items.add(i.getItem()));
        return items;
    }
}
