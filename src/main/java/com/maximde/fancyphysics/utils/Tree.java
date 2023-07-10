package com.maximde.fancyphysics.utils;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Tree {
    public boolean isNatural;
    private final Block ORIGIN;
    private final Material WOOD_MATERIAL;
    private final Material LEAVE_MATERIAL;
    public final ArrayList<Block> STEM = new ArrayList<>();
    public final ArrayList<Block> LEAVES = new ArrayList<>();
    private final FancyPhysics FANCY_PHYSICS;

    public Tree(Block origin, FancyPhysics fancyPhysics) {
        this.FANCY_PHYSICS = fancyPhysics;
        this.ORIGIN = origin;
        this.WOOD_MATERIAL = origin.getType();
        this.LEAVE_MATERIAL = getLeaveType(this.WOOD_MATERIAL);
        scanTree(origin);
        this.isNatural = (this.STEM.size() > 3 && this.LEAVES.size() > 10);
    }

    public void breakWithFallAnimation() {
        if(!isNatural) return;
        for (Block b : this.STEM) {
            spawnDisplay(b);
        }
        for (Block b : this.LEAVES) {
            spawnDisplay(b);
        }
    }

    private void spawnDisplay(Block block) {
        final var location = block.getLocation();
        final BlockData blockData = block.getType().createBlockData();

        /*
         * Spawn block display
         */
        location.getWorld().spawn(location, BlockDisplay.class, blockDisplay -> {
            this.FANCY_PHYSICS.blockDisplayList.add(blockDisplay);
            blockDisplay.setBlock(blockData);
            block.setType(Material.AIR);

            var transformationY = - 1 + (this.ORIGIN.getY() - (block.getY() + 0.7F)) + 1;
            var transformationZ = (this.ORIGIN.getY() - block.getY()) + (this.ORIGIN.getY() - block.getY()) / 0.7F;

            /*
            Transform display
             */
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.FANCY_PHYSICS, () -> {
                Transformation transformation = new Transformation(
                        new Vector3f(0, transformationY + (this.ORIGIN.getY() - (block.getY() + 0.7F)) / 2, transformationZ),//translation
                        new Quaternionf(-1.1F,0,0,0.5),   //left rotation
                        new Vector3f(1, 1,1),    //scale
                        blockDisplay.getTransformation().getRightRotation()  //right rotation
                );
                blockDisplay.setInterpolationDuration(40);
                blockDisplay.setInterpolationDelay(-1);
                blockDisplay.setTransformation(transformation);

                /*
                Break tree
                 */
                Bukkit.getScheduler().scheduleSyncDelayedTask(this.FANCY_PHYSICS, () -> {
                    final var loc = blockDisplay.getLocation().add(0.5,(this.ORIGIN.getY() - (block.getY() + 0.7F)) + 1, transformationY);
                    if(false) { //TODO add option to config
                        blockDisplay.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 50, blockData);
                    } else {
                        this.FANCY_PHYSICS.particleGenerator.simulateBlockParticles(blockDisplay.getLocation().add(0,(this.ORIGIN.getY() - (block.getY() + 0.7F)) + 1.5F, transformationY -0.5F), blockData.getMaterial());
                    }
                    removeTree(blockDisplay, transformationY, loc, blockData);
                }, 18L);
            }, 2L);
        });
    }

    private void removeTree(BlockDisplay blockDisplay, float transformationY, Location loc, BlockData blockData) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.FANCY_PHYSICS, () -> {
            if(this.FANCY_PHYSICS.config.isDropSaplings()) {
                var b = blockDisplay.getLocation().add(0, transformationY + 2, transformationY).getBlock();
                if(b.getType() == Material.AIR) {
                    b.setType(blockData.getMaterial());
                    b.breakNaturally();
                }
            } else {
                blockDisplay.getLocation().getWorld().dropItem(blockDisplay.getLocation().add(0, transformationY + 2, transformationY), new ItemStack(blockData.getMaterial()));
            }
            blockDisplay.remove();
        }, 4L);
    }

    public void breakInstant() {
        if(!isNatural) return;
        for (Block b : this.STEM)
            b.breakNaturally();
        for (Block b : this.LEAVES)
            b.breakNaturally();
    }

    public void breakInstantWithParticles() {
        if(!isNatural) return;
        for (Block b : this.STEM) {
            this.FANCY_PHYSICS.particleGenerator.simulateBlockParticles(b);
            b.breakNaturally();
        }
        for (Block b : this.LEAVES) {
            this.FANCY_PHYSICS.particleGenerator.simulateBlockParticles(b);
            b.breakNaturally();
        }
    }


    private Material getLeaveType(Material wood) {
        return switch (wood) {
            case OAK_LOG -> Material.OAK_LEAVES;
            case DARK_OAK_LOG -> Material.DARK_OAK_LEAVES;
            case JUNGLE_LOG -> Material.JUNGLE_LEAVES;
            case ACACIA_LOG -> Material.ACACIA_LEAVES;
            case BIRCH_LOG -> Material.BIRCH_LEAVES;
            case SPRUCE_LOG -> Material.SPRUCE_LEAVES;
            case CHERRY_LOG -> Material.CHERRY_LEAVES;
            case MANGROVE_LOG -> Material.MANGROVE_LEAVES;
            case WARPED_STEM -> Material.WARPED_WART_BLOCK;
            case CRIMSON_STEM -> Material.NETHER_WART_BLOCK;
            default -> Material.AIR;
        };
    }

    private void scanTree(Block block) {
        if (Math.abs(block.getX() - this.ORIGIN.getX()) > 3 || Math.abs(block.getZ() - this.ORIGIN.getZ()) > 3)
            return;
        if (block.getType() == this.WOOD_MATERIAL) {
            if (this.STEM.size() < 100) {
                if (this.STEM.contains(block))
                    return;
                this.STEM.add(block);
            } else {
                this.isNatural = false;
                return;
            }
        } else if (block.getType() == this.LEAVE_MATERIAL) {
            if (this.LEAVES.size() < 100) {
                if (this.LEAVES.contains(block))
                    return;
                this.LEAVES.add(block);
            } else {
                this.isNatural = false;
                return;
            }
        }
        Block down = block.getRelative(BlockFace.DOWN);
        Block up = block.getRelative(BlockFace.UP);
        Block south = block.getRelative(BlockFace.SOUTH);
        Block north = block.getRelative(BlockFace.NORTH);
        Block east = block.getRelative(BlockFace.EAST);
        Block west = block.getRelative(BlockFace.WEST);

        if (up.getType() == this.WOOD_MATERIAL || up.getType() == this.LEAVE_MATERIAL)
            scanTree(up);
        if (down.getType() == this.LEAVE_MATERIAL || (down.getType() == this.WOOD_MATERIAL && down.getY() >= this.ORIGIN.getY()))
            scanTree(down);
        if (south.getType() == this.WOOD_MATERIAL || south.getType() == this.LEAVE_MATERIAL)
            scanTree(south);
        if (north.getType() == this.WOOD_MATERIAL || north.getType() == this.LEAVE_MATERIAL)
            scanTree(north);
        if (east.getType() == this.WOOD_MATERIAL || east.getType() == this.LEAVE_MATERIAL)
            scanTree(east);
        if (west.getType() == this.WOOD_MATERIAL || west.getType() == this.LEAVE_MATERIAL)
            scanTree(west);
    }
}