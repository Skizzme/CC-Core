package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public class MiscItems extends Category {
    public MiscItems() {
        super("Misc Items", Items.CHEST);
    }

    @Override
    public ArrayList<ItemLike> getItems() {
        return null;
    }
}
