package wyspr.quietsave.mixins;

import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wyspr.quietsave.commands.BackupCommand;

import java.util.List;

@Mixin(value = Commands.class, remap = false)
public class CommandsMixin {
	@Shadow
	public static List<Command> commands;
	@Inject(method = "initCommands", at = @At("TAIL"))
	private static void addCommands(CallbackInfo ci) {
		commands.add(new BackupCommand());
	}
}
