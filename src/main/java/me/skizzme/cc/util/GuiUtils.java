package me.skizzme.cc.util;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class GuiUtils {

    public static GooeyButton background() {
        return GooeyButton.builder()
                .display(new ItemBuilder(Items.GRAY_STAINED_GLASS_PANE).hideAllTooltip().build())
                .build();
    }

}
