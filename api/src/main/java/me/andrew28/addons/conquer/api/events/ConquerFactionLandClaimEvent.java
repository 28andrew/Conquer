package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionLandClaimEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private ConquerFaction faction;
    private Player claimer;
    private Claim claim;
    private Boolean cancelled = false;

    public ConquerFactionLandClaimEvent(ConquerFaction faction, Player claimer, Claim claim) {
        this.faction = faction;
        this.claimer = claimer;
        this.claim = claim;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ConquerFaction getFaction() {
        return faction;
    }

    public Player getClaimer() {
        return claimer;
    }

    public Claim getClaim() {
        return claim;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
