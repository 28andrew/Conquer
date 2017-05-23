package me.andrew28.addons.conquer.api.events;

import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Andrew Tran
 */
public class ConquerFactionRelationChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private ConquerFaction sender, target;
    private Relation oldRelation, newRelation;

    public ConquerFactionRelationChangeEvent(ConquerFaction sender, ConquerFaction target, Relation oldRelation, Relation newRelation) {
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

    public static enum Relation {
        MEMBER, ALLY, TRUCE, NEUTRAL, ENEMY, OTHER;
    }
}
