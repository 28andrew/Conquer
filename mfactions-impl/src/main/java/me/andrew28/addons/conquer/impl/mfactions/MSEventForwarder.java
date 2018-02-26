package me.andrew28.addons.conquer.impl.mfactions;

import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsPowerChange;
import com.massivecraft.factions.event.EventFactionsRelationChange;
import com.massivecraft.massivecore.ps.PS;
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
import me.andrew28.addons.conquer.api.events.ConquerUnclaimAllEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import java.util.Map;
import java.util.Set;

/**
 * @author Andrew Tran
 */
class MSEventForwarder extends EventForwarder {
    private MSPlugin plugin;

    MSEventForwarder(MSPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFactionCreate(EventFactionsCreate event) {
        ConquerFactionCreateEvent forwardEvent =
                new ConquerFactionCreateEvent(MSPlayer.get(plugin, event.getMPlayer()), event.getFactionName());
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionDisband(EventFactionsDisband event) {
        ConquerFactionDisbandEvent forwardEvent =
                new ConquerFactionDisbandEvent(MSFaction.get(plugin, event.getFaction()),
                        MSPlayer.get(plugin, event.getMPlayer()));
        callEvent(forwardEvent);
        if (forwardEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMembershipChange(EventFactionsMembershipChange event) {
        ConquerFaction faction = MSFaction.get(plugin, event.getNewFaction());
        ConquerPlayer player = MSPlayer.get(plugin, event.getMPlayer());
        Event forwardEvent;
        switch (event.getReason()) {
            case JOIN:
            case CREATE:
                forwardEvent = new ConquerFactionJoinEvent(faction, player);
                break;
            case LEAVE:
            case KICK:
            case DISBAND:
                forwardEvent = new ConquerFactionLeaveEvent(faction, player);
                break;
            default:
                return;
        }
        callEvent(forwardEvent);
        if (((Cancellable) forwardEvent).isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRelationChange(EventFactionsRelationChange event) {
        ConquerPlayer player = MSPlayer.get(plugin, event.getMPlayer());
        ConquerFaction faction = MSFaction.get(plugin, event.getFaction());
        ConquerFaction otherFaction = MSFaction.get(plugin, event.getOtherFaction());
        Relation oldRelation = plugin.translateRelation(event.getFaction().getRelationTo(event.getOtherFaction()));
        Relation newRelation = plugin.translateRelation(event.getNewRelation());

        ConquerFactionRelationWishEvent forwardWishEvent =
                new ConquerFactionRelationWishEvent(player, faction, otherFaction, oldRelation, newRelation);
        callEvent(forwardWishEvent);
        if (forwardWishEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        ConquerFactionRelationEvent forwardEvent =
                new ConquerFactionRelationEvent(faction, otherFaction, oldRelation, newRelation);
        callEvent(forwardEvent);
    }

    @EventHandler
    public void onClaimChange(EventFactionsChunksChange event) {
        ConquerFaction faction = MSFaction.get(plugin, event.getNewFaction());
        ConquerPlayer player = MSPlayer.get(plugin, event.getMPlayer());

        Set<PS> chunks = event.getChunks();
        Map<PS, EventFactionsChunkChangeType> chunkTypeMap = event.getChunkType();

        // Check if it's an unclaim all
        if (chunks.size() == faction.getClaims().length &&
                chunkTypeMap.values()
                        .stream()
                        .allMatch(type -> type == EventFactionsChunkChangeType.SELL)) {
            ConquerUnclaimAllEvent forwardEvent =
                new ConquerUnclaimAllEvent(faction, player);
            callEvent(forwardEvent);
            if (forwardEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }

        for (PS chunk : chunks) {
            EventFactionsChunkChangeType type = chunkTypeMap.get(chunk);

            ConquerClaim claim = MSClaim.get(plugin, chunk);

            Event forwardEvent;
            switch (type) {
                case NONE:
                    continue;
                case BUY:
                    forwardEvent = new ConquerLandClaimEvent(claim, true, faction, player);
                    break;
                case CONQUER:
                case PILLAGE:
                    forwardEvent = new ConquerLandClaimEvent(claim, false, faction, player);
                    break;
                default:
                    return;
            }

            callEvent(forwardEvent);
            if (((Cancellable) forwardEvent).isCancelled()) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPowerChange(EventFactionsPowerChange event) {
        ConquerPlayer player = MSPlayer.get(plugin, event.getMPlayer());
        double oldPower = player.getPower();
        double newPower = event.getNewPower();
        if (newPower < oldPower) {
            ConquerPowerLossEvent forwardEvent =
                    new ConquerPowerLossEvent(player.getFaction(), player);
            callEvent(forwardEvent);
            if (forwardEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }
}
