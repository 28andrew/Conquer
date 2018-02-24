package me.andrew28.addons.conquer.impl.factionsuuid;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Factions;
import me.andrew28.addons.conquer.api.sender.AllPermissionsSender;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * @author Andrew Tran
 */
public class FUPlayer extends ConquerPlayer {
    private static WeakHashMap<Object, FUPlayer> cache = new WeakHashMap<>();
    private FUPlugin plugin;
    private Factions factions;
    private FPlayer fPlayer;
    private OfflinePlayer offlinePlayer;

    private FUPlayer(FUPlugin plugin, FPlayer fPlayer) {
        this.plugin = plugin;
        this.factions = plugin.getFactions();
        this.fPlayer = fPlayer;
        this.offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(fPlayer.getId()));
    }

    private FUPlayer(FUPlugin plugin, OfflinePlayer player) {
        this.plugin = plugin;
        this.offlinePlayer = player;
        if (player instanceof Player) {
            this.fPlayer = plugin.getfPlayers().getByPlayer((Player) player);
        } else {
            this.fPlayer = plugin.getfPlayers().getByOfflinePlayer(player);
        }
    }

    public static FUPlayer get(FUPlugin plugin, FPlayer fPlayer) {
        if (fPlayer == null) {
            return null;
        }
        if (!cache.containsKey(fPlayer)) {
            FUPlayer fuPlayer = new FUPlayer(plugin, fPlayer);
            cache.put(fPlayer, fuPlayer);
            return fuPlayer;
        }
        return cache.get(fPlayer);
    }

    public static FUPlayer get(FUPlugin plugin, OfflinePlayer player) {
        if (player == null) {
            return null;
        }
        if (!cache.containsKey(player)) {
            FUPlayer fuPlayer = new FUPlayer(plugin, player);
            cache.put(player, fuPlayer);
            return fuPlayer;
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
        return FUFaction.get(plugin, fPlayer.getFaction());
    }

    @Override
    public void setFaction(ConquerFaction faction) {
        fPlayer.setFaction(faction == null ? factions.getWilderness() : ((FUFaction) faction).getRawFaction());
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
        fPlayer.setLastFrostwalkerMessage();
    }

    @Override
    public Role getRole() {
        return plugin.translate(fPlayer.getRole());
    }

    @Override
    public void setRole(Role role) {
        fPlayer.setRole(plugin.translate(role));
    }

    @Override
    public String getTitle() {
        return fPlayer.getTitle();
    }

    @Override
    public void setTitle(String title) {
        fPlayer.setTitle(new AllPermissionsSender(), title);
    }

    @Override
    public void leaveFaction() {
        fPlayer.leave(false);
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public FPlayer getRawPlayer() {
        return fPlayer;
    }

    @Override
    public double getPower() {
        return fPlayer.getPower();
    }

    @Override
    public void setPower(double power) {
        fPlayer.alterPower(power - getPower());
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
}
