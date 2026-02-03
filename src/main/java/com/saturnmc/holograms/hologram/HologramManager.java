package com.saturnmc.holograms.hologram;

import com.saturnmc.holograms.SaturnHolograms;
import com.saturnmc.holograms.storage.HologramStorage;
import org.bukkit.Location;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HologramManager {

    private final Map<String, Hologram> holograms = new HashMap<>();
    private final HologramStorage storage;
    private final SaturnHolograms plugin;

    public HologramManager(SaturnHolograms plugin, HologramStorage storage) {
        this.plugin = plugin;
        this.storage = storage;
    }

    public void create(String name, Location loc, String text) {
        if (holograms.containsKey(name)) return;
        Hologram h = new Hologram(name, loc, text);
        h.spawn(plugin.getConfig().getDouble("hologram-spacing"));
        holograms.put(name, h);
        storage.save(h);
    }

    public void delete(String name) {
        if (!holograms.containsKey(name)) return;
        Hologram h = holograms.remove(name);
        h.remove();
        storage.delete(name);
    }

    public void addLine(String name, String text) {
        if (!holograms.containsKey(name)) return;
        Hologram h = holograms.get(name);
        h.addLine(text);
        h.remove();
        h.spawn(plugin.getConfig().getDouble("hologram-spacing"));
        storage.save(h);
    }

    public void setText(String name, String text) {
        if (!holograms.containsKey(name)) return;
        Hologram h = holograms.get(name);
        h.getLines().clear();
        h.addLine(text);
        h.remove();
        h.spawn(plugin.getConfig().getDouble("hologram-spacing"));
        storage.save(h);
    }

    public void load(Hologram h) {
        holograms.put(h.getName(), h);
        h.spawn(plugin.getConfig().getDouble("hologram-spacing"));
    }

    public Collection<Hologram> getAll() {
        return holograms.values();
    }
}