package com.maximde.fancyphysics.listeners.entity;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class HitGroundListener implements Listener {

    private final FancyPhysics fancyPhysics;

    public HitGroundListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        createParticles(event);
    }

    private void createParticles(EntityChangeBlockEvent event) {
        if(!this.fancyPhysics.config.isBlockParticles()) return;
        if(event.getBlock().getType() != Material.AIR) return;
        if (event.getEntityType() != EntityType.FALLING_BLOCK) return;
        var fallingBlock = (FallingBlock) event.getEntity();
        event.setCancelled(true);
        var material = fallingBlock.getMaterial();
        final var loc = event.getBlock().getLocation();
        loc.getWorld().playSound(loc, Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
        this.fancyPhysics.particleGenerator.simulateBlockParticles(loc, this.fancyPhysics.particleGenerator.getParticleMaterial(material));
    }

}
