package com.maximde.fancyphysics.listeners.player;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.utils.Tree;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private FancyPhysics fancyPhysics;
    public BlockBreakListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        var block = event.getBlock();
        if(event.isCancelled()) return;
        timber(event);
        this.fancyPhysics.particleGenerator.simulateBlockParticles(block);
    }

    private void timber(BlockBreakEvent event) {
        if (isWood(event.getBlock().getType()) && this.fancyPhysics.config.isRealisticTrees() &&
                event.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR && isWood(event.getBlock().getRelative(BlockFace.UP).getType())) {
            Tree tree = new Tree(event.getBlock(), this.fancyPhysics);
            tree.breakWithFallAnimation();
        }
    }

    private boolean isWood(Material pMaterial) {
        switch (pMaterial) {
            case BIRCH_LOG:
            case OAK_LOG:
            case SPRUCE_LOG:
            case DARK_OAK_LOG:
            case ACACIA_LOG:
            case JUNGLE_LOG:
            case CRIMSON_STEM:
            case WARPED_STEM:
            case MANGROVE_LOG:
            case CHERRY_LOG:
                return true;
        }
        return false;
    }
}
