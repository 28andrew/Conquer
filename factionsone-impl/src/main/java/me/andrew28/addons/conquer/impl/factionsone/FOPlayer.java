package me.andrew28.addons.conquer.impl.factionsone;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Factions;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * @author Andrew Tran
 */
public class FOPlayer extends ConquerPlayer {
    private static Map<Object, FOPlayer> cache = new WeakHashMap<>();
    private FOPlugin plugin;
    private Factions factions;
    private FPlayer fPlayer;
    private OfflinePlayer offlinePlayer;

    private FOPlayer(FOPlugin plugin, FPlayer fPlayer) {
        this.plugin = plugin;
        this.factions = plugin.getFactions();
        this.fPlayer = fPlayer;
        this.offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(fPlayer.getId()));
    }

    private FOPlayer(FOPlugin plugin, OfflinePlayer player) {
        this.plugin = plugin;
        this.offlinePlayer = player;
        if (player instanceof Player) {
            this.fPlayer = plugin.getfPlayers().get((Player) player);
        } else {
            this.fPlayer = plugin.getfPlayers().get(player);
        }
    }

    public static FOPlayer get(FOPlugin plugin, FPlayer fPlayer) {
        if (fPlayer == null) {
            return null;
        }
        if (!cache.containsKey(fPlayer)) {
            FOPlayer foPlayer = new FOPlayer(plugin, fPlayer);
            cache.put(fPlayer, foPlayer);
            return foPlayer;
        }
        return cache.get(fPlayer);
    }

    public static FOPlayer get(FOPlugin plugin, OfflinePlayer player) {
        if (player == null) {
            return null;
        }
        if (!cache.containsKey(player)) {
            FOPlayer foPlayer = new FOPlayer(plugin, player);
            cache.put(player, foPlayer);
            return foPlayer;
        }
        return cache.get(player);
    }

    @Override
    public String getName() {
        return offlinePlayer.getName();
    }

    @Override
    public boolean hasFaction() {
        return fPlayer.getFaction().isNormal();
    }

    @Override
    public ConquerFaction getFaction() {
        return FOFaction.get(plugin, fPlayer.getFaction());
    }

    @Override
    public void setFaction(ConquerFaction faction) {
        fPlayer.setFaction(faction == null ? factions.get("0") : ((FOFaction) faction).getRawFaction());
    }

    @Override
    public boolean isAutoClaiming() {
        return fPlayer.getAutoClaimFor() != null;
    }

    @Override
    public void setAutoClaiming(boolean autoClaiming) {
        fPlayer.setAutoClaimFor(fPlayer.getFaction());
    }

    @Override
    public Date getLastActivity() {
        return new Date(fPlayer.getLastLoginTime());
    }

    @Override
    public void setLastActivity(Date date) {
        fPlayer.setLastLoginTime(date.getTime());
    }

    @Override
    public Role getRole() {
        return plugin.translateRole(fPlayer.getRole());
    }

    @Override
    public void setRole(Role role) {
        fPlayer.setRole(plugin.translateRole(role));
    }

    @Override
    public String getTitle() {
        return fPlayer.getTitle();
    }

    @Override
    public void setTitle(String title) {
        fPlayer.setTitle(title);
    }

    @Override
    public void leaveFaction() {
        fPlayer.leave(false);
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    @Override
    public double getPower() {
        return fPlayer.getPower();
    }

    @Override
    public void setPower(double power) {
        /* UNSUPPORTED */
    }

    @Override
    public double getMaximumPower() {
        return fPlayer.getPowerMax();
    }

    @Override
    public double getPowerBoost() {
        return fPlayer.getPowerBoost();
    }

    @Override
    public void setPowerBoost(double powerBoost) {
        fPlayer.setPowerBoost(powerBoost);
    }

    public FPlayer getRawPlayer() {
        return fPlayer;
    }
}
