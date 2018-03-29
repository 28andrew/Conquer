package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The event when a faction is created
 * @author Andrew Tran
 */
public class ConquerFactionCreateEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled = false;
    private ConquerPlayer player;
    private String name;

    /**
     * Creates a new faction create event
     * @param player the player that created the faction
     * @param name the name of the new faction
     */
    public ConquerFactionCreateEvent(ConquerPlayer player, String name) {
        this.player = player;
        this.name = name;
    }

    /**
     * Gets the player that created the faction
     * @return the player that created the faction
     */
    public ConquerPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the name of the newly created faction
     * @return the name of the newly created faction
     */
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
