


package me.skizzme.cc.shop.category;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonClick;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.mojang.serialization.Lifecycle;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.shop.Shop;
import me.skizzme.cc.util.GuiUtils;
import me.skizzme.cc.util.ItemBuilder;
import me.skizzme.cc.util.TextUtils;
import net.impactdev.impactor.api.utility.collections.mappings.Tuple;
import net.minecraft.item.*;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

import static net.minecraft.item.ItemGroups.COLORED_BLOCKS;

public abstract class Category {

    private Text name;
    private ItemConvertible display;
    private static Registry<ItemGroup> reg;

    public Category(Text name, ItemConvertible display) {
        this.name = name;
        this.display = display;
    }
    public Category(String name, ItemConvertible display) {
        this.name = TextUtils.formatted(name);
        this.display = display;
    }

    public static Registry<ItemGroup> getGroups() {
        if (reg == null) { // Registries.ITEM_GROUP.get(ItemGroups.COLORED_BLOCKS).getDisplayStacks().size() == 0
            reg = new SimpleRegistry<>(RegistryKeys.ITEM_GROUP, Lifecycle.stable(), false);
//            ItemGroups.registerAndGetDefault(Registries.ITEM_GROUP);
            ItemGroups.registerAndGetDefault(reg);
            System.out.println("registered" + ", " + reg.getEntrySet());
            System.out.println(reg.get(COLORED_BLOCKS).getIcon() + ", " + reg.get(COLORED_BLOCKS).getDisplayStacks());
        }
        return reg;
    }

    public abstract ArrayList<ItemConvertible> getItems();
    public abstract boolean isSellable(ItemConvertible item);

    public ArrayList<ItemConvertible> getPurchasableItems() {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        for (ItemConvertible i : getItems()) {
            Shop.ItemInfo itemInfo = Shop.getItemInfo(i.asItem());
            if (itemInfo.getPrice() != 0.0f) {
                items.add(i);
            }
        }
        return items;
    }

    public void display(ServerPlayerEntity player) {
        displayPage(0, player);
    }

    private void displayPage(int pageId, ServerPlayerEntity player) {
        Tuple<GooeyPage, Boolean> pageResult = createPage(pageId, this.getPurchasableItems());
        GooeyPage page = pageResult.getFirst();

        UIManager.openUIForcefully(player, page);
    }

    private Tuple<GooeyPage, Boolean> createPage(final int pageId, final ArrayList<ItemConvertible> items) {
//        System.out.println("create page w/ " + items.size() + ", " + this.getName() + ", " + pageId);
        ChestTemplate.Builder builder = ChestTemplate.builder(5);

        int pageOffset = 9 * 4 * pageId;
        int slot = 0;
        boolean availableNextPage = true;
        for (int i = 0; i < 9 * 4; i++) {
//            System.out.println(items.size() + ", " + pageId + ", " + pageOffset);
            ItemConvertible item = items.get(i + pageOffset);
            boolean sellable = isSellable(item);
            Shop.ItemInfo itemInfo = Shop.getItemInfo(item);
            boolean purchasable = itemInfo.getPrice() != 0.0f;
            MutableText name = Text.empty();
            name.formatted(Formatting.GRAY);
            name.append(item.asItem().getName());
            ItemStack stack = new ItemBuilder(item)
                    .name(name)
                    .lore(new String[] {
                            "",
                            purchasable ? "&aPurchase Price: &e" + CCCore.MONEY_FORMAT.format(itemInfo.getPrice()) : "&cNot purchasable",
                            sellable ? "&cSell Price: &e" + CCCore.MONEY_FORMAT.format(itemInfo.getPrice() * itemInfo.getSellPercent()) : "&cNot sellable",
                            "",
                            purchasable ? "&aLeft-Click &7to buy" : "",
                            sellable ? "&cRight-Click &7to sell" : "",
                            ""
                    })
                    .build();

            Button button = GooeyButton.builder()
                    .display(stack)
                    .onClick((ac) -> {
                        System.out.println(ac.getClickType() + ", " + ac.getSlot());
                        if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK || (!purchasable && !sellable)) {
                            return;
                        }
                        if (ac.getClickType() == ButtonClick.LEFT_CLICK && !purchasable || ac.getClickType() == ButtonClick.RIGHT_CLICK && !sellable) {
                            return;
                        }
                        Shop.itemTransaction(() -> displayPage(pageId, ac.getPlayer()), ac.getPlayer(), item.asItem(), itemInfo, 1, ac.getClickType() == ButtonClick.LEFT_CLICK || !sellable);
                        ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                    })
                    .build();

            builder.set(slot, button);
            if (slot + pageOffset >= items.size()-1) {
                availableNextPage = false;
                break;
            }
            slot++;
        }

        if (pageId > 0) {
            builder.set(4, 2, GooeyButton.builder()
                    .display(new ItemBuilder(Items.ARROW).name("&cPrevious Page").build())
                    .onClick((ac) -> {
                        if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK) {
                            return;
                        }
                        this.displayPage(pageId - 1, ac.getPlayer());
                        ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                    })
                    .build()
            );
        }

        if (availableNextPage) {
            builder.set(4, 6, GooeyButton.builder()
                    .display(new ItemBuilder(Items.SPECTRAL_ARROW).name("&aNext Page").build())
                    .onClick((ac) -> {
                        if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK) {
                            return;
                        }
                        this.displayPage(pageId + 1, ac.getPlayer());
                        ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                    })
                    .build()
            );
        }

        builder.set(4, 4, GooeyButton.builder()
                .display(new ItemBuilder(Items.BARRIER)
                        .name("&4Back")
                        .build()
                )
                .onClick((ac) -> {
                    if (ac.getClickType() != ButtonClick.LEFT_CLICK && ac.getClickType() != ButtonClick.RIGHT_CLICK) {
                        return;
                    }
                    Shop.display(ac.getPlayer());
                    ac.getPlayer().playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 0.7f, 1.0f);
                })
                .build()
        );

        builder.fill(GuiUtils.background());
        GooeyPage page = GooeyPage.builder()
                .template(builder.build())
                .title(Text.empty().append(this.getName()).append(Text.literal(" - Pg. " + (pageId+1)).formatted(Formatting.DARK_GRAY)))
                .build();

        return new Tuple<>(page, availableNextPage);
    }

    public Text getName() {
        return name;
    }

    public ItemConvertible getDisplay() {
        return display;
    }
}