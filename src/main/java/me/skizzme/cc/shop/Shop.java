package me.skizzme.cc.shop;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import me.skizzme.cc.shop.category.Category;
import me.skizzme.cc.shop.category.impl.*;
import me.skizzme.cc.util.ItemBuilder;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Shop {

    public static void display(ServerPlayerEntity player) {
        Category[] categories = {
                new BuildingBlocks(),
                new ColorfulBlocks(),
                new NatureBlocks(),
                new FoodCrops(),
                new OresMinerals(),
                new MiscItems(),
                new Berries(),
                new EvolutionItems(),
                new Consumables(),
                new HeldItems(),
                new Pokeballs(),
                new Archaeology()
        };

        GooeyButton fillButton = GooeyButton.builder()
                .display(new ItemBuilder(Items.GRAY_STAINED_GLASS_PANE).hideAllTooltip().build())
                .build();

        GooeyButton closeButton = GooeyButton.builder()
                .display(new ItemBuilder(Items.BARRIER).name("&cClose").build())
                .onClick(() -> UIManager.closeUI(player))
                .build();

        ChestTemplate.Builder builder = ChestTemplate.builder(5);

        int lineLength = 7;
        int line = 0;
        int linePos = 0;
        for (int i = 0; i < categories.length; i++) {

            Category c = categories[i];

            ItemStack stack = new ItemBuilder(c.getDisplay())
                            .name(c.getName())
                            .lore(new String[]{""})
                            .build();

            GooeyButton button = GooeyButton.builder()
                    .display(stack)
                    .build();

            builder.set(line + 1, linePos + 1 + (3 - lineLength / 2), button);
            linePos++;
            if (linePos >= lineLength) {
                lineLength -= 2;
                linePos = 0;
                line++;
            }
        }


        builder.fill(fillButton);
        builder.set(3, 4, closeButton);
        GooeyPage page = GooeyPage.builder()
                .template(builder.build())
                .build();

        UIManager.openUIForcefully(player, page);
    }
}
