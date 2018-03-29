package me.andrew28.addons.conquer.api;

import ch.njol.yggdrasil.Fields;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.io.StreamCorruptedException;

/**
 * The base class for any Factions plugin implementation
 * @author Andrew Tran
 */
public abstract class FactionsPlugin {
    private String name;
    protected EventForwarder eventForwarder;
    protected FactionResolver factionResolver;

    /**
     * All implementations should {@code super} to this constructor with the plugin name
     * and have a no-args constructor
     * @param name the name of the plugin
     */
    public FactionsPlugin(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this plugin
     * @return the name of this plugin
     */
    public String getName() {
        return name;
    }

    /**
     * Gets whether this implementation can be used
     * @return whether this implementation can be used
     * @see #getPlugin(String)
     */
    public abstract boolean canUse();

    /**
     * Called when the implementation is decided to be used. It should initialize fields and etc
     */
    public abstract void init();

    /**
     * Gets the {@link EventForwarder} implementation
     * @return the {@link EventForwarder} implementation
     */
    public abstract EventForwarder getEventForwarder();

    /**
     * Gets the {@link FactionResolver} implementation
     * @return the {@link FactionResolver} implementation
     */
    public abstract FactionResolver getFactionResolver();

    /**
     * Gets a {@link ConquerPlayer} from an {@link OfflinePlayer}
     * @param player the player to get the ConquerPlayer from
     * @return the ConquerPlayer represented by the specified player
     */
    public abstract ConquerPlayer getConquerPlayer(OfflinePlayer player);

    /**
     * Gets a claim at a location
     * @param location the location to get the claim at
     * @return the claim at the specified location
     */
    public abstract ConquerClaim<?> getClaim(Location location);

    /**
     * Removes the claim at a location
     * @param location the location to remove the claim at
     */
    public abstract void removeClaim(Location location);

    /**
     * Gets the type of the object used to represent claims (usually {@link org.bukkit.Chunk})
     * @return the type of object used to represent claims
     */
    public abstract Class<?> getClaimRepresentationClass();

    /**
     * Deserializes a claim from fields
     * @param fields the fields to deserialize a claim from
     * @return the deserialized claim
     * @throws StreamCorruptedException if the fields aren't compatible with this implementation's spec
     */
    public abstract ConquerClaim<?> deserializeClaim(Fields fields) throws StreamCorruptedException;

    /**
     * Deserializes a faction from fields
     * @param fields the fields to deserialize a faction from
     * @return the deserialized faction
     * @throws StreamCorruptedException if the fields aren't compatible with this implementation's spec
     */
    public abstract ConquerFaction deserializeFaction(Fields fields) throws StreamCorruptedException;

    /**
     * Gets a plugin by it's name.
     * @param name the plugin name
     * @return the plugin with the specified name, null if it is non-existant
     */
    protected Plugin getPlugin(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
