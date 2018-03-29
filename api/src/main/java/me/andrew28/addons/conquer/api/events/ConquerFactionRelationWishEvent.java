package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.Relation;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The event called when a faction wishes to change relations with another faction
 * @author Andrew Tran
 */
public class ConquerFactionRelationWishEvent extends Event implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    private boolean cancelled;
    private ConquerPlayer caller;
    private ConquerFaction sender, target;
    private Relation oldRelation, newRelation;

    /**
     * Creates a new faction relation change wish event
     * @param caller the player that initiated the change
     * @param sender the faction of the player that initiated the change
     * @param target the targeted faction
     * @param oldRelation the old relation
     * @param newRelation the new relation
     */
    public ConquerFactionRelationWishEvent(ConquerPlayer caller, ConquerFaction sender, ConquerFaction target,
                                           Relation oldRelation, Relation newRelation) {
        this.caller = caller;
        this.sender = sender;
        this.target = target;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
    }

    /**
     * Gets the player that initiated the change
     * @return the player that initiated the change
     */
    public ConquerPlayer getCaller() {
        return caller;
    }

    /**
     * Gets the faction of the player that initiated the change
     * @return the faction of the player that initiated the change
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
