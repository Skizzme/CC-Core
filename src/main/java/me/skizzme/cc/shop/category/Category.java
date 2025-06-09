package me.skizzme.cc.shop.category;

import net.minecraft.item.ItemConvertible;

import java.awt.event.ItemListener;
import java.util.ArrayList;

public abstract class Category {

    private String name;
    private ItemConvertible display;

    public Category(String name, ItemConvertible display) {
        this.name = name;
        this.display = display;
    }

    public abstract ArrayList<ItemConvertible> getItems();

    public String getName() {
        return name;
    }

    public ItemConvertible getDisplay() {
        return display;
    }
}
