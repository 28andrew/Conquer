package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionPowerLossEvent extends Event implements Cancellable{
    private static final HandlerList handlers = new HandlerList();

    private ConquerFaction faction;
    private boolean cancelled = false;

    public ConquerFactionPowerLossEvent(ConquerFaction faction) {
        this.faction = faction;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ConquerFaction getFaction() {
        return faction;
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
