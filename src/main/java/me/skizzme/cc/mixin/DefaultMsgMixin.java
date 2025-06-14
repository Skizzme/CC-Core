package me.skizzme.cc.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.MessageCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessageCommand.class)
public class DefaultMsgMixin {
	@Inject(at = @At("HEAD"), method = "register", cancellable = true)
	private static void init(CallbackInfo info) {
		info.cancel();
	}
}