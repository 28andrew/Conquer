package me.andrew28.addons.conquer.api;

import org.bukkit.Location;

/**
 * @author Andrew Tran
 */
public abstract class FactionResolver {
    public abstract ConquerFaction[] getAll();
    public abstract ConquerFaction getAtLocation(Location location);
    // TODO : Implement expr for getByName
    public abstract ConquerFaction getByName(String name);
    public abstract ConquerFaction getByClaim(ConquerClaim claim);
}
