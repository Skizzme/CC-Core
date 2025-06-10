package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;

public class OresMinerals extends Category {
    public OresMinerals() {
        super("Ores and Minerals", Items.DIAMOND);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
//        for (Field f : Items.class.getDeclaredFields()) {
//            try {
//                Item item = (Item) f.get(null);
//                String checkName = item.getName().getString().toLowerCase();
//                if (checkName.contains("coal") || checkName.contains("iron") || checkName.contains("copper") || checkName.contains("iron") ||
//                        checkName.contains("lapis") || checkName.contains("diamond") || checkName.contains("emerald") || checkName.contains("redstone")
//                ) {
//                    if (checkName.contains("ore")) {
//                        item.
//                        items.add(item);
//                    }
//                }
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        items.sort(Comparator.comparing(a -> a.asItem().getName().getString()));
        return items;
    }
}
