package com.maximde.fancyphysics.listeners.entity;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    private final FancyPhysics fancyPhysics;

    public DamageListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(this.fancyPhysics.getPluginConfig().getDisabledWorldsList().contains(event.getEntity().getLocation().getWorld().getName())) return;
        createDamageParticles(event);
    }

    private void createDamageParticles(EntityDamageEvent event) {
        if(!this.fancyPhysics.getPluginConfig().isDamageParticles()) return;
        if(event.isCancelled()) return;
        if(event.getDamage() <= 0.4) return;
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL && !event.getEntity().getWorld().isGameRule("fallDamage")) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        if (event.getEntity().getHeight() < 0) return;

        var entity = event.getEntity();
        var block = entity.getLocation().getBlock();
        var height = entity.getHeight();
        int roundedHeight = (int) Math.ceil(height);
        var material = getParticleMaterial(entity.getType());
        var lightLevel = -1;
        if(entity.getType() == EntityType.BLAZE) lightLevel = 15;
        if(fancyPhysics.getPluginConfig().isSounds()) entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ALLAY_ITEM_THROWN, 0.8f, 1f);

        for (int i = 0; i < roundedHeight; i++) {
            this.fancyPhysics.getParticleGenerator().simulateSplashBloodParticles(block.getLocation(), material, lightLevel);
            block = block.getRelative(0, 1, 0);
        }
    }

    private Material getParticleMaterial(EntityType entityType) {
        return switch (entityType) {
            case ENDER_DRAGON -> Material.AIR;
            case WITHER, WITHER_SKELETON -> Material.BLACK_CONCRETE;
            case BLAZE -> Material.YELLOW_CONCRETE;
            default -> Material.RED_CONCRETE;
        };
    }

}
