package me.skizzme.cc.mixin;

import me.skizzme.cc.ItemStackExt;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin implements ItemStackExt {

    @Unique
    private boolean unTakeable = false;

    @Inject(at = @At("HEAD"), method = "copyAndEmpty", cancellable = true)
    private void copyAndEmptyInjection(CallbackInfoReturnable<ItemStack> cir) {
        if (this.unTakeable) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

    @Inject(at = @At("RETURN"), method = "copy", cancellable = true)
    private void copyInjection(CallbackInfoReturnable<ItemStack> cir) {
        ((ItemStackExt) (Object) cir.getReturnValue()).setUntakeable(this.unTakeable);
    }

    @Override
    public boolean getUntakeable() {
        return unTakeable;
    }

    @Override
    public void setUntakeable(boolean val) {
        this.unTakeable = val;
    }
}