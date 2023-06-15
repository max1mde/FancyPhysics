package com.maximde.fancyphysics.listeners;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Random;

public class PlayerInteractListener implements Listener {
    private FancyPhysics fancyPhysics;
    public PlayerInteractListener(FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        animateTrapdor(event.getClickedBlock(), event);
    }

    private void animateTrapdor(Block clickedBlock, PlayerInteractEvent event) {

        if(clickedBlock == null) return;
        if(!clickedBlock.getType().name().contains("TRAPDOOR")) return;
        final BlockData blockData = clickedBlock.getBlockData();
        event.setCancelled(true);
        clickedBlock.setType(Material.AIR);
        clickedBlock.getLocation().getWorld().spawn(clickedBlock.getLocation(), BlockDisplay.class, blockDisplay -> {

            var leftRotation = new Quaternionf(0,0,0.3,0);
            if (blockData instanceof TrapDoor slab) {
                if(slab.isOpen()) {
                    leftRotation = new Quaternionf(0,0,0,0);
                }
            }

            blockDisplay.setInvulnerable(true);
            blockDisplay.setPersistent(true);
            blockDisplay.setBlock(blockData);
            Transformation transformation = new Transformation(
                    blockDisplay.getTransformation().getTranslation(),
                    leftRotation,
                    new Vector3f(1,1,1),
                    blockDisplay.getTransformation().getRightRotation()
            );
            openTrapdorAnimation(blockDisplay, blockData, clickedBlock);
        });
    }

    private void openTrapdorAnimation(BlockDisplay blockDisplay, BlockData previusBlockData, Block clickedBlock) {

        Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
            clickedBlock.setType(Material.AIR);
            var leftRotation = new Quaternionf(0,0,1,0);
            if (previusBlockData instanceof TrapDoor slab) {
                if(slab.isOpen()) {
                    leftRotation = new Quaternionf(0,0,-1,0);
                }
            }
            Transformation transformationMove = new Transformation(
                    blockDisplay.getTransformation().getTranslation(),
                    leftRotation,
                    new Vector3f(1,1,1),
                    blockDisplay.getTransformation().getRightRotation()
            );
            blockDisplay.setInterpolationDuration(20);
            blockDisplay.setInterpolationDelay(-1);
            blockDisplay.setTransformation(transformationMove);
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
                var block = blockDisplay.getLocation().getBlock();
                var blockData = previusBlockData;
                if (blockData instanceof TrapDoor slab) {
                    slab.setOpen(!(slab.isOpen()));
                    slab.setFacing(BlockFace.EAST);
                }
                block.setBlockData(blockData);
                blockDisplay.remove();
            }, 10L);
            }, 2L);
    }
}
