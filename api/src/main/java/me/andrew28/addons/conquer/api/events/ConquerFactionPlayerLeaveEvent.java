package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionPlayerLeaveEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private ConquerFaction faction;
    private LeaveReason leaveReason;
    private Boolean cancelled = false;

    public ConquerFactionPlayerLeaveEvent(ConquerFaction faction, Player player, LeaveReason leaveReason) {
        this.player = player;
        this.faction = faction;
        this.leaveReason = leaveReason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public ConquerFaction getFaction() {
        return faction;
    }

    public LeaveReason getLeaveReason() {
        return leaveReason;
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

    public static enum LeaveReason {
        KICKED, DISBAND, RESET, JOINOTHER, LEAVE, OTHER;
    }
}
