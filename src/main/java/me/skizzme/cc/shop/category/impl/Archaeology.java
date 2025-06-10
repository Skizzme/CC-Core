package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.ArrayList;

public class Archaeology extends Category {
    public Archaeology() {
        super("Archaeology", CobblemonItems.OLD_AMBER_FOSSIL);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        for (ItemStack i : CobblemonItemGroups.getARCHAEOLOGY().getDisplayStacks()) {
            items.add(i.getItem());
            if (i.getItem() == CobblemonItems.FOSSILIZED_DINO) {
                break;
            }
        }
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }
}
