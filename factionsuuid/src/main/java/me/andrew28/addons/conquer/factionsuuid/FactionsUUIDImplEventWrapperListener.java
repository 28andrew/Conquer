package me.andrew28.addons.conquer.factionsuuid;

import com.massivecraft.factions.event.*;
import com.massivecraft.factions.struct.Relation;
import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionsPluginManager;
import me.andrew28.addons.conquer.api.events.*;
import me.andrew28.addons.conquer.api.EventWrapperListener;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

/**
 * @author Andrew Tran
 */
public class FactionsUUIDImplEventWrapperListener extends EventWrapperListener {
    private FactionsUUIDImpl factionsUUID = (FactionsUUIDImpl) FactionsPluginManager.getInstance().getFactionsPlugin();
    
    @EventHandler
    public void onFactionCreate(FactionCreateEvent factionCreateEvent){
        Event e = new ConquerFactionCreateEvent(factionCreateEvent.getFPlayer().getPlayer(), factionCreateEvent.getFactionTag());
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            factionCreateEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionDisband(FactionDisbandEvent factionDisbandEvent){
        Event e = new ConquerFactionDisbandEvent(factionsUUID.getFactionsUUIDFaction(factionDisbandEvent.getFaction()), factionDisbandEvent.getPlayer());
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            factionDisbandEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onUnclaimAllLand(LandUnclaimAllEvent landUnclaimAllEvent){
        ConquerFaction faction = factionsUUID.getFactionsUUIDFaction(landUnclaimAllEvent.getFaction());
        Player unclaimer = landUnclaimAllEvent.getfPlayer().getPlayer();
        Event e = new ConquerFactionLandUnclaimAllEvent(faction, unclaimer);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            landUnclaimAllEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onLandClaim(LandClaimEvent landClaimEvent){
        ConquerFaction faction = factionsUUID.getFactionsUUIDFaction(landClaimEvent.getFaction());
        Player claimer = landClaimEvent.getfPlayer().getPlayer();
        Claim claim = factionsUUID.getFactionsUUIDClaimFromFLocation(landClaimEvent.getLocation());
        Event e = new ConquerFactionLandClaimEvent(faction, claimer, claim);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            landClaimEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onLandUnclaim(LandUnclaimEvent landUnclaimEvent){
        ConquerFaction faction = factionsUUID.getFactionsUUIDFaction(landUnclaimEvent.getFaction());
        Player unclaimer = landUnclaimEvent.getfPlayer().getPlayer();
        Claim claim = factionsUUID.getFactionsUUIDClaimFromFLocation(landUnclaimEvent.getLocation());
        Event e = new ConquerFactionLandUnclaimEvent(faction, unclaimer, claim);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            landUnclaimEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onFPlayerJoin(FPlayerJoinEvent fPlayerJoinEvent){
        ConquerFaction faction = factionsUUID.getFactionsUUIDFaction(fPlayerJoinEvent.getFaction());
        Player player = fPlayerJoinEvent.getfPlayer().getPlayer();
        ConquerFactionPlayerJoinEvent.JoinReason joinReason = ConquerFactionPlayerJoinEvent.JoinReason.OTHER;
        switch (fPlayerJoinEvent.getReason()){
            case CREATE:
                joinReason = ConquerFactionPlayerJoinEvent.JoinReason.CREATE;
                break;
            case LEADER:
                joinReason = ConquerFactionPlayerJoinEvent.JoinReason.LEADER;
                break;
            case COMMAND:
                joinReason = ConquerFactionPlayerJoinEvent.JoinReason.COMMAND;
                break;
        }
        Event e = new ConquerFactionPlayerJoinEvent(faction, player, joinReason);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            fPlayerJoinEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onFPlayerLeave(FPlayerLeaveEvent fPlayerLeaveEvent){
        ConquerFaction faction = factionsUUID.getFactionsUUIDFaction(fPlayerLeaveEvent.getFaction());
        Player player = fPlayerLeaveEvent.getfPlayer().getPlayer();
        ConquerFactionPlayerLeaveEvent.LeaveReason leaveReason = ConquerFactionPlayerLeaveEvent.LeaveReason.valueOf(fPlayerLeaveEvent.getReason().name());
        Event e = new ConquerFactionPlayerLeaveEvent(faction, player, leaveReason);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            fPlayerLeaveEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onRenameEvent(FactionRenameEvent factionRenameEvent){
        ConquerFaction faction = factionsUUID.getFactionsUUIDFaction(factionRenameEvent.getFaction());
        Player player = factionRenameEvent.getfPlayer().getPlayer();
        String oldName = factionRenameEvent.getFaction().getTag();
        String newName = factionRenameEvent.getFactionTag();
        Event e = new ConquerFactionRenameEvent(faction, player, oldName, newName);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            factionRenameEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionRelationEvent(FactionRelationEvent factionRelationEvent){
        ConquerFaction sender = factionsUUID.getFactionsUUIDFaction(factionRelationEvent.getFaction());
        ConquerFaction target = factionsUUID.getFactionsUUIDFaction(factionRelationEvent.getTargetFaction());
        ConquerFactionRelationChangeEvent.Relation oldRelation = getRelation(factionRelationEvent.getOldRelation());
        ConquerFactionRelationChangeEvent.Relation newRelation = getRelation(factionRelationEvent.getRelation());
        Event e = new ConquerFactionRelationChangeEvent(sender, target, oldRelation, newRelation);
        throwEvent(e);
    }

    @EventHandler
    public void onFactionRelationWishEvent(FactionRelationWishEvent factionRelationWishEvent){
        ConquerFaction sender = factionsUUID.getFactionsUUIDFaction(factionRelationWishEvent.getFaction());
        ConquerFaction target = factionsUUID.getFactionsUUIDFaction(factionRelationWishEvent.getTargetFaction());
        ConquerFactionRelationChangeEvent.Relation oldRelation = getRelation(factionRelationWishEvent.getCurrentRelation());
        ConquerFactionRelationChangeEvent.Relation newRelation = getRelation(factionRelationWishEvent.getTargetRelation());
        Event e = new ConquerFactionRelationWishEvent(sender, target, oldRelation, newRelation);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            factionRelationWishEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPowerLossEvent(PowerLossEvent powerLossEvent){
        ConquerFaction faction = factionsUUID.getFactionsUUIDFaction(powerLossEvent.getFaction());
        Player player = powerLossEvent.getfPlayer().getPlayer();
        Event e = faction != null
                ? new ConquerFactionPowerLossEvent(faction)
                : new ConquerPlayerPowerLossEvent(player);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            powerLossEvent.setCancelled(true);
        }
    }

    public ConquerFactionRelationChangeEvent.Relation getRelation(Relation relation){
        return ConquerFactionRelationChangeEvent.Relation.valueOf(relation.name());
    }
}
