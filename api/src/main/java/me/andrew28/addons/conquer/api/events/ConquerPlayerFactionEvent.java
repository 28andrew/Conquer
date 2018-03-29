package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event that involves both a player and a faction, not necessarily the faction of that player
 * @author Andrew Tran
 */
public class ConquerPlayerFactionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ConquerFaction faction;
    private ConquerPlayer player;

    /**
     * Creates an event that involves both a player and a faction
     * @param faction the faction involved
     * @param player the player involved
     */
    public ConquerPlayerFactionEvent(ConquerFaction faction, ConquerPlayer player) {
        this.faction = faction;
        this.player = player;
    }

    /**
     * Gets the faction involved
     * @return the faction involved
     */
    public ConquerFaction getFaction() {
        return faction;
    }

    /**
     * Gets the player involved
     * @return  the player involved
     */
    public ConquerPlayer getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
