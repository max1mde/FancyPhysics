package com.maximde.fancyphysics.api;

import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * Gets called when a block particle or blood particle simulation
 * in the particle generator is used.
 */
public class ParticleEffectEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Location location;
    private final List<BlockDisplay> particleDisplayList;

    public ParticleEffectEvent(Location location, List<BlockDisplay> particleDisplayList) {
        this.location = location;
        this.particleDisplayList = particleDisplayList;
    }

    public Location getLocation() {
        return this.location;
    }

    public List<BlockDisplay> getParticleDisplayList() {
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
