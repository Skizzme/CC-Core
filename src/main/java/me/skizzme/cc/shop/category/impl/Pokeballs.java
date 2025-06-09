package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;

import java.util.ArrayList;

public class Pokeballs extends Category {
    public Pokeballs() {
        super("Pokeballs", CobblemonItems.MASTER_BALL);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        return null;
    }
}
