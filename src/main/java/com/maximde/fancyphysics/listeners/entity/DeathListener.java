package com.maximde.fancyphysics.listeners.entity;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathListener implements Listener {
    private final FancyPhysics fancyPhysics;

    public DeathListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(this.fancyPhysics.getPluginConfig().getDisabledWorldsList().contains(event.getEntity().getLocation().getWorld().getName())) return;
        createDeathParticles(event);
    }

    private void createDeathParticles(EntityDeathEvent event) {
        if(!this.fancyPhysics.getPluginConfig().isEntityDeathParticles()) return;
        if (event.getEntity().getHeight() < 1) return;

        var entity = event.getEntity();
        final var height = entity.getHeight();
        var material = getParticleMaterial(entity.getType());

        var lightLevel = -1;
        if(entity.getType() == EntityType.BLAZE) lightLevel = 15;

        int roundedHeight = (int) Math.ceil(height);
        Block block = entity.getLocation().getBlock();

        for (int i = 0; i < roundedHeight; i++) {
            this.fancyPhysics.getParticleGenerator().simulateBloodParticles(block.getLocation(), material, lightLevel);
            block = block.getRelative(0, 1, 0);
        }
    }

    private Material getParticleMaterial(EntityType entityType) {
        return switch (entityType) {
            case MAGMA_CUBE -> Material.ORANGE_CONCRETE;
            case SLIME, CREEPER -> Material.SLIME_BLOCK;
            case ALLAY -> Material.LIGHT_BLUE_STAINED_GLASS;
            case ENDERMAN, WITHER, WITHER_SKELETON -> Material.BLACK_CONCRETE;
            case BLAZE -> Material.YELLOW_CONCRETE;
            case SNOWMAN -> Material.SNOW_BLOCK;
            case WARDEN -> Material.SCULK;
            case SKELETON -> Material.BONE_BLOCK;
            case ZOMBIE, ZOMBIE_HORSE, ZOMBIE_VILLAGER -> Material.GREEN_CONCRETE;
            case ENDER_DRAGON -> Material.AIR;
            default -> Material.RED_CONCRETE;
        };
    }

}
