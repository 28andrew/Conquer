package me.andrew28.addons.conquer.api;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A faction which is usually a group of multiple players
 * @author Andrew Tran
 */
public abstract class ConquerFaction implements PowerHolder, SerializableToFields {
    /**
     * Gets the unique id of this faction
     * @return the unique id of this faction
     */
    public abstract String getId();

    /**
     * Sets the unique id of this faction
     * @param id the unique id to set this faction to
     */
    public abstract void setId(String id);

    /**
     * Gets the name (aka tag) of this faction
     * @return the name (aka tag) of this faction
     */
    public abstract String getName();

    /**
     * Sets the name (aka tag) of this faction
     * @param name the name (aka tag) to change to
     */
    public abstract void setName(String name);

    /**
     * Gets the description of this faction
     * @return the description of this faction
     */
    public abstract String getDescription();

    /**
     * Sets the description of this faction
     * @param description the description to change to
     */
    public abstract void setDescription(String description);

    /**
     * Gets the message of the day of this faction
     * @return the message of the day of this faction
     */
    public abstract String getMotd();

    /**
     * Sets the message of the day of this faction
     * @param motd the message of the day to change to
     */
    public abstract void setMotd(String motd);

    /**
     * Gets when this faction was created
     * @return when this faction was created
     */
    public abstract Date getCreationDate();

    /**
     * Sets when this faction was supposedly created
     * @param date the date to set the creation date of the faction to
     */
    public abstract void setCreationDate(Date date);

    /**
     * Gets the power boost (power added on top of normal power) of this faction
     * @return the power boost of this faction
     */
    public abstract double getPowerBoost();

    /**
     * Sets the power boost (power to be added on top of normal power) of this faction
     * @param powerBoost the power boost to give this faction
     */
    public abstract void setPowerBoost(double powerBoost);

    /**
     * Gets the leader (admin) of this faction
     * @return the leader of this faction
     */
    public abstract ConquerPlayer getLeader();

    /**
     * Sets the leader (admin) of this faction
     * @param leader the leader of this faction
     */
    public abstract void setLeader(ConquerPlayer leader);

    /**
     * Gets the home location of this faction
     * @return the home location of this faction
     */
    public abstract Location getHome();

    /**
     * Sets the home location of this faction
     * @param home the home location to change to
     */
    public abstract void setHome(Location home);

    /**
     * Gets the members of this faction
     * @return the members of this faction
     */
    public abstract ConquerPlayer[] getMembers();

    /**
     * Adds a member to this faction
     * @param member the member to add to this faction
     */
    public abstract void addMember(ConquerPlayer member);

    /**
     * Removes a member from this faction
     * @param member the member to remove from this faction
     */
    public abstract void removeMember(ConquerPlayer member);

    /**
     * A {@link CommandSender} that should at least actually implement {@link CommandSender#sendMessage(String)}.
     * For the other methods, do nothing, return null/false, or throw an {@link UnsupportedOperationException}.
     * Implementing {@link me.andrew28.addons.conquer.api.sender.MessageOnlySender} suffices.
     * @return a command sender that sends messages to members of this faction
     */
    public abstract CommandSender getSender();

    /**
     * Gets a map of the warps of this faction, which should be mutable. ({@link java.util.AbstractMap})
     * May return null if the implementation does not support warps.
     * @return a mutable map of the warps of this faction
     */
    public abstract Map<String, Location> getWarps();

    /**
     * Gets whether a warp has a password
     * @param warp the name of the warp
     * @return if the warp exists and it has a password
     */
    public abstract boolean hasWarpPassword(String warp);

    /**
     * Gets whether a password is a valid for a warp
     * @param warp the name of the warp
     * @param password the password to check
     * @return if the warp exists and the password is the correct one
     */
    public abstract boolean isWarpPassword(String warp, String password);

    /**
     * Gets the password of a warp. Some implementations may always return null.
     * @param warp the name of the warp
     * @return the password of the given warp; {@code null} if the warp doesn't exist
     */
    public abstract String getWarpPassword(String warp);

