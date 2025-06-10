package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;

import java.util.ArrayList;

public class Pokeballs extends Category {
    public Pokeballs() {
        super("Pokeballs", CobblemonItems.MASTER_BALL);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        for (ItemConvertible item : CobblemonItems.pokeBalls) {
            items.add(item);
        }
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }
}
