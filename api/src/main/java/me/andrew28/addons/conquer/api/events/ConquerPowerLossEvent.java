package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;

/**
 * The event called when a player loses power
 * @author Andrew Tran
 */
public class ConquerPowerLossEvent extends ConquerPlayerFactionEvent implements Cancellable {
    private boolean cancelled;

    /**
     * Creates a player power lose event
     * @param faction the faction of the player that lost power
     * @param player the player that lost power
     */
    public ConquerPowerLossEvent(ConquerFaction faction, ConquerPlayer player) {
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
