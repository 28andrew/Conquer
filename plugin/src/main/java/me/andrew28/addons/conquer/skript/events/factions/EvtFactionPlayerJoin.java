package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionPlayerJoinEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Player/New Member Join")
@Description("Called when a new player becomes a member of a given factions/kingdom/guild")
@Syntax({"[player] (faction|kingdom|guild) [player] join"})
@Examples({
        @Example({
                "on faction player join:",
                "\tbroadcast \"%event-player% has joined %event-faction%\""
        })
})
public class EvtFactionPlayerJoin extends ASASimpleEvent<ConquerFactionPlayerJoinEvent>{
    public EvtFactionPlayerJoin() {
        registerEventValue(new EventValue<ConquerFactionPlayerJoinEvent, ConquerFaction>() {
            @Override
            public ConquerFaction get(ConquerFactionPlayerJoinEvent conquerFactionPlayerJoinEvent) {
                return conquerFactionPlayerJoinEvent.getFaction();
            }
        });
        registerEventValue(new EventValue<ConquerFactionPlayerJoinEvent, Player>() {
            @Override
            public Player get(ConquerFactionPlayerJoinEvent conquerFactionPlayerJoinEvent) {
                return conquerFactionPlayerJoinEvent.getPlayer();
            }
        });
        registerEventValue(new EventValue<ConquerFactionPlayerJoinEvent, ConquerFactionPlayerJoinEvent.JoinReason>() {
            @Override
            public ConquerFactionPlayerJoinEvent.JoinReason get(ConquerFactionPlayerJoinEvent conquerFactionPlayerJoinEvent) {
                return conquerFactionPlayerJoinEvent.getJoinReason();
            }
        });
    }
}
