package me.skizzme.cc.util;

import me.skizzme.cc.ItemStackExt;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Unit;

import java.util.ArrayList;

public class ItemBuilder {

    private ItemStack stack;

    public static ItemStack plain(ItemConvertible item, int amount) {
        ItemStack stack = new ItemStack(item);
        stack.setCount(amount);
        return stack;
    }

    public ItemBuilder(ItemConvertible item) {
        this.stack = new ItemStack(item);
        this.setUntakeable();
        this.hideTooltip();
    }

    public ItemBuilder name(String name) {
        this.stack.set(DataComponentTypes.ITEM_NAME, TextUtils.formatted(name));
        return this;
    }

    public ItemBuilder name(Text name) {
        this.stack.set(DataComponentTypes.ITEM_NAME, name);
        return this;
    }

    public ItemBuilder lore(String[] lore) {
        ArrayList<Text> lines = new ArrayList<>();
        for (String s : lore) {
            lines.add(TextUtils.formatted(s));
        }
        this.stack.set(DataComponentTypes.LORE, new LoreComponent(lines));
        return this;
    }

    public ItemBuilder setCustomData(NbtComponent comp) {
        this.stack.set(DataComponentTypes.CUSTOM_DATA, comp);
        return this;
    }

    public ItemBuilder setUntakeable() {
//        ((ItemStackExt) (Object) this.stack).setUntakeable(true);
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

//    public static boolean isItemUntakeable()

}
