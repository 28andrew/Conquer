package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.events.ConquerFactionCreateEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Creation")
@Description("Called when a factions/kingdom/guild is created")
@Syntax("(faction|kingdom|guild) creat(e|ion)")
@Examples({
        @Example({
                "on faction create:",
                "\tbroadcast \"%event-player% created the faction %event-string%\""
        })
})
public class EvtFactionCreate extends ASASimpleEvent<ConquerFactionCreateEvent>{
    public EvtFactionCreate() {
        registerEventValue(new EventValue<ConquerFactionCreateEvent, Player>() {
            @Override
            public Player get(ConquerFactionCreateEvent conquerFactionCreateEvent) {
                return conquerFactionCreateEvent.getCreator();
            }
        });
        registerEventValue(new EventValue<ConquerFactionCreateEvent, String>() {
            @Override
            public String get(ConquerFactionCreateEvent conquerFactionCreateEvent) {
                return conquerFactionCreateEvent.getFactionIdentifier();
            }
        });
    }
}
