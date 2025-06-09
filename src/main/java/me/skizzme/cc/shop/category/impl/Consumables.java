package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class Consumables extends Category {
    public Consumables() {
        super("Consumables", CobblemonItems.CARBOS);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        boolean add = false;
        for (ItemStack i : CobblemonItemGroups.getCONSUMABLES().getDisplayStacks()) {
            if (i.getItem() == CobblemonItems.POTION) {
                add = true;
            }
            if (add) {
                items.add(i.getItem());
            }
            if (i.getItem() == CobblemonItems.PP_MAX) {
                break;
            }
        }
        return items;
    }
}
