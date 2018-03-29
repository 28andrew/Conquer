package me.andrew28.addons.conquer.api;

import org.bukkit.Location;

/**
 * A class which allows you to resolve factions through a variety of ways
 * @author Andrew Tran
 */
public abstract class FactionResolver {
    /**
     * Resolve all the factions
     * @return all the factions
     */
    public abstract ConquerFaction[] getAll();

    /**
     * Resolve a faction at a location
     * @param location the location to look for a faction at
     * @return the faction at the specified location
     */
    public abstract ConquerFaction getAtLocation(Location location);

    /**
     * Resolve a faction by a name (aka tag)
     * @param name the name (aka tag) of the faction to resolve for
     * @return the resolved faction with the specified name
     */
    public abstract ConquerFaction getByName(String name);

    /**
     * Resolve a faction at a claim
     * @param claim the claim to resolve for a faction at
     * @return the resolved faction at the specified claim
     */
    public abstract ConquerFaction getByClaim(ConquerClaim claim);
}
