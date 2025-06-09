package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;

public class Berries extends Category {
    public Berries() {
        super("Agricultural Berries", CobblemonItems.PINAP_BERRY);
    }

    @Override
    public ArrayList<ItemLike> getItems() {
        return null;
    }
}
