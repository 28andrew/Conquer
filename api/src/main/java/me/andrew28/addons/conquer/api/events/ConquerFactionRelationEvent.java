package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.Relation;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionRelationEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private ConquerFaction sender, target;
    private Relation oldRelation, newRelation;
    private boolean cancelled;

    public ConquerFactionRelationEvent(ConquerFaction sender, ConquerFaction target, Relation oldRelation, Relation newRelation) {
        this.sender = sender;
        this.target = target;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
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
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
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
