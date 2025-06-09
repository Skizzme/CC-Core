package me.skizzme.cc.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;

public class ItemBuilder {

    private ItemStack stack;

    public ItemBuilder(ItemLike item) {
        this.stack = new ItemStack(item);
        this.hideTooltip();
    }

    public ItemBuilder name(String name) {
        this.stack.set(DataComponents.ITEM_NAME, Component.literal(name.replace("&", "§").replace("\\§", "\\&")));
        return this;
    }

    public ItemBuilder lore(String[] lore) {
        ArrayList<Component> lines = new ArrayList<>();
        for (String s : lore) {
            lines.add(Component.literal(s));
        }
        this.stack.set(DataComponents.LORE, new ItemLore(lines));
        return this;
    }

    public ItemBuilder glow(boolean value) {
        this.stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, value);
        return this;
    }

    public ItemBuilder count(int count) {
        this.stack.setCount(count);
        return this;
    }

    public ItemBuilder hideTooltip() {
        this.stack.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        return this;
    }

    public ItemBuilder hideAllTooltip() {
        this.stack.set(DataComponents.HIDE_TOOLTIP, Unit.INSTANCE);
        return this;
    }

    public ItemStack build() {
        return this.stack;
    }

}
