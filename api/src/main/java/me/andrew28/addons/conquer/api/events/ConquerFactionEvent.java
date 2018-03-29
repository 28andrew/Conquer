package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event that involves a faction
 * @author Andrew Tran
 */
public class ConquerFactionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ConquerFaction faction;

    /**
     * Creates a new event that involves a faction
     * @param faction the faction that was involved in this event
     */
    public ConquerFactionEvent(ConquerFaction faction) {
        this.faction = faction;
    }

    /**
     * Gets the faction involved in this event
     * @return the faction involved in this event
     */
    public ConquerFaction getFaction() {
        return faction;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
