package me.andrew28.addons.conquer.impl.legacyfactions;

import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.EventForwarder;
import me.andrew28.addons.conquer.api.Relation;
import me.andrew28.addons.conquer.api.events.ConquerFactionCreateEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionDisbandEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionJoinEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionLeaveEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationWishEvent;
import me.andrew28.addons.conquer.api.events.ConquerLandClaimEvent;
import me.andrew28.addons.conquer.api.events.ConquerPowerLossEvent;
import net.redstoneore.legacyfactions.FLocation;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.event.EventFactionsChange;
import net.redstoneore.legacyfactions.event.EventFactionsCreate;
import net.redstoneore.legacyfactions.event.EventFactionsDisband;
import net.redstoneore.legacyfactions.event.EventFactionsLandChange;
import net.redstoneore.legacyfactions.event.EventFactionsPowerLoss;
import net.redstoneore.legacyfactions.event.EventFactionsRelation;
import net.redstoneore.legacyfactions.event.EventFactionsRelationChange;
import net.redstoneore.legacyfactions.locality.Locality;
import org.bukkit.Chunk;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Andrew Tran
 */
class LFEventForwarder extends EventForwarder {
    private LFPlugin plugin;

    LFEventForwarder(LFPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFactionCreate(EventFactionsCreate event) {
        ConquerFactionCreateEvent forwardEvent =
                new ConquerFactionCreateEvent(LFPlayer.get(plugin, event.getFPlayer()), event.getFactionTag());
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionDisband(EventFactionsDisband event) {
        ConquerFactionDisbandEvent forwardEvent =
                new ConquerFactionDisbandEvent(LFFaction.get(plugin, event.getFaction()),
                        LFPlayer.get(plugin, event.getFPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionsChange(EventFactionsChange event) {
        Event forwardEvent;
        if (event.getFactionNew().isWilderness()) {
            forwardEvent = new ConquerFactionLeaveEvent(LFFaction.get(plugin, event.getFactionOld()),
                    LFPlayer.get(plugin, event.getFPlayer()));
        } else {
            forwardEvent = new ConquerFactionJoinEvent(LFFaction.get(plugin, event.getFactionNew()),
                    LFPlayer.get(plugin, event.getFPlayer()));
        }
        callEvent(forwardEvent);
        if (((Cancellable) forwardEvent).isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRelationChange(EventFactionsRelation event) {
        Relation oldRelation = plugin.translate(event.getOldRelation());
        Relation newRelation = plugin.translate(event.getRelation());
        ConquerFaction faction = LFFaction.get(plugin, event.getFaction());
        ConquerFaction targetFaction = LFFaction.get(plugin, event.getTargetFaction());
        ConquerFactionRelationEvent forwardEvent =
                new ConquerFactionRelationEvent(faction, targetFaction, oldRelation, newRelation);
        callEvent(forwardEvent);
    }

    @EventHandler
    public void onRelationWish(EventFactionsRelationChange event) {
        ConquerPlayer caller = LFPlayer.get(plugin, event.getfPlayer());
        Relation oldRelation = plugin.translate(event.getCurrentRelation());
        Relation newRelation = plugin.translate(event.getTargetRelation());
        ConquerFaction faction = LFFaction.get(plugin, event.getFaction());
        ConquerFaction targetFaction = LFFaction.get(plugin, event.getTargetFaction());
        ConquerFactionRelationWishEvent forwardEvent =
                new ConquerFactionRelationWishEvent(caller, faction, targetFaction, oldRelation, newRelation);
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLandChange(EventFactionsLandChange event) {
        boolean claiming = event.getCause() == EventFactionsLandChange.LandChangeCause.Claim;
        ConquerPlayer player = LFPlayer.get(plugin, event.getFPlayer());

        Iterator<Map.Entry<FLocation, Faction>> iterator = event.getTransactions().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<FLocation, Faction> entry = iterator.next();

            ConquerClaim<Chunk> claim = LFClaim.get(plugin, entry.getKey());
            ConquerFaction faction = LFFaction.get(plugin, entry.getValue());

            ConquerLandClaimEvent forwardEvent = new ConquerLandClaimEvent(claim, claiming, faction, player);
            callEvent(forwardEvent);
            if (forwardEvent.isCancelled()) {
                iterator.remove();
            }
        }
    }

    @EventHandler
    public void onPowerLoss(EventFactionsPowerLoss event) {
        ConquerPlayer player = LFPlayer.get(plugin, event.getfPlayer());
        ConquerFaction faction = LFFaction.get(plugin, event.getFaction());
        ConquerPowerLossEvent forwardEvent = new ConquerPowerLossEvent(faction, player);
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}
