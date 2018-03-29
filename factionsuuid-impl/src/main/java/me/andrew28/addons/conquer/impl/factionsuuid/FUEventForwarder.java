package me.andrew28.addons.conquer.impl.factionsuuid;

import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.FactionRelationEvent;
import com.massivecraft.factions.event.FactionRelationWishEvent;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import com.massivecraft.factions.event.PowerLossEvent;
import me.andrew28.addons.conquer.api.EventForwarder;
import me.andrew28.addons.conquer.api.events.ConquerFactionCreateEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionDisbandEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionJoinEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionLeaveEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationWishEvent;
import me.andrew28.addons.conquer.api.events.ConquerLandClaimEvent;
import me.andrew28.addons.conquer.api.events.ConquerPowerLossEvent;
import me.andrew28.addons.conquer.api.events.ConquerUnclaimAllEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;

/**
 * @author Andrew Tran
 */
public class FUEventForwarder extends EventForwarder {
    private FUPlugin plugin;

    FUEventForwarder(FUPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        ConquerFactionCreateEvent forwardEvent =
                new ConquerFactionCreateEvent(FUPlayer.get(plugin, event.getFPlayer()), event.getFactionTag());
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            if (event instanceof Cancellable) {
                ((Cancellable) event).setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFactionDisband(FactionDisbandEvent event) {
        ConquerFactionDisbandEvent forwardEvent =
                new ConquerFactionDisbandEvent(FUFaction.get(plugin, event.getFaction()),
                        FUPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(FPlayerJoinEvent event) {
        ConquerFactionJoinEvent forwardEvent =
                new ConquerFactionJoinEvent(FUFaction.get(plugin, event.getFaction()),
                        FUPlayer.get(plugin, event.getfPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(FPlayerLeaveEvent event) {
        ConquerFactionLeaveEvent forwardEvent =
                new ConquerFactionLeaveEvent(FUFaction.get(plugin, event.getFaction()),
                        FUPlayer.get(plugin, event.getfPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRelationChange(FactionRelationEvent event) {
        ConquerFactionRelationEvent forwardEvent =
                new ConquerFactionRelationEvent(
                        FUFaction.get(plugin, event.getFaction()),
                        FUFaction.get(plugin, event.getTargetFaction()),
                        plugin.translate(event.getOldRelation()),
                        plugin.translate(event.getRelation()));
        callEvent(forwardEvent);
    }

    @EventHandler
    public void onRelationWish(FactionRelationWishEvent event) {
        ConquerFactionRelationWishEvent forwardEvent =
                new ConquerFactionRelationWishEvent(
                        FUPlayer.get(plugin, event.getfPlayer()),
                        FUFaction.get(plugin, event.getFaction()),
                        FUFaction.get(plugin, event.getTargetFaction()),
                        plugin.translate(event.getCurrentRelation()),
                        plugin.translate(event.getTargetRelation()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLandClaim(LandClaimEvent event) {
        ConquerLandClaimEvent forwardEvent =
                new ConquerLandClaimEvent(
                        FUClaim.get(plugin, event.getLocation()),
                        true,
                        FUFaction.get(plugin, event.getFaction()),
                        FUPlayer.get(plugin, event.getfPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLandUnclaimEvent(LandUnclaimEvent event) {
        ConquerLandClaimEvent forwardEvent =
                new ConquerLandClaimEvent(
                        FUClaim.get(plugin, event.getLocation()),
                        false,
                        FUFaction.get(plugin, event.getFaction()),
                        FUPlayer.get(plugin, event.getfPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPowerLossEvent(PowerLossEvent event) {
        ConquerPowerLossEvent forwardEvent =
                new ConquerPowerLossEvent(FUFaction.get(plugin, event.getFaction()),
                        FUPlayer.get(plugin, event.getfPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUnclaimAll(LandUnclaimAllEvent event) {
        ConquerUnclaimAllEvent forwardEvent =
                new ConquerUnclaimAllEvent(FUFaction.get(plugin, event.getFaction()),
                        FUPlayer.get(plugin, event.getfPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}
