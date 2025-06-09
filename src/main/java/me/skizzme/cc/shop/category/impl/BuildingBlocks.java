package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public class BuildingBlocks extends Category {
    public BuildingBlocks() {
        super("Building Blocks", Items.STONE_BRICKS);
    }

    @Override
    public ArrayList<ItemLike> getItems() {
        return null;
    }
}
