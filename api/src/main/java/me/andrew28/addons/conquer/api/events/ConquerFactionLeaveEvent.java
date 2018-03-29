package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;

/**
 * The event when a player leaves their faction
 * @author Andrew Tran
 */
public class ConquerFactionLeaveEvent extends ConquerPlayerFactionEvent implements Cancellable {
    private boolean cancelled;

    /**
     * Creates a player leave faction event
     * @param faction the faction that the player left
     * @param player the player that left the faction
     */
    public ConquerFactionLeaveEvent(ConquerFaction faction, ConquerPlayer player) {
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
