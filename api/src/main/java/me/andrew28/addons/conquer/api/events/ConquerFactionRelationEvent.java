package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.Relation;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionRelationEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ConquerFaction sender, target;
    private Relation oldRelation, newRelation;

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
        return null;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
