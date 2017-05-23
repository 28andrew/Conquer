package me.andrew28.addons.conquer.api;

import ch.njol.yggdrasil.Fields;
import me.andrew28.addons.core.Addon;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.StreamCorruptedException;
import java.util.Date;

/**
 * @author Andrew Tran
 */
public interface FactionsPlugin {
    boolean canBeUsed();
    void initialize();
    void initializeSkriptComponents(Addon addon);
    Listener getEventWrapperListener();

    boolean hasBeenInitialized();

    ConquerFaction[] getAll();
    ConquerFaction getAtLocation(Location location);
    ConquerFaction getByName(String name);
    ConquerFaction getFaction(Claim claim);

    ConquerPlayer getConquerPlayer(Player player);

    //void setFaction(Claim claim, Faction faction);
    //void resetFaction(Claim claim);
    //String getDescription(Faction faction);
    //Location getHome(Faction faction);
    //OfflinePlayer[] getPlayers(Faction faction);
    //void addPlayer(Faction faction, OfflinePlayer player);
    //void removePlayer(Faction faction, OfflinePlayer player);

    //void setHome(Faction faction, Location home);
    //void setDescription(Faction faction, String description);
    //String getMotd(Faction faction);
    //void setMotd(Faction faction, String motd);
    //String getIdentifier(Faction faction);
    //void setIdentifier(Faction faction, String identifier);
    //Date getCreationDate(Faction faction);
    //void setCreationDate(Faction faction, Date date);
    //Double getPowerBoost(Faction faction);
    //void setPowerBoost(Faction faction, Double powerBoost);
    //Double getPower(Faction faction);
    //void setPower(Faction faction, Double power);
    //Double getMaximumPower(Faction faction);
    //OfflinePlayer getLeader(Faction faction);
    //void setLeader(Faction faction, OfflinePlayer leader);
    //void sendMessage(String[] messages, Faction faction);

    Claim getClaim(Location location);
    Claim[] getClaims(ConquerFaction faction);
    void claim(ConquerFaction faction, Location location);
    void removeClaim(Location location);
    //boolean isSafeZone(Claim claim);
    //boolean isWarZone(Claim claim);

    Claim deserializeClaim(Fields f) throws StreamCorruptedException;
    ConquerFaction deserializeFaction(Fields f) throws StreamCorruptedException;

    //void setFaction(Player player, ConquerFaction faction);
    //void resetFaction(Player player);
    //Boolean playerIsAutoClaiming(Player player);
    //Date getLastActivity(Player player);
    //void setLastActivity(Player player, Date date);
    //Role getRole(Player player);
    //void setRole(Player player, Role role);
    //String getTitle(Player player);
    //void setTitle(Player player, String title);
    //void resetTitle(Player player);
    //Double getPowerBoost(Player player);
    //void setPowerBoost(Player player, Double powerBoost);
    //void resetPowerBoost(Player player);
    //Double getPower(Player player);
    //void setPower(Player player, Double power);
    //void resetPower(Player player);
    //Double getMaximumPower(Player player);
    //Double getMinimumPower(Player player);
    //boolean getAutoMapMode(Player player);
    //void setAutoMapMode(Player player, Boolean mode);
}
