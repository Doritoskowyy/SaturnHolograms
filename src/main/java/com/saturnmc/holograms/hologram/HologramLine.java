package com.saturnmc.holograms.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class HologramLine {

    private final String text;
    private ArmorStand entity;

    public HologramLine(String text) {
        this.text = text;
    }

    public ArmorStand spawn(Location loc) {
        ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        stand.setInvisible(true);
        stand.setMarker(true);
        stand.setGravity(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName(text);
        return stand;
    }

    public String getText() {
        return text;
    }

    public ArmorStand getEntity() {
        return entity;
    }

    public void setEntity(ArmorStand entity) {
        this.entity = entity;
    }
}