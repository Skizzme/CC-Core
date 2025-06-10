package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.CobblemonItem;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class HeldItems extends Category {
    public HeldItems() {
        super("Held Items", CobblemonItems.LOADED_DICE);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        for (ItemStack i : CobblemonItemGroups.getHELD_ITEMS().getDisplayStacks()) {
            items.add(i.getItem());
            if (i.getItem() == CobblemonItems.ZOOM_LENS) {
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
