package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

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
