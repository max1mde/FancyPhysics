package com.maximde.fancyphysics.command;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FPTabCompleter implements TabCompleter {
    private final FancyPhysics fancyPhysics;
    public FPTabCompleter(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            commands.add("reload");
            commands.add("settings");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("settings")) {
                commands.addAll(fancyPhysics.getPluginConfig().getConfig().getConfigurationSection("Physics").getKeys(false));
            }
            StringUtil.copyPartialMatches(args[1], commands, completions);
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("settings")) {
                String key = args[1];
                if (fancyPhysics.getPluginConfig().getConfig().getConfigurationSection("Physics").getKeys(false).contains(key)) {
                    final var val = fancyPhysics.getPluginConfig().getConfig().get("Physics." + key).toString().toLowerCase();
                    if(val.equals("true") || val.equals("false")) {
                        commands.add("enable");
                        commands.add("disable");
                    } else {
                        commands.add(val);
                    }
                }
            }
            StringUtil.copyPartialMatches(args[2], commands, completions);
        }

        return completions;
    }
}