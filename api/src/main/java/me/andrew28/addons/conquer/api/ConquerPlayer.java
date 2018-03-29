package me.andrew28.addons.conquer.api;

import org.bukkit.OfflinePlayer;

import java.util.Date;

/**
 * @author Andrew Tran
 */
public abstract class ConquerPlayer implements PowerHolder {

    public abstract String getName();

    public abstract boolean hasFaction();
    public abstract ConquerFaction getFaction();
    public abstract void setFaction(ConquerFaction faction);

    public abstract boolean isAutoClaiming();
    public abstract void setAutoClaiming(boolean autoClaiming);

    public abstract Date getLastActivity();
    public abstract void setLastActivity(Date date);

    public abstract Role getRole();
    public abstract void setRole(Role role);

    public abstract String getTitle();
    public abstract void setTitle(String title);

    public abstract void leaveFaction();

    public abstract OfflinePlayer getOfflinePlayer();

    public enum Role {
        NORMAL, COADMIN, ADMIN, MODERATOR, RECRUIT, OTHER
    }
}
