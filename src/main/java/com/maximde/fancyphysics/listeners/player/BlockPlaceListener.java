package com.maximde.fancyphysics.listeners.player;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    private FancyPhysics fancyPhysics;
    public BlockPlaceListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(this.fancyPhysics.config.isTrapdoorPhysics() && InteractListener.animatedLocations.contains(event.getBlock().getLocation())) event.setCancelled(true);
    }
}
