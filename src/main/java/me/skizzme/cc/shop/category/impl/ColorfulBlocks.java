package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.awt.*;
import java.util.ArrayList;

public class ColorfulBlocks extends Category {
    public ColorfulBlocks() {
        super(
                Text.empty()
                        .append(TextUtils.gradientTextHSB(Color.getHSBColor(0, 0.6f, 0.9f), Color.getHSBColor(0.99f, 0.6f, 0.9f), "Colorful"))
                        .append(TextUtils.formatted(" &7Blocks")),
                Items.RED_CONCRETE
        );
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        Registries.ITEM_GROUP.get(ItemGroups.COLORED_BLOCKS).getDisplayStacks().forEach((i) -> items.add(i.getItem()));
        return items;
    }
}
