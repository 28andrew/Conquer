package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionLandUnclaimAllEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Unclaiming/Deleting All Claims/Land")
@Description("Called when a factions/kingdom/guild unclaims all of their land")
@Syntax("(faction|kingdom|guild) unclaim[ing] all [(land|claim[s])]")
@Examples({
        @Example({
                "on faction unclaim all land:",
                "\tbroadcast \"%event-player% has unclaimed all the land of their faction %event-faction%\""
        })
})
public class EvtFactionUnclaimAll extends ASASimpleEvent<ConquerFactionLandUnclaimAllEvent>{
    public EvtFactionUnclaimAll() {
        registerEventValue(new EventValue<ConquerFactionLandUnclaimAllEvent, ConquerFaction>() {
            @Override
            public ConquerFaction get(ConquerFactionLandUnclaimAllEvent conquerFactionLandUnclaimAllEvent) {
                return conquerFactionLandUnclaimAllEvent.getFaction();
            }
        });
        registerEventValue(new EventValue<ConquerFactionLandUnclaimAllEvent, Player>() {
            @Override
            public Player get(ConquerFactionLandUnclaimAllEvent conquerFactionLandUnclaimAllEvent) {
                return conquerFactionLandUnclaimAllEvent.getPlayer();
            }
        });
    }
}
