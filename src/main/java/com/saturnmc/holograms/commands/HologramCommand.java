package com.saturnmc.holograms.commands;

import com.saturnmc.holograms.SaturnHolograms;
import com.saturnmc.holograms.hologram.HologramManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramCommand implements CommandExecutor {

    private final SaturnHolograms plugin;
    private final HologramManager manager;

    public HologramCommand(SaturnHolograms plugin, HologramManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    private String msg(String key) {
        return plugin.getMessages().getString(key).replace("&", "§");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(msg("help-header"));
            sender.sendMessage(msg("help-title"));
            sender.sendMessage(msg("help-create"));
            sender.sendMessage(msg("help-delete"));
            sender.sendMessage(msg("help-list"));
            sender.sendMessage("§f/sh addline <name> <text>");
            sender.sendMessage("§f/sh settext <name> <text>");
            sender.sendMessage(msg("help-footer"));
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (!(sender instanceof Player p)) return true;
            if (args.length < 3) return true;
            String name = args[1];
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String text = sb.toString().trim();
            Location loc = p.getLocation();
            manager.create(name, loc, text);
            sender.sendMessage(msg("created").replace("%name%", name));
            return true;
        }

        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length < 2) return true;
            manager.delete(args[1]);
            sender.sendMessage(msg("deleted").replace("%name%", args[1]));
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(msg("list-header"));
            manager.getAll().forEach(h -> sender.sendMessage(msg("list-entry").replace("%name%", h.getName())));
            return true;
        }

        if (args[0].equalsIgnoreCase("addline")) {
            if (args.length < 3) return true;
            String name = args[1];
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String text = sb.toString().trim();
            manager.addLine(name, text);
            sender.sendMessage(msg("added-line").replace("%name%", name));
            return true;
        }

        if (args[0].equalsIgnoreCase("settext")) {
            if (args.length < 3) return true;
            String name = args[1];
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String text = sb.toString().trim();
            manager.setText(name, text);
            sender.sendMessage(msg("updated-text").replace("%name%", name));
            return true;
        }

        return true;
    }
}