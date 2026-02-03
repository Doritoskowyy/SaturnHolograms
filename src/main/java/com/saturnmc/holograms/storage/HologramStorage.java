package com.saturnmc.holograms.storage;

import com.saturnmc.holograms.SaturnHolograms;
import com.saturnmc.holograms.hologram.Hologram;
import com.saturnmc.holograms.hologram.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class HologramStorage {

    private final SaturnHolograms plugin;
    private final File folder;

    public HologramStorage(SaturnHolograms plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getDataFolder(), plugin.getConfig().getString("storage-folder"));
        if (!folder.exists()) folder.mkdirs();
    }

    public void save(Hologram h) {
        File file = new File(folder, h.getName() + ".yml");
        YamlConfiguration cfg = new YamlConfiguration();
        cfg.set("world", h.getLocation().getWorld().getName());
        cfg.set("x", h.getLocation().getX());
        cfg.set("y", h.getLocation().getY());
        cfg.set("z", h.getLocation().getZ());
        cfg.set("text", h.getLines().get(0).getText());
        try { cfg.save(file); } catch (Exception ignored) {}
    }

    public void delete(String name) {
        File file = new File(folder, name + ".yml");
        if (file.exists()) file.delete();
    }

    public void loadAll(HologramManager manager) {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File f : files) {
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            String world = cfg.getString("world");
            double x = cfg.getDouble("x");
            double y = cfg.getDouble("y");
            double z = cfg.getDouble("z");
            String text = cfg.getString("text");
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            String name = f.getName().replace(".yml", "");
            Hologram h = new Hologram(name, loc, text);
            manager.load(h);
        }
    }

    public void saveAll(HologramManager manager) {
        manager.getAll().forEach(this::save);
    }
}