package com.maximde.fancyphysics.listeners.entity;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ExplodeListener implements Listener {
    private final FancyPhysics fancyPhysics;
    public ExplodeListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        createRealisticExplosion(event);
    }

    private void createRealisticExplosion(EntityExplodeEvent event) {
        if(this.fancyPhysics.getPluginConfig().getDisabledWorldsList().contains(event.getLocation().getWorld().getName())) return;
        if(!this.fancyPhysics.getPluginConfig().isRealisticExplosion()) return;
        if(event.isCancelled()) return;
        if(fancyPhysics.getPluginConfig().isPerformanceMode() && event.getLocation().getChunk().getEntities().length > 2000) return;
        event.setYield(40);
        if(event.getEntity().getName().toLowerCase().contains("wind charge")) return;
        final var entitysAmount = event.getLocation().getChunk().getEntities().length;
        final var oldBlockList = new HashMap<Location, Material>();
        for (Block block : event.blockList()) {
            var x = -(float)(ThreadLocalRandom.current().nextDouble() / 10) + (float) (Math.random() / 10);
            var y = -0.1F + (float) (ThreadLocalRandom.current().nextDouble() + 0.5D);
            var z = -(float)(ThreadLocalRandom.current().nextDouble() / 10) + (float) (Math.random() / 10);
            if(block.getType() == Material.TNT) {
                var tntPrimed = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
                tntPrimed.setFuseTicks((int)(ThreadLocalRandom.current().nextFloat() * 100) + 80);
                tntPrimed.setVelocity(new Vector(x, y, z));
                block.setType(Material.AIR);
                continue;
            } else {
                oldBlockList.put(block.getLocation(), block.getType());
            }
            if(fancyPhysics.getPluginConfig().isPerformanceMode() && entitysAmount> 500) {
                if(fancyPhysics.getPluginConfig().isDropsOnExplode()) block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
                block.setType(Material.AIR);
                continue;
            }

            var fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().add(0,1,0), block.getType().createBlockData());
            if(fancyPhysics.getPluginConfig().isNaturalDropsOnExplode() || !fancyPhysics.getPluginConfig().isDropsOnExplode()) fallingBlock.setDropItem(false);
            if(!fancyPhysics.getPluginConfig().isDropsOnExplode()) fallingBlock.addScoreboardTag("NoDrop");
            fallingBlock.setVelocity(new Vector(x, y, z));
            block.setType(Material.AIR);
        }
        if(fancyPhysics.getPluginConfig().isExplosionRegeneration()) regenerate(oldBlockList, fancyPhysics.getPluginConfig().getExplosionRegenerationDelay());
    }

    private void regenerate(HashMap<Location, Material> blocks, int seconds) {
        Bukkit.getScheduler().runTaskLater(fancyPhysics, () -> {
            for(Location location : blocks.keySet()) {
                var block = location.getBlock();
                if(block.getType() != Material.AIR) continue;
                block.setType(blocks.get(location));
            }
        },  20L * seconds);
    }

}
