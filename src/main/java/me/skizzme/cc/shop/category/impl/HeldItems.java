package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public class HeldItems extends Category {
    public HeldItems() {
        super("Held Items", CobblemonItems.LOADED_DICE);
    }

    @Override
    public ArrayList<ItemLike> getItems() {
        return null;
    }
}
