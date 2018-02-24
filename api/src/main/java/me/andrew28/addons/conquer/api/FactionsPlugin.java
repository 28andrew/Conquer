package me.andrew28.addons.conquer.api;

import ch.njol.yggdrasil.Fields;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
public abstract class FactionsPlugin {
    private String name;
    protected EventForwarder eventForwarder;
    protected FactionResolver factionResolver;

    public FactionsPlugin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean canUse();
    public abstract void init();

    public abstract EventForwarder getEventForwarder();
    public abstract FactionResolver getFactionResolver();

    public abstract ConquerPlayer getConquerPlayer(OfflinePlayer player);

    public abstract ConquerClaim<?> getClaim(Location location);
    public abstract void removeClaim(Location location);
    public abstract Class<?> getClaimRepresentationClass();

    public abstract ConquerClaim<?> deserializeClaim(Fields fields) throws StreamCorruptedException;
    public abstract ConquerFaction deserializeFaction(Fields fields) throws StreamCorruptedException;

    public Plugin getPlugin(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
