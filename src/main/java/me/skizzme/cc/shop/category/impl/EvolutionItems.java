package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.CobblemonItem;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class EvolutionItems extends Category {
    public EvolutionItems() {
        super("Evolution Items", CobblemonItems.WATER_STONE);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        for (ItemConvertible i : c_items) {
            items.add(i.asItem());
        }
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }

    private static final ItemConvertible[] c_items = {
            CobblemonItems.FIRE_STONE,
            CobblemonItems.WATER_STONE,
            CobblemonItems.THUNDER_STONE,
            CobblemonItems.LEAF_STONE,
            CobblemonItems.MOON_STONE,
            CobblemonItems.SUN_STONE,
            CobblemonItems.SHINY_STONE,
            CobblemonItems.DUSK_STONE,
            CobblemonItems.DAWN_STONE,
            CobblemonItems.ICE_STONE,
            CobblemonItems.LINK_CABLE,
            CobblemonItems.KINGS_ROCK,
            CobblemonItems.GALARICA_CUFF,
            CobblemonItems.GALARICA_WREATH,
            CobblemonItems.METAL_COAT,
            CobblemonItems.BLACK_AUGURITE,
            CobblemonItems.PROTECTOR,
            CobblemonItems.OVAL_STONE,
            CobblemonItems.DRAGON_SCALE,
            CobblemonItems.ELECTIRIZER,
            CobblemonItems.MAGMARIZER,
            CobblemonItems.UPGRADE,
            CobblemonItems.DUBIOUS_DISC,
            CobblemonItems.RAZOR_FANG,
            CobblemonItems.RAZOR_CLAW,
            CobblemonItems.PEAT_BLOCK,
            CobblemonItems.PRISM_SCALE,
            CobblemonItems.REAPER_CLOTH,
            CobblemonItems.DEEP_SEA_TOOTH,
            CobblemonItems.DEEP_SEA_SCALE,
            CobblemonItems.SACHET,
            CobblemonItems.WHIPPED_DREAM,
            CobblemonItems.TART_APPLE,
            CobblemonItems.SWEET_APPLE,
            CobblemonItems.CRACKED_POT,
            CobblemonItems.CHIPPED_POT,
            CobblemonItems.MASTERPIECE_TEACUP,
            CobblemonItems.UNREMARKABLE_TEACUP,
            CobblemonItems.STRAWBERRY_SWEET,
            CobblemonItems.LOVE_SWEET,
            CobblemonItems.BERRY_SWEET,
            CobblemonItems.CLOVER_SWEET,
            CobblemonItems.FLOWER_SWEET,
            CobblemonItems.STAR_SWEET,
            CobblemonItems.RIBBON_SWEET,
            CobblemonItems.AUSPICIOUS_ARMOR,
            CobblemonItems.MALICIOUS_ARMOR,
            CobblemonItems.SHELL_HELMET
    };
}
