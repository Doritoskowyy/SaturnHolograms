package com.saturnmc.holograms.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import java.util.ArrayList;
import java.util.List;

public class Hologram {

    private final String name;
    private final Location location;
    private final List<HologramLine> lines = new ArrayList<>();

    public Hologram(String name, Location location, String text) {
        this.name = name;
        this.location = location;
        addLine(text);
    }

    public void addLine(String text) {
        lines.add(new HologramLine(text));
    }

    public void spawn(double spacing) {
        double offset = 0;
        for (HologramLine line : lines) {
            Location loc = location.clone().add(0, offset, 0);
            ArmorStand stand = line.spawn(loc);
            line.setEntity(stand);
            offset -= spacing;
        }
    }

    public void remove() {
        lines.forEach(l -> {
            if (l.getEntity() != null) l.getEntity().remove();
        });
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public List<HologramLine> getLines() {
        return lines;
    }
}