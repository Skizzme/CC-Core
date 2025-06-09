package me.skizzme.cc.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Unit;

import java.util.ArrayList;

public class ItemBuilder {

    private ItemStack stack;

    public ItemBuilder(ItemConvertible item) {
        this.stack = new ItemStack(item);
        this.hideTooltip();
    }

    public ItemBuilder name(String name) {
        this.stack.set(DataComponentTypes.ITEM_NAME, Text.literal(name.replace("&", "§").replace("\\§", "\\&")));
        return this;
    }

    public ItemBuilder lore(String[] lore) {
        ArrayList<Text> lines = new ArrayList<>();
        for (String s : lore) {
            lines.add(Text.of(s));
        }
        this.stack.set(DataComponentTypes.LORE, new LoreComponent(lines));
        return this;
    }

    public ItemBuilder glow(boolean value) {
        this.stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, value);
        return this;
    }

    public ItemBuilder count(int count) {
        this.stack.setCount(count);
        return this;
    }

    public ItemBuilder hideTooltip() {
        this.stack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        return this;
    }

    public ItemBuilder hideAllTooltip() {
        this.stack.set(DataComponentTypes.HIDE_TOOLTIP, Unit.INSTANCE);
        return this;
    }

    public ItemStack build() {
        return this.stack;
    }

}
