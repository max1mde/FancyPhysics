package com.maximde.fancyphysics.listeners.entity;

import com.google.common.base.Predicates;
import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class DeathListener implements Listener {

    private FancyPhysics fancyPhysics;

    public DeathListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(!this.fancyPhysics.config.isEntityDeathParticles()) return;
        var entity = event.getEntity();

        final var height = entity.getHeight();
        var material = switch (entity.getType()) {
            case MAGMA_CUBE -> Material.ORANGE_CONCRETE;
            case SLIME -> Material.SLIME_BLOCK;
            case ALLAY -> Material.LIGHT_BLUE_STAINED_GLASS;
            case ENDERMAN, WITHER -> Material.BLACK_CONCRETE;
            case BLAZE -> Material.YELLOW_CONCRETE;
            case SNOWMAN -> Material.SNOW_BLOCK;
            case WARDEN -> Material.SCULK;
            case SKELETON -> Material.BONE_BLOCK;
            default -> Material.RED_MUSHROOM_BLOCK;
        };
        if (height > 0) {
            int roundedHeight = (int) Math.ceil(height);
            Block block = entity.getLocation().getBlock();
            for (int i = 0; i < roundedHeight; i++) {
                this.fancyPhysics.particleGenerator.simulateBloodParticles(block.getLocation(), material);
                block = block.getRelative(0, 1, 0);
            }
        }
    }
}
