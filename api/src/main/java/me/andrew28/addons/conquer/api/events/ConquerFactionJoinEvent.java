package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;

/**
 * The event when a player joins a faction
 * @author Andrew Tran
 */
public class ConquerFactionJoinEvent extends ConquerPlayerFactionEvent implements Cancellable {
    private boolean cancelled;

    /**
     * Creates a new player join faction event
     * @param faction the faction that the player joined
     * @param player the player that joined the faction
     */
    public ConquerFactionJoinEvent(ConquerFaction faction, ConquerPlayer player) {
        super(faction, player);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
