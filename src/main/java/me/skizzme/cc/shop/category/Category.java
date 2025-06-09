package me.skizzme.cc.shop.category;

import net.minecraft.world.level.ItemLike;

import java.awt.event.ItemListener;
import java.util.ArrayList;

public abstract class Category {

    private String name;
    private ItemLike display;

    public Category(String name, ItemLike display) {
        this.name = name;
        this.display = display;
    }

    public abstract ArrayList<ItemLike> getItems();

    public String getName() {
        return name;
    }

    public ItemLike getDisplay() {
        return display;
    }
}
