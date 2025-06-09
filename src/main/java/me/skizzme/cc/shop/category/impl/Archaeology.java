package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;

import java.util.ArrayList;

public class Archaeology extends Category {
    public Archaeology() {
        super("Archaeology", CobblemonItems.OLD_AMBER_FOSSIL);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        return null;
    }
}
