package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.CobblemonItem;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.ArrayList;

public class Archaeology extends Category {
    public Archaeology() {
        super("Archaeology", CobblemonItems.OLD_AMBER_FOSSIL);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        for (ItemConvertible item : c_items) {
            if (item.asItem() == CobblemonItems.FOSSILIZED_DINO) {
                break;
            }
            items.add(item);
        }
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }

    private static final ItemConvertible[] c_items = {
            CobblemonItems.HELIX_FOSSIL,
            CobblemonItems.DOME_FOSSIL,
            CobblemonItems.OLD_AMBER_FOSSIL,
            CobblemonItems.ROOT_FOSSIL,
            CobblemonItems.CLAW_FOSSIL,
            CobblemonItems.SKULL_FOSSIL,
            CobblemonItems.ARMOR_FOSSIL,
            CobblemonItems.COVER_FOSSIL,
            CobblemonItems.PLUME_FOSSIL,
            CobblemonItems.JAW_FOSSIL,
            CobblemonItems.SAIL_FOSSIL,
            CobblemonItems.FOSSILIZED_BIRD,
            CobblemonItems.FOSSILIZED_FISH,
            CobblemonItems.FOSSILIZED_DRAKE,
            CobblemonItems.FOSSILIZED_DINO,
            CobblemonItems.TUMBLESTONE,
            CobblemonItems.BLACK_TUMBLESTONE,
            CobblemonItems.SKY_TUMBLESTONE,
            CobblemonItems.SMALL_BUDDING_TUMBLESTONE,
            CobblemonItems.SMALL_BUDDING_BLACK_TUMBLESTONE,
            CobblemonItems.SMALL_BUDDING_SKY_TUMBLESTONE,
            CobblemonItems.MEDIUM_BUDDING_TUMBLESTONE,
            CobblemonItems.MEDIUM_BUDDING_BLACK_TUMBLESTONE,
            CobblemonItems.MEDIUM_BUDDING_SKY_TUMBLESTONE,
            CobblemonItems.LARGE_BUDDING_TUMBLESTONE,
            CobblemonItems.LARGE_BUDDING_BLACK_TUMBLESTONE,
            CobblemonItems.LARGE_BUDDING_SKY_TUMBLESTONE,
            CobblemonItems.TUMBLESTONE_CLUSTER,
            CobblemonItems.BLACK_TUMBLESTONE_CLUSTER,
            CobblemonItems.SKY_TUMBLESTONE_CLUSTER,
            CobblemonItems.TUMBLESTONE_BLOCK,
            CobblemonItems.BLACK_TUMBLESTONE_BLOCK,
            CobblemonItems.SKY_TUMBLESTONE_BLOCK,
            CobblemonItems.BYGONE_SHERD,
            CobblemonItems.CAPTURE_SHERD,
            CobblemonItems.DOME_SHERD,
            CobblemonItems.HELIX_SHERD,
            CobblemonItems.NOSTALGIC_SHERD,
            CobblemonItems.SUSPICIOUS_SHERD,
            CobblemonItems.AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE,
            CobblemonItems.RELIC_COIN,
            CobblemonItems.RELIC_COIN_POUCH,
            CobblemonItems.RELIC_COIN_SACK,
            CobblemonItems.GILDED_CHEST,
            CobblemonItems.YELLOW_GILDED_CHEST,
            CobblemonItems.GREEN_GILDED_CHEST,
            CobblemonItems.BLUE_GILDED_CHEST,
            CobblemonItems.PINK_GILDED_CHEST,
            CobblemonItems.BLACK_GILDED_CHEST,
            CobblemonItems.WHITE_GILDED_CHEST,
            CobblemonItems.GIMMIGHOUL_CHEST,
            CobblemonItems.NORMAL_GEM,
            CobblemonItems.FIRE_GEM,
            CobblemonItems.WATER_GEM,
            CobblemonItems.GRASS_GEM,
            CobblemonItems.ELECTRIC_GEM,
            CobblemonItems.ICE_GEM,
            CobblemonItems.FIGHTING_GEM,
            CobblemonItems.POISON_GEM,
            CobblemonItems.GROUND_GEM,
            CobblemonItems.FLYING_GEM,
            CobblemonItems.PSYCHIC_GEM,
            CobblemonItems.BUG_GEM,
            CobblemonItems.ROCK_GEM,
            CobblemonItems.GHOST_GEM,
            CobblemonItems.DRAGON_GEM,
            CobblemonItems.DARK_GEM,
            CobblemonItems.STEEL_GEM,
            CobblemonItems.FAIRY_GEM
    };
}
