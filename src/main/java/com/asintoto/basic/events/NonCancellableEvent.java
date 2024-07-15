package com.asintoto.basic.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class NonCancellableEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
