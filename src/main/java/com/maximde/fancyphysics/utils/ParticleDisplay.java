package com.maximde.fancyphysics.utils;

import com.maximde.fancyphysics.FancyPhysics;
import com.maximde.fancyphysics.api.events.ParticleSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleDisplay {
    private BlockDisplay blockDisplay;
    private final FancyPhysics fancyPhysics;
    private float speed = 1;
    private float startSize = 0F;
    private final Material particleMaterial;
    private int lightLevel = -1;
    private float gravity = 0;

    /**
     * Constructs a ParticleDisplay object with the given block, offsets, and FancyPhysics instance.
     *
     * @param block         The block from which the particle display originates.
     * @param x             The x-axis offset of the particle display.
     * @param y             The y-axis offset of the particle display.
     * @param z             The z-axis offset of the particle display.
     * @param fancyPhysics  The FancyPhysics instance.
     */
    public ParticleDisplay(Block block, float x, float y, float z, FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
        this.particleMaterial = block.getType();
        spawnBlockDisplay(block.getLocation(), x, y, z);
    }

    /**
     * Constructs a ParticleDisplay object with the given location, particle material, offsets, and FancyPhysics instance.
     *
     * @param location          The location from which the particle display originates.
     * @param particleMaterial  The material of the particle display.
     * @param x                 The x-axis offset of the particle display.
     * @param y                 The y-axis offset of the particle display.
     * @param z                 The z-axis offset of the particle display.
     * @param fancyPhysics      The FancyPhysics instance.
     * @param startSize         The starting size of the particle display.
     */
    public ParticleDisplay(Location location, Material particleMaterial, float x, float y, float z, FancyPhysics fancyPhysics, float startSize) {
        this.fancyPhysics = fancyPhysics;
        this.startSize = startSize;
        this.particleMaterial = particleMaterial;
        spawnBlockDisplay(location, x, y, z);
    }

    /**
     * Constructs a ParticleDisplay object with the given location, particle material, offsets, speed, and FancyPhysics instance.
     *
     * @param location          The location from which the particle display originates.
     * @param particleMaterial  The material of the particle display.
     * @param x                 The x-axis offset of the particle display.
     * @param y                 The y-axis offset of the particle display.
     * @param z                 The z-axis offset of the particle display.
     * @param fancyPhysics      The FancyPhysics instance.
     * @param startSize         The starting size of the particle display.
     * @param speed             The speed of the particle display.
     */
    public ParticleDisplay(Location location, Material particleMaterial, float x, float y, float z, FancyPhysics fancyPhysics, float startSize, float speed) {
        this.fancyPhysics = fancyPhysics;
        this.speed = speed;
        this.startSize = startSize;
        this.particleMaterial = particleMaterial;
        spawnBlockDisplay(location, x, y, z);
    }


    public ParticleDisplay(float gravity, Location location, Material particleMaterial, float x, float y, float z, FancyPhysics fancyPhysics, float startSize, float speed) {
        this.fancyPhysics = fancyPhysics;
        this.speed = speed;
        this.startSize = startSize;
        this.particleMaterial = particleMaterial;
        this.gravity = gravity;
        spawnBlockDisplay(location, x, y, z);
    }
    /**
     * Constructs a ParticleDisplay object with the given location, particle material, offsets, speed, light level, and FancyPhysics instance.
     *
     * @param location          The location from which the particle display originates.
     * @param particleMaterial  The material of the particle display.
     * @param x                 The x-axis offset of the particle display.
     * @param y                 The y-axis offset of the particle display.
     * @param z                 The z-axis offset of the particle display.
     * @param fancyPhysics      The FancyPhysics instance.
     * @param startSize         The starting size of the particle display.
     * @param speed             The speed of the particle display.
     * @param lightLevel        The light level of the particle display.
     */
    public ParticleDisplay(Location location, Material particleMaterial, float x, float y, float z, FancyPhysics fancyPhysics, float startSize, float speed, int lightLevel) {
        this.fancyPhysics = fancyPhysics;
        this.speed = speed;
        this.startSize = startSize;
        this.particleMaterial = particleMaterial;
        this.lightLevel = lightLevel;
        spawnBlockDisplay(location, x, y, z);
    }

    /**
     * Spawns the block display at the given location with the specified offsets.
     *
     * @param location  The location at which to spawn the block display.
     * @param x         The x-axis offset of the block display.
     * @param y         The y-axis offset of the block display.
     * @param z         The z-axis offset of the block display.
     */
    private void spawnBlockDisplay(Location location, float x, float y, float z) {
        var loc = new Location(location.getWorld(), (float)((int)location.getX()) + x, (float)((int)location.getY()) + y, (float)((int)location.getZ()) + z);
        float randomSize = ThreadLocalRandom.current().nextFloat() * 10;
        var material = this.particleMaterial;
        if(material == null) material = location.getBlock().getType();
        final BlockData blockData = material.createBlockData();
        if(this.fancyPhysics.displayList.size() > this.fancyPhysics.getPluginConfig().getMaxParticleCount()) return;

        loc.getWorld().spawn(loc, BlockDisplay.class, blockDisplay -> {
            Vector3f size = new Vector3f(this.startSize,this.startSize,this.startSize);
            if(this.startSize == 0) size = new Vector3f(10.0F / 30,10.0F / (30 + randomSize),10.0F / 30);

            ParticleSpawnEvent event = new ParticleSpawnEvent(blockDisplay.getLocation(), blockDisplay);
            Bukkit.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                blockDisplay.remove();
                return;
            }

            this.blockDisplay = blockDisplay;
            this.fancyPhysics.displayList.add(this.blockDisplay);
            blockDisplay.setInvulnerable(true);
            blockDisplay.setPersistent(true);
            blockDisplay.setBlock(blockData);
            blockDisplay.setViewRange(0);
            if(this.lightLevel > -1) {
                blockDisplay.setBrightness(new Display.Brightness(this.lightLevel, this.lightLevel));
            }

            Transformation transformation = new Transformation(
                    blockDisplay.getTransformation().getTranslation(),
                    blockDisplay.getTransformation().getLeftRotation(),
                    size,
                    blockDisplay.getTransformation().getRightRotation()
            );
            animateDisplay(x, z, blockDisplay, transformation, loc, y);
        });
    }

    /**
     * Animates the display by moving it and updating its rotation (simulating physics).
     *
     * @param x             The x-axis offset of the display.
     * @param z             The z-axis offset of the display.
     * @param blockDisplay  The block display to animate.
     * @param transformation The transformation of the block display.
     */
    private void animateDisplay(float x, float z, BlockDisplay blockDisplay, Transformation transformation, Location location, float relativeY) {
        blockDisplay.setInterpolationDelay(-1);
        blockDisplay.setInterpolationDuration(0);
        blockDisplay.setTransformation(transformation);

        float randomY = ThreadLocalRandom.current().nextFloat()  / 10;

        Random random = new Random();
        float randomZ = random.nextFloat();
        float randomX = random.nextFloat();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {

            boolean firstBelow = location.getWorld().getBlockAt(location.clone().subtract(0, 1, 0)).getType().isSolid();
            boolean secondBelow = location.getWorld().getBlockAt(location.clone().subtract(0, 2, 0)).getType().isSolid();
            float blockModifier = 0;
            if (secondBelow) blockModifier = 1.3F;
            if (firstBelow) blockModifier = (3.3F - randomY) - relativeY * 2;

            var translationMove = new Vector3f((x - 0.4F) * (randomX * (9 * this.speed)), -3.3F + randomY + this.gravity + blockModifier, (z - 0.4F) * (randomZ * (9 * this.speed)));

            var rotationLeft = blockDisplay.getTransformation().getLeftRotation();
            var rotationRight = blockDisplay.getTransformation().getLeftRotation();
            if(this.fancyPhysics.getPluginConfig().isParticleRotation()) {
                rotationLeft = new Quaternionf(x * randomZ / 2, x * randomZ / 2, x * randomZ / 2, 0);
                rotationRight = new Quaternionf(x * randomZ / 2, x * randomZ / 2, x * randomZ / 2, 0);
            }

            Vector3f scale = new Vector3f(1F / 20F * x,1F / 20F * x,1F / 20F * x);

            if(secondBelow || firstBelow) scale = new Vector3f(0.9F,0.9F,0.9F);

            Transformation transformationMove = new Transformation(
                    translationMove,
                    rotationLeft,
                    scale,
                    rotationRight
            );
            blockDisplay.setInterpolationDuration(fancyPhysics.getPluginConfig().getParticleAnimationSpeed());
            blockDisplay.setInterpolationDelay(-1);
            blockDisplay.setTransformation(transformationMove);
            blockDisplay.setViewRange(5);
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, this::removeDisplay, fancyPhysics.getPluginConfig().getParticleAnimationSpeed());
        }, 2L);
    }

    /**
     * Removes the block display from the FancyPhysics block display list and removes it from the world.
     */
    private void removeDisplay() {
        this.fancyPhysics.displayList.remove(this.blockDisplay);
        this.blockDisplay.remove();
    }

    /**
     * Returns the block display associated with this ParticleDisplay.
     *
     * @return The block display.
     */
    public BlockDisplay getBlockDisplay() {
        return blockDisplay;
    }

}
