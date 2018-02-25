package me.andrew28.addons.conquer.impl.factionsone;

import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.FactionRelationEvent;
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
public class FOEventForwarder extends EventForwarder {
    private FOPlugin plugin;

    FOEventForwarder(FOPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        ConquerFactionCreateEvent forwardEvent =
                new ConquerFactionCreateEvent(FOPlayer.get(plugin, event.getFPlayer()), event.getFactionTag());
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionDisband(FactionDisbandEvent event) {
        ConquerFactionDisbandEvent forwardEvent =
                new ConquerFactionDisbandEvent(FOFaction.get(plugin, event.getFaction()),
                        FOPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(FPlayerJoinEvent event) {
        ConquerFactionJoinEvent forwardEvent =
                new ConquerFactionJoinEvent(FOFaction.get(plugin, event.getFaction()),
                        FOPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(FPlayerLeaveEvent event) {
        ConquerFactionLeaveEvent forwardEvent =
                new ConquerFactionLeaveEvent(FOFaction.get(plugin, event.getFaction()),
                        FOPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRelationChange(FactionRelationEvent event) {
        callEvent(new ConquerFactionRelationWishEvent(
                null,
                FOFaction.get(plugin, event.getFaction()),
                FOFaction.get(plugin, event.getTargetFaction()),
                plugin.translateRelation(event.getOldRelation()),
                plugin.translateRelation(event.getRelation())));
        callEvent(new ConquerFactionRelationEvent(
                FOFaction.get(plugin, event.getFaction()),
                FOFaction.get(plugin, event.getTargetFaction()),
                plugin.translateRelation(event.getOldRelation()),
                plugin.translateRelation(event.getRelation())));
    }

    @EventHandler
    public void onLandClaim(LandClaimEvent event) {
        ConquerLandClaimEvent forwardEvent =
                new ConquerLandClaimEvent(
                        FOClaim.get(plugin, event.getLocation()),
                        true,
                        FOFaction.get(plugin, event.getFaction()),
                        FOPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLandUnclaimEvent(LandUnclaimEvent event) {
        ConquerLandClaimEvent forwardEvent =
                new ConquerLandClaimEvent(
                        FOClaim.get(plugin, event.getLocation()),
                        false,
                        FOFaction.get(plugin, event.getFaction()),
                        FOPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPowerLossEvent(PowerLossEvent event) {
        ConquerPowerLossEvent forwardEvent =
                new ConquerPowerLossEvent(FOFaction.get(plugin, event.getFaction()),
                        FOPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUnclaimAll(LandUnclaimAllEvent event) {
        ConquerUnclaimAllEvent forwardEvent =
                new ConquerUnclaimAllEvent(FOFaction.get(plugin, event.getFaction()),
                        FOPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            // In some versions of FactionsOne, it is cancelled, some others, it's not
            if (event instanceof Cancellable) {
                ((Cancellable) event).setCancelled(true);
            }
        }
    }
}
