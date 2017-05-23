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
public class ConquerFactionLandUnclaimEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private ConquerFaction faction;
    private Player unclaimer;
    private Claim claim;
    private Boolean cancelled = false;

    public ConquerFactionLandUnclaimEvent(ConquerFaction faction, Player unclaimer, Claim claim) {
        this.faction = faction;
        this.unclaimer = unclaimer;
        this.claim = claim;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ConquerFaction getFaction() {
        return faction;
    }

    public Player getUnclaimer() {
        return unclaimer;
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
