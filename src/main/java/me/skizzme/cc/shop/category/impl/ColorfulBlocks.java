package me.skizzme.cc.shop.category.impl;

import me.skizzme.cc.shop.category.Category;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Map;

public class ColorfulBlocks extends Category {
    public ColorfulBlocks() {
        super("Colorful Blocks", Items.RED_CONCRETE);
    }

    @Override
    public ArrayList<ItemLike> getItems() {
        ArrayList<ItemLike> items = new ArrayList<>();
        for (ResourceLocation id : BuiltInRegistries.CREATIVE_MODE_TAB.keySet()) {
            System.out.println(id);
        }
                //..getDisplayStacks().forEach((i) -> items.add(i.getItem()));
        return items;
    }
}
