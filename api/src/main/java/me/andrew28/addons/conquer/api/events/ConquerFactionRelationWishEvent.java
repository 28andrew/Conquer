package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionRelationWishEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private ConquerFaction sender, target;
    private ConquerFactionRelationChangeEvent.Relation oldRelation, newRelation;
    private Boolean cancelled = false;

    public ConquerFactionRelationWishEvent(ConquerFaction sender, ConquerFaction target, ConquerFactionRelationChangeEvent.Relation oldRelation, ConquerFactionRelationChangeEvent.Relation newRelation) {
        this.sender = sender;
        this.target = target;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ConquerFaction getSender() {
        return sender;
    }

    public ConquerFaction getTarget() {
        return target;
    }

    public ConquerFactionRelationChangeEvent.Relation getOldRelation() {
        return oldRelation;
    }

    public ConquerFactionRelationChangeEvent.Relation getNewRelation() {
        return newRelation;
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
