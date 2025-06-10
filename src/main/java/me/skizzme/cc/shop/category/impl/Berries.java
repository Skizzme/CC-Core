package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;

public class Berries extends Category {
    public Berries() {
        super("Agricultural Berries", CobblemonItems.PINAP_BERRY);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        for (Field f : CobblemonItems.class.getDeclaredFields()) {
            if (f.getName().toLowerCase().contains("berry")) {
                try {
                    items.add((ItemConvertible) f.get(null));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        items.sort(Comparator.comparing(a -> a.asItem().getName().getString()));
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }
}
