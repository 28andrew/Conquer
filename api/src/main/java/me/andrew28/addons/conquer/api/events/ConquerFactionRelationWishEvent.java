package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.Relation;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionRelationWishEvent extends Event implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    private boolean cancelled;
    private ConquerPlayer caller;
    private ConquerFaction sender, target;
    private Relation oldRelation, newRelation;

    public ConquerFactionRelationWishEvent(ConquerPlayer caller, ConquerFaction sender, ConquerFaction target,
                                           Relation oldRelation, Relation newRelation) {
        this.caller = caller;
        this.sender = sender;
        this.target = target;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
    }

    public ConquerPlayer getCaller() {
        return caller;
    }

    public ConquerFaction getSender() {
        return sender;
    }

    public ConquerFaction getTarget() {
        return target;
    }

    public Relation getOldRelation() {
        return oldRelation;
    }

    public Relation getNewRelation() {
        return newRelation;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
