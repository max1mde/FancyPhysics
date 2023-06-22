package com.maximde.fancyphysics.components;

import com.maximde.fancyphysics.FancyPhysics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleDisplay {

    private BlockDisplay blockDisplay;
    private FancyPhysics fancyPhysics;

    public ParticleDisplay(Block block, float x, float y, float z, FancyPhysics fancyPhysics) {
        this.fancyPhysics = fancyPhysics;
        spawnBlockDisplay(block, x, y, z);
    }

    public ParticleDisplay(Location location, Material particleMaterial, float x, float y, float z, FancyPhysics fancyPhysics, float startSize) {
        this.fancyPhysics = fancyPhysics;
        spawnBlockDisplay(location, particleMaterial, x, y, z, startSize);
    }

    public void doNothing() {

    }

    private void spawnBlockDisplay(Location location, Material particleMaterial, float x, float y, float z, float startSize) {
        var loc = new Location(location.getWorld(), (float)((int)location.getX()) + x, (float)((int)location.getY()) + y, (float)((int)location.getZ()) + z);
        float randomSize = ThreadLocalRandom.current().nextFloat() * 10;
        final var material = getParticleMaterial(particleMaterial);
        final BlockData blockData = material.createBlockData();
        loc.getWorld().spawn(loc, BlockDisplay.class, blockDisplay -> {
            this.blockDisplay = blockDisplay;
            blockDisplay.setInvulnerable(true);
            blockDisplay.setPersistent(true);
            blockDisplay.setBlock(blockData);
            if(material == Material.FIRE) {
                blockDisplay.setBrightness(new Display.Brightness(15,15));
            }
            Transformation transformation = new Transformation(
                    blockDisplay.getTransformation().getTranslation(),
                    blockDisplay.getTransformation().getLeftRotation(),
                    new Vector3f(startSize,startSize,startSize),
                    blockDisplay.getTransformation().getRightRotation()
            );
            animateDisplay(x, z, blockDisplay, transformation);

        });

    }

    private void animateDisplay(float x, float z, BlockDisplay blockDisplay, Transformation transformation) {
        blockDisplay.setInterpolationDelay(-1);
        blockDisplay.setInterpolationDuration(0);
        blockDisplay.setTransformation(transformation);

        float randomY = ThreadLocalRandom.current().nextFloat()  / 5;

        Random random = new Random();
        float randomZ = random.nextFloat();
        float randomX = random.nextFloat();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, () -> {
            Vector3f translationMove = new Vector3f((x - 0.4F) * (randomX * 9), -3.3F + randomY, (z - 0.4F) * (randomZ * 9));
            Transformation transformationMove = new Transformation(
                    translationMove,
                    blockDisplay.getTransformation().getLeftRotation(),
                    new Vector3f(1F / 100F,1F / 100F,1F / 100F),
                    blockDisplay.getTransformation().getRightRotation()
            );
            blockDisplay.setInterpolationDuration(35);
            blockDisplay.setInterpolationDelay(-1);
            blockDisplay.setTransformation(transformationMove);


            Bukkit.getScheduler().scheduleSyncDelayedTask(this.fancyPhysics, blockDisplay::remove, 35L);
        }, 2L);
    }

    private void spawnBlockDisplay(Block block, float x, float y, float z) {
        Location location = new Location(block.getLocation().getWorld(), (float)((int)block.getLocation().getX()) + x, (float)((int)block.getLocation().getY()) + y, (float)((int)block.getLocation().getZ()) + z);
        float randomSize = ThreadLocalRandom.current().nextFloat() * 10;
        final var material = getParticleMaterial(block.getType());
        final BlockData blockData = material.createBlockData();
        block.getWorld().spawn(location, BlockDisplay.class, blockDisplay -> {
            this.blockDisplay = blockDisplay;
            blockDisplay.setInvulnerable(true);
            blockDisplay.setPersistent(true);
            blockDisplay.setBlock(blockData);

            Transformation transformation = new Transformation(
                    blockDisplay.getTransformation().getTranslation(),
                    blockDisplay.getTransformation().getLeftRotation(),
                    new Vector3f(10.0F / 30,10.0F / (30 + randomSize),10.0F / 30),
                    blockDisplay.getTransformation().getRightRotation()
            );
            animateDisplay(x, z, blockDisplay, transformation);

        });

    }

    public BlockDisplay getBlockDisplay() {
        return blockDisplay;
    }

    private Material getParticleMaterial(Material type) {
        return switch (type) {
            case GRASS_BLOCK -> Material.DIRT;
            case VINE -> Material.AIR;
            default -> type;
        };
    }
}
