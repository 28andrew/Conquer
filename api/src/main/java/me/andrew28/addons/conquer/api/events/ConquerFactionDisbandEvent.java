package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;

/**
 * The event when a faction is disbanded
 * @author Andrew Tran
 */
public class ConquerFactionDisbandEvent extends ConquerPlayerFactionEvent implements Cancellable {
    private boolean cancelled = false;

    /**
     * Creates a new faction disband event
     * @param faction the faction that was disbanded
     * @param player the player that disbanded the faction
     */
    public ConquerFactionDisbandEvent(ConquerFaction faction, ConquerPlayer player) {
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
