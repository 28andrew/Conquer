package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ConquerFaction faction;

    public ConquerFactionEvent(ConquerFaction faction) {
        this.faction = faction;
    }

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