    /**
     * Sets the password of a warp
     * @param warp the name of the warp
     * @param password the password to set for the warp
     */
    public abstract void setWarpPassword(String warp, String password);

    /**
     * Gets the invited players to this faction
     * @return the currently invited players to this faction
     */
    public abstract Set<ConquerPlayer> getInvited();

    /**
     * Invites a player to this faction
     * @param player the player to invite
     */
    public abstract void invite(ConquerPlayer player);

    /**
     * Deinvites a player from this faction
     * @param player deinvites a player from this faction
     */
    public abstract void deinvite(ConquerPlayer player);

    /**
     * Gets whether this faction is peaceful
     * @return whether this faction is peaceful
     */
    public abstract boolean isPeaceful();

    /**
     * Sets whether this faction is peaceful
     * @param peaceful whether this faction should be peaceful
     */
    public abstract void setPeaceful(boolean peaceful);

    /**
     * Gets whether this faction is open
     * @return whether this faction is open
     */
    public abstract boolean isOpen();

    /**
     * Sets whether this faction is open
     * @param open whether this faction should be open
     */
    public abstract void setOpen(boolean open);

    /**
     * Gets the relation from this faction to another faction
     * @param faction the other faction
     * @return the relation between this faction and another faction
     */
    public abstract Relation getRelationTo(ConquerFaction faction);

    /**
     * Sets the relation between this faction and another faction. Invoking
     * {@link #setRelationBetween(ConquerFaction, Relation)} on the other faction with this faction
     * as a param and the same relation, should have the same result.
     * @param faction the other faction
     * @param relation the new relation
     */
    public abstract void setRelationBetween(ConquerFaction faction, Relation relation);

    /**
     * Gets the claims of this faction
     * @return the claims of this faction
     */
    public abstract ConquerClaim<?>[] getClaims();

    /**
     * Claims a claim for this faction
     * @param claim the claim to claim
     */
    public abstract void claim(ConquerClaim<?> claim);

    /**
     * Claims the claim at a location for this faction
     * @param location the location where the claim to claim is located
     */
    public abstract void claim(Location location);

    /**
     * Unclaims a claim for this faction
     * @param claim the claim to unclaim
     */
    public abstract void unclaim(ConquerClaim<?> claim);

    /**
     * Unclaims the claim at a location for this faction
     * @param location the location of the claim to unclaim
     */
    public abstract void unclaim(Location location);

    /**
     * Disbands (deletes) this faction
     */
    public abstract void disband();

    /**
     * Gets the amount of TNT this faction has stored in their virtual vault
     * @return the amount of TNT this faction has
     */
    public int getTNT() {
        return 0;
    }

    /**
     * Adds an amount of TNT to this faction's virtual vault
     * @param amount the amount of TNT to add
     */
    public void addTNT(int amount) {

    }

    /**
     * Removes an amount of TNT from this faction's virtual vault
     * @param amount the amount of TNT to remove
     */
    public void removeTNT(int amount) {

    }

    /**
     * Sets the amount of TNT that this faction has in their virtual vault
     * @param amount the amount of TNT they should have
     */
    public void setTNT(int amount) {
        int currentAmount = getTNT();
        if (amount == currentAmount) {
            return;
        }
        if (amount > currentAmount) {
            addTNT(amount - currentAmount);
        } else {
            removeTNT(currentAmount - amount);
        }
    }

    /**
     * Gets the checkpoint location (a warp in the wilderness) of this faction
     * @return the checkpoint location of this faction
     */
    public Location getCheckpoint() {
        return null;
    }

    /**
     * Sets the checkpoint location (a warp in the wilderness) of this faction
     * @param location the checkpoint location for this faction
     */
    public void setCheckpoint(Location location) {

    }

    /**
     * The mutable list of rules for this faction. If unsupported, return null. {@link java.util.AbstractList}
     * @return a mutable list of rules for this faction
     */
    public List<String> getRules() {
        return null;
    }
}
