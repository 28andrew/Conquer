package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionPlayerJoinEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private ConquerFaction faction;
    private Player player;
    private JoinReason joinReason;
    private Boolean cancelled = false;

    public ConquerFactionPlayerJoinEvent(ConquerFaction faction, Player player, JoinReason joinReason) {
        this.faction = faction;
        this.player = player;
        this.joinReason = joinReason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ConquerFaction getFaction() {
        return faction;
    }

    public Player getPlayer() {
        return player;
    }

    public JoinReason getJoinReason() {
        return joinReason;
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

    public static enum JoinReason {
        CREATE, LEADER, COMMAND, OTHER;
    }
}
