package com.maximde.fancyphysics.api;

import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Gets called when creating a ParticleDisplay
 * When using the ParticleGenerator for example it will be called for every particle of one effect
 */
public class ParticleSpawnEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Location location;
    private final BlockDisplay particleDisplayList;

    public ParticleSpawnEvent(Location location, BlockDisplay particleDisplayList) {
        this.location = location;
        this.particleDisplayList = particleDisplayList;
    }

    public Location getLocation() {
        return this.location;
    }

    public BlockDisplay getParticleDisplayList() {
        return this.particleDisplayList;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
