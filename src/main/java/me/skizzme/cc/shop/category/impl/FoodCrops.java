package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import java.util.ArrayList;

public class FoodCrops extends Category {
    public FoodCrops() {
        super("Food and Crops", Items.WHEAT);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        for (ItemStack i : Registries.ITEM_GROUP.get(ItemGroups.FOOD_AND_DRINK).getDisplayStacks()) {
            if (i.getItem() == Items.ROTTEN_FLESH || i.getItem() == Items.GOLDEN_APPLE || i.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
                continue;
            }
            items.add(i.getItem());
        }

        for (ItemStack i : CobblemonItemGroups.getAGRICULTURE().getDisplayStacks()) {
            if (i.getItem() == CobblemonItems.ORAN_BERRY) {
                break;
            }
            items.add(i.getItem());
        }

        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }
}
