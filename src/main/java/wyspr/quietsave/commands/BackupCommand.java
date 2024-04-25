package wyspr.quietsave.commands;

import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import wyspr.quietsave.BackupManager;

public class BackupCommand extends Command {
	public BackupCommand() {
		super("backup");
	}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args) {
		boolean backupFailed = !BackupManager.backup();

		if (backupFailed) {
			sender.sendMessage("§e§lBackup failed for some reason, check the console!");
		} else {
			sender.sendMessage("§1§nBackup successful!");
		}

		return true;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		sender.sendMessage("§3/backup");
		sender.sendMessage("§5Backup the world data.");
	}
}
