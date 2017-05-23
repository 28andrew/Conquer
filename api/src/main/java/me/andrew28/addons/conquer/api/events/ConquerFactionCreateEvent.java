package me.andrew28.addons.conquer.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionCreateEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Player creator;
    private String factionIdentifier;
    private Boolean cancelled = false;

    public ConquerFactionCreateEvent(Player creator, String factionIdentifier) {
        this.creator = creator;
        this.factionIdentifier = factionIdentifier;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getCreator() {
        return creator;
    }

    public void setCreator(Player creator) {
        this.creator = creator;
    }

    public String getFactionIdentifier() {
        return factionIdentifier;
    }

    public void setFactionIdentifier(String factionIdentifier) {
        this.factionIdentifier = factionIdentifier;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
