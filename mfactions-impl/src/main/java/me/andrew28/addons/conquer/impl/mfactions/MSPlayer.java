package me.andrew28.addons.conquer.impl.mfactions;

import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.WeakHashMap;

/**
 * @author Andrew Tran
 */
public class MSPlayer extends ConquerPlayer {
    private static WeakHashMap<Object, MSPlayer> cache = new WeakHashMap<>();
    private MSPlugin plugin;
    private FactionColl factionColl;
    private MPlayer mPlayer;
    private OfflinePlayer offlinePlayer;

    private MSPlayer(MSPlugin plugin, MPlayer mPlayer) {
        this.plugin = plugin;
        this.factionColl = plugin.getFactionColl();
        this.mPlayer = mPlayer;
        this.offlinePlayer = mPlayer.getPlayer();
    }

    private MSPlayer(MSPlugin plugin, OfflinePlayer player) {
        this.plugin = plugin;
        this.factionColl = plugin.getFactionColl();
        this.mPlayer = plugin.getmPlayerColl().get(player.getUniqueId());
        this.offlinePlayer = player;
    }

    public static MSPlayer get(MSPlugin plugin, MPlayer mPlayer) {
        if (mPlayer == null) {
            return null;
        }
        if (!cache.containsKey(mPlayer)) {
            MSPlayer msPlayer = new MSPlayer(plugin, mPlayer);
            cache.put(mPlayer, msPlayer);
            return msPlayer;
        }
        return cache.get(mPlayer);
    }

    public static MSPlayer get(MSPlugin plugin, OfflinePlayer player) {
        if (player == null) {
            return null;
        }
        if (!cache.containsKey(player)) {
            MSPlayer msPlayer = new MSPlayer(plugin, player);
            cache.put(player, msPlayer);
            return msPlayer;
        }
        return cache.get(player);
    }

    @Override
    public String getName() {
        return offlinePlayer.getName();
    }

    @Override
    public boolean hasFaction() {
        return mPlayer.getFaction().isNormal();
    }

    @Override
    public MSFaction getFaction() {
        return MSFaction.get(plugin, mPlayer.getFaction());
    }

    @Override
    public void setFaction(ConquerFaction faction) {
        mPlayer.setFaction(faction == null ? factionColl.getNone() : ((MSFaction) faction).getRawFaction());
    }

    @Override
    public boolean isAutoClaiming() {
        return mPlayer.getAutoClaimFaction() != null;
    }

    @Override
    public void setAutoClaiming(boolean autoClaiming) {
        mPlayer.setAutoClaimFaction(mPlayer.getFaction());
    }

    @Override
    public Date getLastActivity() {
        return new Date(mPlayer.getLastActivityMillis());
    }

    @Override
    public void setLastActivity(Date date) {
        mPlayer.setLastActivityMillis(date.getTime());
    }

    @Override
    public Role getRole() {
        return plugin.translateRole(mPlayer.getRole());
    }

    @Override
    public void setRole(Role role) {
        mPlayer.setRole(plugin.translateRole(role));
    }

    @Override
    public String getTitle() {
        return mPlayer.hasTitle() ? mPlayer.getTitle() : null;
    }

    @Override
    public void setTitle(String title) {
        mPlayer.setTitle(title);
    }

    @Override
    public void leaveFaction() {
        mPlayer.leave();
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public MPlayer getRawPlayer() {
        return mPlayer;
    }

    @Override
    public double getPower() {
        return mPlayer.getPower();
    }

    @Override
    public void setPower(double power) {
        mPlayer.setPower(power);
    }

    @Override
    public double getMaximumPower() {
        return mPlayer.getPowerMax();
    }

    @Override
    public double getPowerBoost() {
        return mPlayer.getPowerBoost();
    }

    @Override
    public void setPowerBoost(double powerBoost) {
        mPlayer.setPowerBoost(powerBoost);
    }
}
