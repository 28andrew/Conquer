package me.andrew28.addons.conquer.impl.savagefactions;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Factions;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.sender.AllPermissionsSender;
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
public class SFPlayer extends ConquerPlayer {
    private static Map<Object, SFPlayer> cache = new WeakHashMap<>();
    private SFPlugin plugin;
    private Factions factions;
    private FPlayer fPlayer;
    private OfflinePlayer offlinePlayer;

    private SFPlayer(SFPlugin plugin, FPlayer fPlayer) {
        this.plugin = plugin;
        this.factions = plugin.getFactions();
        this.fPlayer = fPlayer;
        this.offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(fPlayer.getId()));
    }

    private SFPlayer(SFPlugin plugin, OfflinePlayer player) {
        this.plugin = plugin;
        this.offlinePlayer = player;
        if (player instanceof Player) {
            this.fPlayer = plugin.getFPlayers().getByPlayer((Player) player);
        } else {
            this.fPlayer = plugin.getFPlayers().getByOfflinePlayer(player);
        }
    }

    public static SFPlayer get(SFPlugin plugin, FPlayer fPlayer) {
        if (fPlayer == null) {
            return null;
        }
        if (!cache.containsKey(fPlayer)) {
            SFPlayer fuPlayer = new SFPlayer(plugin, fPlayer);
            cache.put(fPlayer, fuPlayer);
            return fuPlayer;
        }
        return cache.get(fPlayer);
    }

    public static SFPlayer get(SFPlugin plugin, OfflinePlayer player) {
        if (player == null) {
            return null;
        }
        if (!cache.containsKey(player)) {
            SFPlayer fuPlayer = new SFPlayer(plugin, player);
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
        return SFFaction.get(plugin, fPlayer.getFaction());
    }

    @Override
    public void setFaction(ConquerFaction faction) {
        fPlayer.setFaction(faction == null ? factions.getWilderness() : ((SFFaction) faction).getRawFaction());
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

    public FPlayer getRawPlayer() {
        return fPlayer;
    }
}
