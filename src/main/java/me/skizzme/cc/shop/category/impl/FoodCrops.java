package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;

import java.util.ArrayList;

public class FoodCrops extends Category {
    public FoodCrops() {
        super("Food and Crops", Items.WHEAT);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        for (ItemConvertible i : c_items) {
            if (i.asItem() == Items.ROTTEN_FLESH || i.asItem() == Items.GOLDEN_APPLE || i.asItem() == Items.ENCHANTED_GOLDEN_APPLE) {
                continue;
            }
            if (i.asItem() == CobblemonItems.ORAN_BERRY) {
                break;
            }
            items.add(i.asItem());
        }

//        for (ItemStack i : CobblemonItemGroups.getAGRICULTURE().getDisplayStacks()) {
//            items.add(i.getItem());
//        }

        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }

    private static final ItemConvertible[] c_items = {
            Items.APPLE,
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.MELON_SLICE,
            Items.SWEET_BERRIES,
            Items.GLOW_BERRIES,
            Items.CHORUS_FRUIT,
            Items.CARROT,
            Items.GOLDEN_CARROT,
            Items.POTATO,
            Items.BAKED_POTATO,
            Items.POISONOUS_POTATO,
            Items.BEETROOT,
            Items.DRIED_KELP,
            Items.BEEF,
            Items.COOKED_BEEF,
            Items.PORKCHOP,
            Items.COOKED_PORKCHOP,
            Items.MUTTON,
            Items.COOKED_MUTTON,
            Items.CHICKEN,
            Items.COOKED_CHICKEN,
            Items.RABBIT,
            Items.COOKED_RABBIT,
            Items.COD,
            Items.COOKED_COD,
            Items.SALMON,
            Items.COOKED_SALMON,
            Items.TROPICAL_FISH,
            Items.PUFFERFISH,
            Items.BREAD,
            Items.COOKIE,
            Items.CAKE,
            Items.PUMPKIN_PIE,
            Items.ROTTEN_FLESH,
            Items.SPIDER_EYE,
            Items.MUSHROOM_STEW,
            Items.BEETROOT_SOUP,
            Items.RABBIT_STEW,
            Items.MILK_BUCKET,
            Items.HONEY_BOTTLE,
            Items.WHEAT,

            CobblemonItems.MEDICINAL_LEEK,
            CobblemonItems.BIG_ROOT,
            CobblemonItems.ENERGY_ROOT,
            CobblemonItems.REVIVAL_HERB,
            CobblemonItems.PEP_UP_FLOWER,
            CobblemonItems.MENTAL_HERB,
            CobblemonItems.POWER_HERB,
            CobblemonItems.WHITE_HERB,
            CobblemonItems.MIRROR_HERB,
            CobblemonItems.VIVICHOKE,
            CobblemonItems.VIVICHOKE_SEEDS,
            CobblemonItems.GALARICA_NUTS,

            CobblemonItems.RED_APRICORN,
            CobblemonItems.YELLOW_APRICORN,
            CobblemonItems.GREEN_APRICORN,
            CobblemonItems.BLUE_APRICORN,
            CobblemonItems.PINK_APRICORN,
            CobblemonItems.BLACK_APRICORN,
            CobblemonItems.WHITE_APRICORN,
            CobblemonItems.RED_APRICORN_SEED,
            CobblemonItems.YELLOW_APRICORN_SEED,
            CobblemonItems.GREEN_APRICORN_SEED,
            CobblemonItems.BLUE_APRICORN_SEED,
            CobblemonItems.PINK_APRICORN_SEED,
            CobblemonItems.BLACK_APRICORN_SEED,
            CobblemonItems.WHITE_APRICORN_SEED,

            CobblemonItems.RED_MINT_SEEDS,
            CobblemonItems.RED_MINT_LEAF,
            CobblemonItems.BLUE_MINT_SEEDS,
            CobblemonItems.BLUE_MINT_LEAF,
            CobblemonItems.CYAN_MINT_SEEDS,
            CobblemonItems.CYAN_MINT_LEAF,
            CobblemonItems.PINK_MINT_SEEDS,
            CobblemonItems.PINK_MINT_LEAF,
            CobblemonItems.GREEN_MINT_SEEDS,
            CobblemonItems.GREEN_MINT_LEAF,
            CobblemonItems.WHITE_MINT_SEEDS,
            CobblemonItems.WHITE_MINT_LEAF,

            CobblemonItems.GROWTH_MULCH,
            CobblemonItems.RICH_MULCH,
            CobblemonItems.SURPRISE_MULCH,
            CobblemonItems.LOAMY_MULCH,
            CobblemonItems.COARSE_MULCH,
            CobblemonItems.PEAT_MULCH,
            CobblemonItems.HUMID_MULCH,
            CobblemonItems.SANDY_MULCH,
            CobblemonItems.MULCH_BASE
    };
}
