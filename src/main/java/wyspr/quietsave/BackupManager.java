package wyspr.quietsave;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupManager {
	private long lastBackupTime = System.currentTimeMillis();

	public static boolean backup() {
		MinecraftServer mc = MinecraftServer.getInstance();

		for (WorldServer world : mc.dimensionWorlds) {
			world.saveWorld(true, null, true);
		}

		String worldName = mc.propertyManager.getStringProperty("level-name", "world");
		Path worldPath = Paths.get(worldName);

		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd - HH.mm");
		String formattedDate = formatter.format(now);
		File zipFileName = QuietSave.SAVE_DIR.resolve(formattedDate + ".zip").toFile();

		try {
			final ZipOutputStream outputStream = new ZipOutputStream(Files.newOutputStream(zipFileName.toPath()));
			Files.walkFileTree(worldPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
					try {
						Path targetFile = worldPath.relativize(file);
						outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
						byte[] bytes = Files.readAllBytes(file);
						outputStream.write(bytes, 0, bytes.length);
						outputStream.closeEntry();
					} catch (IOException e) {
						QuietSave.LOGGER.error("Failed to zip files", e);
					}
					return FileVisitResult.CONTINUE;
				}
			});
			outputStream.close();
		} catch (IOException e) {
			QuietSave.LOGGER.error("Failed to zip files", e);
			return false;
		}
		System.out.println("World backup saved to: " + zipFileName);
		QuietSave.LOGGER.info("World backup saved to: {}", zipFileName);
		return true;
	}

	public void tick() {
		long now = System.currentTimeMillis();
		long timeChange = now - this.lastBackupTime;

		if (timeChange >= QuietSave.SAVE_FREQ * 60000) {
			boolean backupSucessful = backup();
			if (backupSucessful) {
				this.lastBackupTime = now;
			}
		}
	}
}
