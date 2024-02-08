package com.maximde.fancyphysics.listeners.player;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.api.events.TreeBreakEvent;
import com.maximde.fancyphysics.utils.Tree;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class BlockBreakListener implements Listener {
    private final FancyPhysics fancyPhysics;
    public BlockBreakListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        if(this.fancyPhysics.getPluginConfig().getDisabledWorldsList().contains(event.getPlayer().getLocation().getWorld().getName())) return;
        manageTreePhysics(event);
        if(fancyPhysics.getPluginConfig().isFlyUpParticles()) {
            this.fancyPhysics.getParticleGenerator().simulateBigBlockParticle(event.getBlock().getLocation(), event.getBlock().getType());
        } else {
            this.fancyPhysics.getParticleGenerator().simulateBlockParticles(event.getBlock());
        }
    }

    /**
     * Creates a new Tree object and plays a break animation
     */
    private void manageTreePhysics(BlockBreakEvent event) {
        if (isWood(event.getBlock().getType()) && this.fancyPhysics.getPluginConfig().isRealisticTrees() &&
                event.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR && isWood(event.getBlock().getRelative(BlockFace.UP).getType())) {
            Tree tree = new Tree(event.getBlock(), this.fancyPhysics);
            TreeBreakEvent treeBreakEvent = new TreeBreakEvent(tree);
            Bukkit.getServer().getPluginManager().callEvent(treeBreakEvent);
            if (treeBreakEvent.isCancelled()) return;
            tree.breakWithFallAnimation();
            if(fancyPhysics.getPluginConfig().isTreeRegeneration()) regenerate(tree, 10);
        }
    }

    private void regenerate(Tree tree, int seconds) {
        Bukkit.getScheduler().runTaskLater(fancyPhysics, () -> {
            for(Location location : tree.getOldBlockList().keySet()) {
                var block = location.getBlock();
                if(block.getType() != Material.AIR) continue;
                block.setType(tree.getOldBlockList().get(location));
            }
        },  20L * seconds);
    }

    private boolean isWood(Material pMaterial) {
        return switch (pMaterial.name()) {
            case "BIRCH_LOG", "OAK_LOG", "SPRUCE_LOG", "DARK_OAK_LOG", "ACACIA_LOG", "JUNGLE_LOG", "CRIMSON_STEM", "WARPED_STEM", "MANGROVE_LOG", "CHERRY_LOG" ->
                    true;
            default -> false;
        };
    }

}
