package me.andrew28.addons.conquer.api;

import org.bukkit.entity.Player;

import java.util.Date;

/**
 * @author Andrew Tran
 */
public abstract class ConquerPlayer implements PowerHolder, PowerChangeable{
    private Player player;

    public ConquerPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract boolean hasFaction();
    public abstract ConquerFaction getFaction();
    public abstract void setFaction(ConquerFaction faction);
    public abstract void resetFaction();
    
    public abstract boolean isAutoClaiming();
    public abstract void setAutoClaiming(Boolean autoClaiming);
    
    public abstract Date getLastActivity();
    public abstract void setLastActivity(Date date);
    
    public abstract Role getRole();
    public abstract void setRole(Role role);
    
    public abstract String getTitle();
    public abstract void setTitle(String title);
    public abstract void resetTitle();
    public abstract double getMinimumPower();

    public abstract boolean getAutomaticMapUpdateMode();
    public abstract void setAutomaticMapUpdateMode(Boolean automaticMapUpdateMode);

}
