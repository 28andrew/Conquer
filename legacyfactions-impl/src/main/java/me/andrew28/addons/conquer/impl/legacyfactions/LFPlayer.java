package me.andrew28.addons.conquer.impl.legacyfactions;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.sender.AllPermissionsSender;
import net.redstoneore.legacyfactions.entity.FPlayer;
import net.redstoneore.legacyfactions.entity.FPlayerColl;
import net.redstoneore.legacyfactions.entity.FactionColl;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Andrew Tran
 */
public class LFPlayer extends ConquerPlayer  {
    private static Map<Object, LFPlayer> cache = new WeakHashMap<>();
    private LFPlugin plugin;
    private FactionColl factionColl;
    private FPlayer fPlayer;
    private OfflinePlayer offlinePlayer;

    private LFPlayer(LFPlugin plugin, FPlayer fPlayer) {
        this.plugin = plugin;
        this.factionColl = plugin.getFactionColl();
        this.fPlayer = fPlayer;
        this.offlinePlayer = fPlayer.getPlayer();
    }

    private LFPlayer(LFPlugin plugin, OfflinePlayer player) {
        this.plugin = plugin;
        this.factionColl = plugin.getFactionColl();
        this.fPlayer = FPlayerColl.get(player);
        this.offlinePlayer = player;
    }

    public static LFPlayer get(LFPlugin plugin, FPlayer mPlayer) {
        if (mPlayer == null) {
            return null;
        }
        if (!cache.containsKey(mPlayer)) {
            LFPlayer lfPlayer = new LFPlayer(plugin, mPlayer);
            cache.put(mPlayer, lfPlayer);
            return lfPlayer;
        }
        return cache.get(mPlayer);
    }

    public static LFPlayer get(LFPlugin plugin, OfflinePlayer player) {
        if (player == null) {
            return null;
        }
        if (!cache.containsKey(player)) {
            LFPlayer lfPlayer = new LFPlayer(plugin, player);
            cache.put(player, lfPlayer);
            return lfPlayer;
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
        return LFFaction.get(plugin, fPlayer.getFaction());
    }

    @Override
    public void setFaction(ConquerFaction faction) {
        fPlayer.setFaction(faction == null ? factionColl.getWilderness() : ((LFFaction) faction).getRawFaction());
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
