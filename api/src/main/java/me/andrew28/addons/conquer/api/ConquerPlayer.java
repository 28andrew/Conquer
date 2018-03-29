package me.andrew28.addons.conquer.api;

import org.bukkit.OfflinePlayer;

import java.util.Date;

/**
 * An offline or online player that may or may not be in a {@link ConquerFaction}
 * @author Andrew Tran
 */
public abstract class ConquerPlayer implements PowerHolder {
    /**
     * Gets the name of this player
     * @return the name of this player
     */
    public abstract String getName();

    /**
     * Gets whether this player has a faction
     * @return whether this player has a faction
     */
    public abstract boolean hasFaction();

    /**
     * Gets the faction of this player.
     * @return the faction of this player, null if {@link #hasFaction()} is {@code false}
     */
    public abstract ConquerFaction getFaction();

    /**
     * Sets the faction of this player
     * @param faction the faction this player should be in
     */
    public abstract void setFaction(ConquerFaction faction);

    /**
     * Gets whether this player is currently auto-claiming
     * @return whether this player is currently auto-claiming
     */
    public abstract boolean isAutoClaiming();

    /**
     * Sets whether this player is currently auto-claiming
     * @param autoClaiming whether this player should be currently auto-claiming
     */
    public abstract void setAutoClaiming(boolean autoClaiming);

    /**
     * Gets when this player was last active
     * @return when this player was last active
     */
    public abstract Date getLastActivity();

    /**
     * Sets when this player was supposedly last active
     * @param date when this player was supposedly last active
     */
    public abstract void setLastActivity(Date date);

    /**
     * Gets the role of this player
     * @return the role of this player
     */
    public abstract Role getRole();

    /**
     * Sets the role of this player
     * @param role the new role of this player
     */
    public abstract void setRole(Role role);

    /**
     * Gets the title of this player
     * @return the title of this player
     */
    public abstract String getTitle();

    /**
     * Sets the title of this player
     * @param title the new title of this player
     */
    public abstract void setTitle(String title);

    /**
     * Makes this player leave their faction
     */
    public abstract void leaveFaction();

    /**
     * Gets the {@link OfflinePlayer} which represents this player
     * @return the {@link OfflinePlayer} which represents this player
     */
    public abstract OfflinePlayer getOfflinePlayer();

    /**
     * The role an {@link ConquerPlayer} may have in their faction
     * @see #getRole()
     * @see #setRole(Role)
     */
    public enum Role {
        /**
         * Essentially, no role, a normal/basic member
         */
        NORMAL,
        /**
         * The co-leader or person that assists the admin/leader ({@link #ADMIN})
         */
        COADMIN,
        /**
         * The leader or owner
         */
        ADMIN,
        /**
         * A member which helps moderate
         */
        MODERATOR,
        /**
         * A member that has been recently recruited
         */
        RECRUIT,
        /**
         * Other which may be multiple roles depending on the implementation
         */
        OTHER
    }
}
