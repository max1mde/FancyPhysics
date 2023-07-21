package com.maximde.fancyphysics.command;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.ParseException;

public class FPCommand implements CommandExecutor {
    private FancyPhysics fancyPhysics;
    public FPCommand(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if(sender instanceof Player player) {
            if(!player.hasPermission("fancyphysics.commands") && !player.hasPermission("fancyphysics.admin")) {
                player.sendMessage(ChatColor.RED + "No permission!");
                return false;
            }
        }

        if(args.length == 0) {
            sendInfoMessage(sender);
            return false;
        }

        if(args[0].equalsIgnoreCase("reload")) {
            fancyPhysics.getPluginConfig().reload();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
            return false;
        }

        if(args.length < 3) {
            sendInfoMessage(sender);
            return false;
        }
        if(args[0].equalsIgnoreCase("settings")) {
            for(String key : fancyPhysics.getPluginConfig().getConfig().getConfigurationSection("Physics").getKeys(false)) {
                if(args[1].equalsIgnoreCase(key)) {
                    if(args[2].equalsIgnoreCase("enable")) args[2] = "true";
                    if(args[2].equalsIgnoreCase("disable")) args[2] = "false";
                    var value = fancyPhysics.getPluginConfig().getValue("Physics."+key);
                    try {
                        switch (getValueType(value)) {
                            default -> fancyPhysics.getPluginConfig().setValue("Physics."+key, args[2]);
                            case "Integer" -> fancyPhysics.getPluginConfig().setValue("Physics."+key, Integer.parseInt(args[2]));
                            case "Boolean" -> fancyPhysics.getPluginConfig().setValue("Physics."+key, Boolean.parseBoolean(args[2]));
                            case "Float" -> fancyPhysics.getPluginConfig().setValue("Physics."+key, Float.parseFloat(args[2]));
                            case "Double" -> fancyPhysics.getPluginConfig().setValue("Physics."+key, Double.parseDouble(args[2]));
                            case "Long" -> fancyPhysics.getPluginConfig().setValue("Physics."+key, Long.parseLong(args[2]));
                        };
                        fancyPhysics.getPluginConfig().saveConfig();
                        fancyPhysics.getPluginConfig().reload();
                        sender.sendMessage(ChatColor.GREEN + "Changed " + key + " to " + args[2]);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + args[1] + " is not a valid " + getValueType(value));
                    }
                    break;
                }
            }

        }

        return false;
    }


    private String getValueType(Object value) {
        return value != null ? value.getClass().getSimpleName() : "Unknown";
    }

    private void sendInfoMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "===== ALL COMMANDS =====");
        final var command = "/fancyphysics ";
        sender.sendMessage(ChatColor.LIGHT_PURPLE + command + "reload");
        for(String key : fancyPhysics.getPluginConfig().getConfig().getConfigurationSection("Physics").getKeys(false)) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + command + key.toLowerCase() + ChatColor.GOLD + " <value>");
        }
        sender.sendMessage(ChatColor.DARK_PURPLE + "===== ALL COMMANDS =====");
    }
}
