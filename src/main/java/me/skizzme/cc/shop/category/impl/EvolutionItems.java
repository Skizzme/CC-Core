package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.CobblemonItem;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class EvolutionItems extends Category {
    public EvolutionItems() {
        super("Evolution Items", CobblemonItems.WATER_STONE);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        for (ItemStack i : CobblemonItemGroups.getEVOLUTION_ITEMS().getDisplayStacks()) {
            items.add(i.getItem());
        }
        return items;
    }
}
