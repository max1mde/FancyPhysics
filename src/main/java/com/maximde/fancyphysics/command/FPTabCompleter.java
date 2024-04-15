package com.maximde.fancyphysics.command;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
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

        /*
         * Check player permissions
         */
        if(sender instanceof Player player) {
            if(!player.hasPermission("fancyphysics.commands") && !player.hasPermission("fancyphysics.admin")) {
                return new ArrayList<>();
            }
        }

        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            commands.add("reload");
            //commands.add("settings");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }


        //TODO TEMP DISABLED
        if(true) return completions;

        /*
         * Add all keys from the config
         */
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("settings")) {
                commands.addAll(fancyPhysics.getPluginConfig().getCfg().getConfigurationSection("Physics").getKeys(false));
            }
            StringUtil.copyPartialMatches(args[1], commands, completions);
        }

        /*
         * Add the current value from the config of the selected key (if it's a boolean true and false) to the command list
         */
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("settings")) {
                String key = args[1];
                if (fancyPhysics.getPluginConfig().getCfg().getConfigurationSection("Physics").getKeys(false).contains(key)) {
                    final var val = fancyPhysics.getPluginConfig().getCfg().get("Physics." + key).toString().toLowerCase();

                    if (key.equalsIgnoreCase("BlockParticleBlackList") || key.equalsIgnoreCase("DisabledWorldsList")) {
                        commands.add("add");
                        commands.add("remove");
                    } else {
                     /*
                        Convert boolean to nice readable strings
                        and add the unmodified given value if it's not a boolean
                     */
                        if(val.equals("true") || val.equals("false")) {
                            commands.add("enable");
                            commands.add("disable");
                        } else {
                            commands.add(val);
                        }
                    }
                }
            }
            StringUtil.copyPartialMatches(args[2], commands, completions);
        }

        if (args.length == 4) {

            if(args[1].equals("BlockParticleBlackList")) {
                for(Material m : Material.values()) {
                    commands.add(m.name());
                }
            } else if(args[1].equals("DisabledWorldsList")) {
                commands.addAll(fancyPhysics.getPluginConfig().getDisabledWorldsList());
            }

            StringUtil.copyPartialMatches(args[3], commands, completions);
        }

        return completions;
    }

}