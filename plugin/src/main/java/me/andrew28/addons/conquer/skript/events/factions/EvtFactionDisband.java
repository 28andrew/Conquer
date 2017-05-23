package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionDisbandEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Deletion/Disband")
@Description("Called when a factions/kingdom/guild is removed/deleted/redacted/disbanded")
@Syntax("(faction|kingdom|guild) (delet(e|ion)|disband[ment][ed]|remov(al|e))")
@Examples({
        @Example({
                "on faction disband:",
                "\tbroadcast \"%event-player% has disbanded the faction %event-faction%\""
        })
})
public class EvtFactionDisband extends ASASimpleEvent<ConquerFactionDisbandEvent>{
    public EvtFactionDisband() {
        registerEventValue(new EventValue<ConquerFactionDisbandEvent, ConquerFaction>() {
            @Override
            public ConquerFaction get(ConquerFactionDisbandEvent conquerFactionDisbandEvent) {
                return conquerFactionDisbandEvent.getFaction();
            }
        });
        registerEventValue(new EventValue<ConquerFactionDisbandEvent, Player>() {
            @Override
            public Player get(ConquerFactionDisbandEvent conquerFactionDisbandEvent) {
                return conquerFactionDisbandEvent.getPlayer();
            }
        });
    }
}
