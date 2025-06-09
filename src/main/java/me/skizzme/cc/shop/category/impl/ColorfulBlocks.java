package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;

public class ColorfulBlocks extends Category {
    public ColorfulBlocks() {
        super(
                Text.empty()
                        .append(TextUtils.gradientTextHSB(Color.getHSBColor(0, 0.6f, 0.9f), Color.getHSBColor(0.99f, 0.6f, 0.9f), "Colorful"))
                        .append(TextUtils.formatted(" &8Blocks")),
                Items.RED_CONCRETE
        );
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        Registries.ITEM_GROUP.get(ItemGroups.COLORED_BLOCKS).getDisplayStacks().forEach((i) -> {
            System.out.println(i.getName().getString());
            if (i.getName().getString().contains("shulker")) {
                return;
            }
            items.add(i.getItem());
        });
        return items;
    }
}
