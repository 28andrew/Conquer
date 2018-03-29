package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;

/**
 * The event called when a faction claims/declaims a piece of land
 * @author Andrew Tran
 */
public class ConquerLandClaimEvent extends ConquerPlayerFactionEvent implements Cancellable {
    private boolean cancelled = false;
    private boolean claiming;
    private ConquerClaim<?> claim;

    /**
     * Creates a faction claim/declaim event
     * @param claim the claim involved
     * @param claiming whether the claim is being claimed (false if declaiming)
     * @param faction the faction involved
     * @param player the player that initiated the change
     */
    public ConquerLandClaimEvent(ConquerClaim claim, boolean claiming, ConquerFaction faction, ConquerPlayer player) {
        super(faction, player);
        this.claiming = claiming;
        this.claim = claim;
    }

    /**
     * Gets whether the claim is being claimed (false if being declaimed)
     * @return whether the claim is being claimed
     */
    public boolean isClaiming() {
        return claiming;
    }

    /**
     * Gets the claim involved
     * @return the claim involved
     */
    public ConquerClaim<?> getClaim() {
        return claim;
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
