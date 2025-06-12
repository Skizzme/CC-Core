package me.skizzme.cc.shop.category.impl;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import me.skizzme.cc.shop.category.Category;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class Consumables extends Category {
    public Consumables() {
        super("Consumables", CobblemonItems.CARBOS);
    }

    @Override
    public ArrayList<ItemConvertible> getItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();

        boolean add = false;
        for (ItemConvertible i : c_items) {
            if (i.asItem() == CobblemonItems.POTION) {
                add = true;
            }
            if (add) {
                items.add(i.asItem());
            }
            if (i.asItem() == CobblemonItems.PP_MAX) {
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
            CobblemonItems.ROASTED_LEEK,
            CobblemonItems.LEEK_AND_POTATO_STEW,
            CobblemonItems.BRAISED_VIVICHOKE,
            CobblemonItems.VIVICHOKE_DIP,
            CobblemonItems.BERRY_JUICE,
            CobblemonItems.REMEDY,
            CobblemonItems.FINE_REMEDY,
            CobblemonItems.SUPERB_REMEDY,
            CobblemonItems.HEAL_POWDER,
            CobblemonItems.MEDICINAL_BREW,

            CobblemonItems.POTION,
            CobblemonItems.SUPER_POTION,
            CobblemonItems.HYPER_POTION,
            CobblemonItems.MAX_POTION,
            CobblemonItems.FULL_RESTORE,

            CobblemonItems.ANTIDOTE,
            CobblemonItems.AWAKENING,
            CobblemonItems.BURN_HEAL,
            CobblemonItems.ICE_HEAL,
            CobblemonItems.PARALYZE_HEAL,

            CobblemonItems.FULL_HEAL,

            CobblemonItems.ETHER,
            CobblemonItems.MAX_ETHER,
            CobblemonItems.ELIXIR,
            CobblemonItems.MAX_ELIXIR,

            CobblemonItems.REVIVE,
            CobblemonItems.MAX_REVIVE,

            CobblemonItems.X_ATTACK,
            CobblemonItems.X_DEFENSE,
            CobblemonItems.X_SP_ATK,
            CobblemonItems.X_SP_DEF,
            CobblemonItems.X_SPEED,
            CobblemonItems.X_ACCURACY,

            CobblemonItems.DIRE_HIT,
            CobblemonItems.GUARD_SPEC,

            CobblemonItems.HEALTH_FEATHER,
            CobblemonItems.MUSCLE_FEATHER,
            CobblemonItems.RESIST_FEATHER,
            CobblemonItems.GENIUS_FEATHER,
            CobblemonItems.CLEVER_FEATHER,
            CobblemonItems.SWIFT_FEATHER,

            CobblemonItems.HP_UP,
            CobblemonItems.PROTEIN,
            CobblemonItems.IRON,
            CobblemonItems.CALCIUM,
            CobblemonItems.ZINC,
            CobblemonItems.CARBOS,
            CobblemonItems.PP_UP,
            CobblemonItems.PP_MAX,
            CobblemonItems.EXPERIENCE_CANDY_XS,
            CobblemonItems.EXPERIENCE_CANDY_S,
            CobblemonItems.EXPERIENCE_CANDY_M,
            CobblemonItems.EXPERIENCE_CANDY_L,
            CobblemonItems.EXPERIENCE_CANDY_XL,
            CobblemonItems.RARE_CANDY,

            CobblemonItems.LONELY_MINT,
            CobblemonItems.ADAMANT_MINT,
            CobblemonItems.NAUGHTY_MINT,
            CobblemonItems.BRAVE_MINT,
            CobblemonItems.BOLD_MINT,
            CobblemonItems.IMPISH_MINT,
            CobblemonItems.LAX_MINT,
            CobblemonItems.RELAXED_MINT,
            CobblemonItems.MODEST_MINT,
            CobblemonItems.MILD_MINT,
            CobblemonItems.RASH_MINT,
            CobblemonItems.QUIET_MINT,
            CobblemonItems.CALM_MINT,
            CobblemonItems.GENTLE_MINT,
            CobblemonItems.CAREFUL_MINT,
            CobblemonItems.SASSY_MINT,
            CobblemonItems.TIMID_MINT,
            CobblemonItems.HASTY_MINT,
            CobblemonItems.JOLLY_MINT,
            CobblemonItems.NAIVE_MINT,
            CobblemonItems.SERIOUS_MINT,

            CobblemonItems.ABILITY_CAPSULE,
            CobblemonItems.ABILITY_PATCH
    };
}
