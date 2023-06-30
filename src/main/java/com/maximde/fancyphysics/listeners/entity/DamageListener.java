package com.maximde.fancyphysics.listeners.entity;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;



public class DamageListener implements Listener {
    private FancyPhysics fancyPhysics;

    public DamageListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!this.fancyPhysics.config.isDamageParticles()) return;
        if(event.isCancelled()) return;
        var entity = event.getEntity();

        if(!(entity instanceof LivingEntity)) return;

        var height = entity.getHeight();
        if (height < 0) return;
        int roundedHeight = (int) Math.ceil(height);
        Block block = entity.getLocation().getBlock();

        var material = switch (entity.getType()) {
            case ENDER_DRAGON -> Material.AIR;
            case WITHER, WITHER_SKELETON -> Material.BLACK_CONCRETE;
            case BLAZE -> Material.YELLOW_CONCRETE;
            default -> Material.RED_CONCRETE;
        };

        var lightLevel = switch (entity.getType()) {
            case BLAZE -> 15;
            default -> -1;
        };

        for (int i = 0; i < roundedHeight; i++) {
            this.fancyPhysics.particleGenerator.simulateSplashBloodParticles(block.getLocation(), material, lightLevel);
            block = block.getRelative(0, 1, 0);
        }
    }
}
