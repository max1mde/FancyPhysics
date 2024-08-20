package com.maximde.fancyphysics.listeners.player;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;


public class BlockPlaceListener implements Listener {

    private final FancyPhysics fancyPhysics;

    private static final BlockFace[] CHECK_FACES = {
            BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
            BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_WEST,
            BlockFace.UP, BlockFace.DOWN
    };

    public BlockPlaceListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        checkBlockPhysics(event.getBlock());
        if (this.fancyPhysics.getPluginConfig().getDisabledWorldsList().contains(event.getPlayer().getLocation().getWorld().getName())) return;
        if (this.fancyPhysics.getPluginConfig().isTrapdoorPhysics() && InteractListener.animatedLocations.contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
            return;
        }
    }

    private void checkBlockPhysics(Block startBlock) {
        if (!fancyPhysics.getPluginConfig().isBlockPhysicsEnabled()) return;
        if (startBlock.getType().isAir()) return;
        if(startBlock.getRelative(BlockFace.DOWN).getType().isSolid()) return;
        int maxBlocks = 50;

        Material mainMaterial = startBlock.getType();
        int maxDistance = fancyPhysics.getPluginConfig().getMaxBridgingLength(mainMaterial);
        if(maxDistance < 0) return;
        Block nearestSupportedBlock = findNearestSupportedBlock(startBlock, maxBlocks);
        if (nearestSupportedBlock == null) {
            scheduleBlockFall(startBlock);
            return;
        }

        if (startBlock.getLocation().distance(nearestSupportedBlock.getLocation()) + 1 > maxDistance) {
            scheduleBlockFall(startBlock);
        }
    }

    private Block findNearestSupportedBlock(Block startBlock, int maxBlocks) {
        Queue<Block> toCheck = new LinkedList<>();
        Set<Block> checked = new HashSet<>();
        toCheck.offer(startBlock);

        while (!toCheck.isEmpty() && checked.size() < maxBlocks) {
            Block current = toCheck.poll();
            if (checked.contains(current)) continue;
            checked.add(current);

            if (hasSolidGroundBelow(current)) {
                return current;
            }

            for (BlockFace face : CHECK_FACES) {
                Block relative = current.getRelative(face);
                if (!checked.contains(relative) && relative.getType() == startBlock.getType()) {
                    toCheck.offer(relative);
                }
            }
        }
        return null;
    }


    private boolean hasSolidGroundBelow(Block block) {
        Block current = block;
        if (!block.getType().isSolid()) return false;
        int solidAmount = 0;
        for (int i = 0; i < 15; i++) {
            current = current.getRelative(BlockFace.DOWN);
            Material type = current.getType();

            if(i < 12 && !current.getType().isSolid()) return false;

            if (type.isSolid()) {
                solidAmount++;
            }

        }
        return solidAmount > 10;
    }

    private void scheduleBlockFall(Block block) {
        if (block.getType().isAir()) return;
        final BlockData finalData = block.getBlockData().clone();
        block.setType(Material.AIR);
        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(
                block.getLocation().add(0.5, 0, 0.5),
                finalData
        );
        fallingBlock.setGravity(false);
        fallingBlock.setDropItem(false);
        new BukkitRunnable() {
            @Override
            public void run() {
                fallingBlock.setGravity(true);
            }
        }.runTaskLater(fancyPhysics, (int) (Math.random() * 10));
    }
}