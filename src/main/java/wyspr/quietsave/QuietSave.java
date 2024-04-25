package wyspr.quietsave;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class QuietSave implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "quietsave";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final TomlConfigHandler CONFIG;
	public static Path SAVE_DIR;
	public static long SAVE_FREQ;
	public static BackupManager BACKUPS;

	static {
		Toml toml = new Toml();
		toml.addEntry("saveFrequency", "How often backups are saved (in minutes)", 20)
			.addEntry("saveDir", "Directory to save backups to", "backups");

		CONFIG = new TomlConfigHandler(MOD_ID, toml);

		SAVE_FREQ = CONFIG.getInt("saveFrequency");
		SAVE_DIR = Paths.get(CONFIG.getString("saveDir"));
	}

    @Override
    public void onInitialize() {
        LOGGER.info("QuietSave initialized.");
		Path saveDirPath = SAVE_DIR;
		if (!Files.exists(saveDirPath)) {
			try {
				Files.createDirectories(saveDirPath);
				System.out.println("Created /" + saveDirPath +"/ for world backups");
			} catch (IOException e) {
				System.out.println("Could not create save directory: " + SAVE_DIR);
				return;
			}
		}
		BACKUPS = new BackupManager();
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
	}

	@Override
	public void onRecipesReady() {

	}
}
