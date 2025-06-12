package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.CobblemonItem;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class HeldItems extends Category {
    public HeldItems() {
        super("Held Items", CobblemonItems.LOADED_DICE);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        for (ItemConvertible i : c_items) {
            items.add(i.asItem());
            if (i.asItem() == CobblemonItems.ZOOM_LENS) {
                break;
            }
        }
        return items;
    }

    @Override
    public boolean isSellable(ItemConvertible item) {
        return true;
    }
    private static final ItemConvertible[] c_items = {
            CobblemonItems.ABILITY_SHIELD,
            CobblemonItems.ABSORB_BULB,
            CobblemonItems.AIR_BALLOON,
            CobblemonItems.ASSAULT_VEST,
            CobblemonItems.BIG_ROOT,
            CobblemonItems.BINDING_BAND,
            CobblemonItems.BLACK_BELT,
            CobblemonItems.BLACK_GLASSES,
            CobblemonItems.BLACK_SLUDGE,
            CobblemonItems.BLUNDER_POLICY,
            CobblemonItems.BRIGHT_POWDER,
            CobblemonItems.CELL_BATTERY,
            CobblemonItems.CHARCOAL,
            CobblemonItems.CHOICE_BAND,
            CobblemonItems.CHOICE_SCARF,
            CobblemonItems.CHOICE_SPECS,
            CobblemonItems.CLEANSE_TAG,
            CobblemonItems.COVERT_CLOAK,
            CobblemonItems.DAMP_ROCK,
            CobblemonItems.DEEP_SEA_SCALE,
            CobblemonItems.DEEP_SEA_TOOTH,
            CobblemonItems.DESTINY_KNOT,
            CobblemonItems.DRAGON_FANG,
            CobblemonItems.EJECT_BUTTON,
            CobblemonItems.EJECT_PACK,
            CobblemonItems.EVERSTONE,
            CobblemonItems.EVIOLITE,
            CobblemonItems.EXPERT_BELT,
            CobblemonItems.EXP_SHARE,
            CobblemonItems.FAIRY_FEATHER,
            CobblemonItems.FLAME_ORB,
            CobblemonItems.FLOAT_STONE,
            CobblemonItems.FOCUS_BAND,
            CobblemonItems.FOCUS_SASH,
            CobblemonItems.HARD_STONE,
            CobblemonItems.HEAT_ROCK,
            CobblemonItems.HEAVY_DUTY_BOOTS,
            CobblemonItems.ICY_ROCK,
            CobblemonItems.IRON_BALL,
            CobblemonItems.KINGS_ROCK,
            CobblemonItems.LEFTOVERS,
            CobblemonItems.LIFE_ORB,
            CobblemonItems.LIGHT_BALL,
            CobblemonItems.LIGHT_CLAY,
            CobblemonItems.LOADED_DICE,
            CobblemonItems.LUCKY_EGG,
            CobblemonItems.MAGNET,
            CobblemonItems.MENTAL_HERB,
            CobblemonItems.METAL_COAT,
            CobblemonItems.METAL_POWDER,
            CobblemonItems.METRONOME,
            CobblemonItems.MIRACLE_SEED,
            CobblemonItems.MIRROR_HERB,
            CobblemonItems.MUSCLE_BAND,
            CobblemonItems.MYSTIC_WATER,
            CobblemonItems.NEVER_MELT_ICE,
            CobblemonItems.POISON_BARB,
            CobblemonItems.POWER_ANKLET,
            CobblemonItems.POWER_BAND,
            CobblemonItems.POWER_BELT,
            CobblemonItems.POWER_BRACER,
            CobblemonItems.POWER_LENS,
            CobblemonItems.POWER_WEIGHT,
            CobblemonItems.POWER_HERB,
            CobblemonItems.PROTECTIVE_PADS,
            CobblemonItems.PUNCHING_GLOVE,
            CobblemonItems.QUICK_CLAW,
            CobblemonItems.QUICK_POWDER,
            CobblemonItems.RAZOR_CLAW,
            CobblemonItems.RAZOR_FANG,
            CobblemonItems.RED_CARD,
            CobblemonItems.RING_TARGET,
            CobblemonItems.ROCKY_HELMET,
            CobblemonItems.ROOM_SERVICE,
            CobblemonItems.SAFETY_GOGGLES,
            CobblemonItems.SCOPE_LENS,
            CobblemonItems.SHARP_BEAK,
            CobblemonItems.SHED_SHELL,
            CobblemonItems.SHELL_BELL,
            CobblemonItems.SILK_SCARF,
            CobblemonItems.SILVER_POWDER,
            CobblemonItems.SMOKE_BALL,
            CobblemonItems.SMOOTH_ROCK,
            CobblemonItems.SOFT_SAND,
            CobblemonItems.SOOTHE_BELL,
            CobblemonItems.SPELL_TAG,
            CobblemonItems.STICKY_BARB,
            CobblemonItems.TERRAIN_EXTENDER,
            CobblemonItems.THROAT_SPRAY,
            CobblemonItems.TOXIC_ORB,
            CobblemonItems.TWISTED_SPOON,
            CobblemonItems.UTILITY_UMBRELLA,
            CobblemonItems.WEAKNESS_POLICY,
            CobblemonItems.WHITE_HERB,
            CobblemonItems.WIDE_LENS,
            CobblemonItems.WISE_GLASSES,
            CobblemonItems.ZOOM_LENS,

            CobblemonItems.MEDICINAL_LEEK,
            Items.BONE,
            Items.SNOWBALL,

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
            CobblemonItems.FAIRY_GEM,
            CobblemonItems.ELECTRIC_SEED,
            CobblemonItems.GRASSY_SEED,
            CobblemonItems.MISTY_SEED,
            CobblemonItems.PSYCHIC_SEED
    };
}
