package me.andrew28.addons.conquer;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.event.*;
import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.EventWrapperListener;
import me.andrew28.addons.conquer.api.events.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

/**
 * @author Andrew Tran
 */
public class FactionsImplEventWrapper extends EventWrapperListener{
    private FactionsImpl factions;

    public FactionsImplEventWrapper(FactionsImpl factions) {
        this.factions = factions;
    }

    public FactionsImpl getFactions() {
        return factions;
    }

    @EventHandler
    public void onFactionCreate(EventFactionsCreate eventFactionsCreate){
        Event e = new ConquerFactionCreateEvent(eventFactionsCreate.getMPlayer().getPlayer(), eventFactionsCreate.getFactionId());
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            eventFactionsCreate.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionDisband(EventFactionsDisband eventFactionsDisband){
        Event e = new ConquerFactionDisbandEvent(factions.getFactionsFactionFromFaction(eventFactionsDisband.getFaction()), eventFactionsDisband.getMPlayer().getPlayer());
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            eventFactionsDisband.setCancelled(true);
        }
    }

    @EventHandler
    public void onLandChangeEvent(EventFactionsChunksChange eventFactionsChunksChange){
        eventFactionsChunksChange.getTypeChunks().forEach((type, psList) -> {
            ConquerFaction faction = factions.getFactionsFactionFromFaction(eventFactionsChunksChange.getNewFaction());
            Player player = eventFactionsChunksChange.getMPlayer().getPlayer();
            switch (type){
                case BUY:
                case CONQUER:
                case PILLAGE:
                    psList.forEach(ps -> {
                        Claim claim = factions.getFactionsClaimFromPS(ps);
                        Event e = new ConquerFactionLandClaimEvent(faction, player, claim);
                        throwEvent(e);
                        if (((Cancellable) e).isCancelled()){
                            eventFactionsChunksChange.setCancelled(true);
                        }
                    });
                    break;
                case SELL:
                    psList.forEach(ps -> {
                        Claim claim = factions.getFactionsClaimFromPS(ps);
                        Event e = new ConquerFactionLandUnclaimEvent(faction, player, claim);
                        throwEvent(e);
                        if (((Cancellable) e).isCancelled()){
                            eventFactionsChunksChange.setCancelled(true);
                        }
                    });
                    break;
            }
        });
    }

    @EventHandler
    public void onFactionPlayerJoin(EventFactionsMembershipChange eventFactionsMembershipChange){
        ConquerFaction faction = factions.getFactionsFactionFromFaction(eventFactionsMembershipChange.getNewFaction());
        Player player = eventFactionsMembershipChange.getMPlayer().getPlayer();
        Boolean join = false;
        Boolean cancelChecks = eventFactionsMembershipChange.getReason().isCancellable();
        switch (eventFactionsMembershipChange.getReason()){

            case CREATE:
            case RANK:
            case JOIN:
                join = true;
                break;
            case LEAVE:
            case KICK:
            case DISBAND:
                break;
        }
        Event e = null;
        if (join){
            ConquerFactionPlayerJoinEvent.JoinReason joinReason = ConquerFactionPlayerJoinEvent.JoinReason.OTHER;
            switch (eventFactionsMembershipChange.getReason()){
                case JOIN:
                    joinReason = ConquerFactionPlayerJoinEvent.JoinReason.COMMAND;
                    break;
                case CREATE:
                    joinReason = ConquerFactionPlayerJoinEvent.JoinReason.CREATE;
                    break;
            }
            e = new ConquerFactionPlayerJoinEvent(faction, player, joinReason);
        }else{
            ConquerFactionPlayerLeaveEvent.LeaveReason leaveReason = ConquerFactionPlayerLeaveEvent.LeaveReason.OTHER;
            switch (eventFactionsMembershipChange.getReason()){
                case LEAVE:
                    leaveReason = ConquerFactionPlayerLeaveEvent.LeaveReason.LEAVE;
                    break;
                case KICK:
                    leaveReason = ConquerFactionPlayerLeaveEvent.LeaveReason.KICKED;
                    break;
                case DISBAND:
                    leaveReason = ConquerFactionPlayerLeaveEvent.LeaveReason.DISBAND;
                    break;
            }
            e = new ConquerFactionPlayerLeaveEvent(faction, player, leaveReason);
        }
        throwEvent(e);
        if (cancelChecks && ((Cancellable) e).isCancelled()){
            eventFactionsMembershipChange.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionRename(EventFactionsNameChange eventFactionsNameChange){
        ConquerFaction faction = factions.getFactionsFactionFromFaction(eventFactionsNameChange.getFaction());
        Player player = eventFactionsNameChange.getMPlayer().getPlayer();
        String newName = eventFactionsNameChange.getNewName();
        String oldName = faction.getName();
        Event e = new ConquerFactionRenameEvent(faction, player, oldName, newName);
        throwEvent(e);
        if (((Cancellable) e).isCancelled()){
            eventFactionsNameChange.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionRelationChange(EventFactionsRelationChange eventFactionsRelationChange){
        ConquerFaction sender = factions.getFactionsFactionFromFaction(eventFactionsRelationChange.getFaction());
        ConquerFaction target = factions.getFactionsFactionFromFaction(eventFactionsRelationChange.getOtherFaction());
        ConquerFactionRelationChangeEvent.Relation oldRelation = getRelation(eventFactionsRelationChange.getFaction().getRelationTo(eventFactionsRelationChange.getOtherFaction()));
        ConquerFactionRelationChangeEvent.Relation newRelation = getRelation(eventFactionsRelationChange.getNewRelation());
        Event e = new ConquerFactionRelationChangeEvent(sender, target, oldRelation, newRelation);
        if (((Cancellable) e).isCancelled()){
            eventFactionsRelationChange.setCancelled(true);
        }
    }

    public ConquerFactionRelationChangeEvent.Relation getRelation(Rel rel){
        switch (rel){
            case ENEMY:
                return ConquerFactionRelationChangeEvent.Relation.ENEMY;
            case NEUTRAL:
                return ConquerFactionRelationChangeEvent.Relation.NEUTRAL;
            case TRUCE:
                return ConquerFactionRelationChangeEvent.Relation.TRUCE;
            case ALLY:
                return ConquerFactionRelationChangeEvent.Relation.ALLY;
        }
        return ConquerFactionRelationChangeEvent.Relation.OTHER;
    }
}
