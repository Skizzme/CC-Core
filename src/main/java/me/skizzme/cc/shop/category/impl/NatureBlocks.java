package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class NatureBlocks extends Category {
    public NatureBlocks() {
        super("Nature Blocks", Items.GRASS_BLOCK);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        return null;
    }
}
