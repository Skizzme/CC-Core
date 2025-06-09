package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public class NatureBlocks extends Category {
    public NatureBlocks() {
        super("Nature Blocks", Items.GRASS_BLOCK);
    }

    @Override
    public ArrayList<ItemLike> getItems() {
        return null;
    }
}
