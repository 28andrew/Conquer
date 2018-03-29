package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;

/**
 * The event called when a faction declaims all of their land
 * @author Andrew Tran
 */
public class ConquerUnclaimAllEvent extends ConquerPlayerFactionEvent implements Cancellable {
    private boolean cancelled = false;

    /**
     * Creates a faction declaim all claims event
     * @param faction the faction that declaimed all of their land
     * @param player the player that initiated this declaiming
     */
    public ConquerUnclaimAllEvent(ConquerFaction faction, ConquerPlayer player) {
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
