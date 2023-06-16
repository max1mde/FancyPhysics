package com.maximde.fancyphysics.listeners;

import com.maximde.fancyphysics.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(Config.isTrapdoorPhysics() && PlayerInteractListener.animatedLocations.contains(event.getBlock().getLocation())) event.setCancelled(true);
    }
}
