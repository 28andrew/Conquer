package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.Relation;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The event called when the relations change between two factions
 * @author Andrew Tran
 */
public class ConquerFactionRelationEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private ConquerFaction sender, target;
    private Relation oldRelation, newRelation;
    private boolean cancelled;

    /**
     * Creates a new faction relation change event
     * @param sender the faction that initiated the change
     * @param target the targeted faction
     * @param oldRelation the old relation
     * @param newRelation the new relation
     */
    public ConquerFactionRelationEvent(ConquerFaction sender, ConquerFaction target, Relation oldRelation, Relation newRelation) {
        this.sender = sender;
        this.target = target;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
    }

    /**
     * Gets the faction that initiated the change
     * @return the faction that initiated the change
     */
    public ConquerFaction getSender() {
        return sender;
    }

    /**
     * Gets the targeted faction
     * @return the targeted faction
     */
    public ConquerFaction getTarget() {
        return target;
    }

    /**
     * Gets the old relation
     * @return the old relation
     */
    public Relation getOldRelation() {
        return oldRelation;
    }

    /**
     * Gets the new relation
     * @return the new relation
     */
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
