package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionRenameEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private ConquerFaction faction;
    private Player player;
    private String oldName, newName;
    private Boolean cancelled = false;

    public ConquerFactionRenameEvent(ConquerFaction faction, Player player, String oldName, String newName) {
        this.faction = faction;
        this.player = player;
        this.oldName = oldName;
        this.newName = newName;
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

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
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
