package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class EvolutionItems extends Category {
    public EvolutionItems() {
        super("Evolution Items", CobblemonItems.WATER_STONE);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        return null;
    }
}
