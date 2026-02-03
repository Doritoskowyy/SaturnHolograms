package com.saturnmc.holograms;

import com.saturnmc.holograms.commands.HologramCommand;
import com.saturnmc.holograms.hologram.HologramManager;
import com.saturnmc.holograms.storage.HologramStorage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SaturnHolograms extends JavaPlugin {

    private static SaturnHolograms instance;
    private HologramManager hologramManager;
    private HologramStorage storage;
    private FileConfiguration messages;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveResource("messages.yml", false);

        messages = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));

        storage = new HologramStorage(this);
        hologramManager = new HologramManager(this, storage);
        storage.loadAll(hologramManager);

        getCommand("saturnholograms").setExecutor(new HologramCommand(this, hologramManager));
    }

    @Override
    public void onDisable() {
        storage.saveAll(hologramManager);
    }

    public static SaturnHolograms getInstance() {
        return instance;
    }

    public FileConfiguration getMessages() {
        return messages;
    }
}