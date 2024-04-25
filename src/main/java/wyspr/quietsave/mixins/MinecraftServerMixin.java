package wyspr.quietsave.mixins;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wyspr.quietsave.QuietSave;

@Mixin(value = MinecraftServer.class, remap = false)
public class MinecraftServerMixin {
	@Inject(method = "run", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/server/MinecraftServer;doTick()V",
		shift = At.Shift.AFTER
	))
	public void checkTime(CallbackInfo ci) {
		QuietSave.BACKUPS.tick();
	}
}
