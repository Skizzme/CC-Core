package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;

import java.util.ArrayList;

public class Consumables extends Category {
    public Consumables() {
        super("Consumables", CobblemonItems.CARBOS);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        return null;
    }
}
