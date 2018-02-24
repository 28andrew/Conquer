package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionCreateEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled = false;
    private ConquerPlayer player;
    private String name;

    public ConquerFactionCreateEvent(ConquerPlayer player, String name) {
        this.player = player;
        this.name = name;
    }

    public ConquerPlayer getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
