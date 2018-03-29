package me.andrew28.addons.conquer.api;

/**
 * Represents the relationship between two entities like two {@link ConquerFaction}s
 * @author Andrew Tran
 */
public enum Relation {
    /**
     * Implementations should avoid using this
     */
    @Deprecated
    MEMBER,
    /**
     * A relationship where two entities are partnered or allied together
     */
    ALLY,
    /**
     * A relationship where two entities are truced
     */
    TRUCE,
    /**
     * A relationship where two entities are neither enemies, allies, or truced
     */
    NEUTRAL,
    /**
     * A relationship where two entities are enemies to one another
     */
    ENEMY,
    /**
     * An unknown relationship, may represent different things depending on the implementation
     */
    OTHER
}
