package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerPlayerFactionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ConquerFaction faction;
    private ConquerPlayer player;

    public ConquerPlayerFactionEvent(ConquerFaction faction, ConquerPlayer player) {
        this.faction = faction;
        this.player = player;
    }

    public ConquerFaction getFaction() {
        return faction;
    }

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
