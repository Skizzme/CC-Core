package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.item.Items.*;
import static com.cobblemon.mod.common.CobblemonItems.*;

public class MiscItems extends Category {
    public MiscItems() {
        super("Misc Items", Items.CHEST);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>(List.of(
                BUCKET,
                LAVA_BUCKET,
                WATER_BUCKET,
                ROTTEN_FLESH,
                BONE,
                STRING,
                ARROW,
                SPIDER_EYE,
                GLOW_INK_SAC,
                GUNPOWDER,
                FIREWORK_ROCKET,
                ENDER_PEARL,
                SLIME_BALL,
                CHEST,
                FURNACE,
                ANVIL,
                ENCHANTING_TABLE,
                BELL,
                CAULDRON,
                BREWING_STAND,
                NETHER_STAR,
                NAUTILUS_SHELL,
                TOTEM_OF_UNDYING,
                BLAZE_ROD,
                BREEZE_ROD,
                TURTLE_SCUTE,
                COBWEB,
                TORCH,
                SOUL_TORCH,
                END_ROD,
                PAPER,
                PHANTOM_MEMBRANE,
                GHAST_TEAR,
                NAME_TAG,
                TRIAL_KEY,
                OMINOUS_TRIAL_KEY,
                NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                POKEROD_SMITHING_TEMPLATE,
                SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
                VEX_ARMOR_TRIM_SMITHING_TEMPLATE,
                WILD_ARMOR_TRIM_SMITHING_TEMPLATE,
                COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
                DUNE_ARMOR_TRIM_SMITHING_TEMPLATE,
                WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
                RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
                SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
                HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
                WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
                SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
                TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
                SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE,
                RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
                EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
                SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,
                AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE,
                FLOW_ARMOR_TRIM_SMITHING_TEMPLATE,
                BOLT_ARMOR_TRIM_SMITHING_TEMPLATE
        ));

        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        if (item.asItem() == STRING) {
            return false;
        }
        return true;
    }
}
