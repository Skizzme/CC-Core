package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;


import java.util.ArrayList;

public class FoodCrops extends Category {
    public FoodCrops() {
        super("Food & Crops", Items.WHEAT);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        return null;
    }
}
