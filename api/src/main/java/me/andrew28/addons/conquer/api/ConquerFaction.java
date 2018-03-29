package me.andrew28.addons.conquer.api;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrew Tran
 */
public abstract class ConquerFaction implements PowerHolder, SerializableToFields {
    public abstract String getId();
    public abstract void setId(String id);

    public abstract String getName();
    public abstract void setName(String name);

    public abstract String getDescription();
    public abstract void setDescription(String description);

    public abstract String getMotd();
    public abstract void setMotd(String motd);

    public abstract Date getCreationDate();
    public abstract void setCreationDate(Date date);

    public abstract double getPowerBoost();
    public abstract void setPowerBoost(double powerBoost);

    public abstract ConquerPlayer getLeader();
    public abstract void setLeader(ConquerPlayer leader);

    public abstract Location getHome();
    public abstract void setHome(Location home);

    public abstract ConquerPlayer[] getMembers();
    public abstract void addMember(ConquerPlayer member);
    public abstract void removeMember(ConquerPlayer member);

    public abstract CommandSender getSender();

    public abstract Map<String, Location> getWarps();
    public abstract boolean hasWarpPassword(String warp);
    public abstract boolean isWarpPassword(String warp, String password);
    public abstract String getWarpPassword(String warp);
    public abstract void setWarpPassword(String warp, String password);

    public abstract Set<ConquerPlayer> getInvited();
    public abstract void invite(ConquerPlayer player);
    public abstract void deinvite(ConquerPlayer player);

    public abstract boolean isPeaceful();
    public abstract void setPeaceful(boolean peaceful);

    public abstract boolean isOpen();
    public abstract void setOpen(boolean open);

    public abstract Relation getRelationTo(ConquerFaction faction);
    public abstract void setRelationBetween(ConquerFaction faction, Relation relation);

    public abstract ConquerClaim<?>[] getClaims();
    public abstract void claim(ConquerClaim<?> claim);
    public abstract void claim(Location location);
    public abstract void unclaim(ConquerClaim<?> claim);
    public abstract void unclaim(Location location);

    public abstract void disband();
}
