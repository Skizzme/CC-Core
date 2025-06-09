package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class OresMinerals extends Category {
    public OresMinerals() {
        super("Ores & Minerals", Items.DIAMOND);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        return null;
    }
}
