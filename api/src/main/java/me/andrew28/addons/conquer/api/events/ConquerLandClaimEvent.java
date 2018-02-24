package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Cancellable;

/**
 * @author Andrew Tran
 */
public class ConquerLandClaimEvent extends ConquerPlayerFactionEvent implements Cancellable {
    private boolean cancelled = false;
    private boolean claiming;
    private ConquerClaim<?> claim;

    public ConquerLandClaimEvent(ConquerClaim claim, boolean claiming, ConquerFaction faction, ConquerPlayer player) {
        super(faction, player);
        this.claiming = claiming;
        this.claim = claim;
    }

    public boolean isClaiming() {
        return claiming;
    }

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
