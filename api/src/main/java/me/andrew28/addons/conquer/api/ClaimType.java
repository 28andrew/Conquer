package me.andrew28.addons.conquer.api;

/**
 * Types of a {@link ConquerClaim}
 * @author Andrew Tran
 */
public enum ClaimType {
    /**
     * Wilderness, land which is unclaimed
     */
    WILDERNESS,
    /**
     * Safe zone, land where PVP isn't allowed
     */
    SAFE_ZONE,
    /**
     * War zone, where PVP is always allowed
     */
    WAR_ZONE,
    /**
     * Faction, land claimed by a normal faction
     */
    FACTION
}
