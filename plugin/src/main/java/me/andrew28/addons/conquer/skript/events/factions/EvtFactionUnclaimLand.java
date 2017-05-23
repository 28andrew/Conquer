package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionLandUnclaimEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Land Unclaim")
@Description("Called when a factions/kingdom/guild unclaims a piece/chunk of land")
@Syntax("(faction|kingdom|guild) [(chunk|land)] unclaim")
@Examples({
        @Example({
                "on faction land unclaim:",
                "\tbroadcast \"%event-player% has unclaimed %event-claim% for their faction %event-faction%\""
        })
})
public class EvtFactionUnclaimLand extends ASASimpleEvent<ConquerFactionLandUnclaimEvent>{
    public EvtFactionUnclaimLand(){
        registerEventValue(new EventValue<ConquerFactionLandUnclaimEvent, ConquerFaction>() {
            @Override
            public ConquerFaction get(ConquerFactionLandUnclaimEvent conquerFactionLandUnclaimEvent) {
                return conquerFactionLandUnclaimEvent.getFaction();
            }
        });
        registerEventValue(new EventValue<ConquerFactionLandUnclaimEvent, Player>() {
            @Override
            public Player get(ConquerFactionLandUnclaimEvent conquerFactionLandUnclaimEvent) {
                return conquerFactionLandUnclaimEvent.getUnclaimer();
            }
        });
        registerEventValue(new EventValue<ConquerFactionLandUnclaimEvent, Claim>() {
            @Override
            public Claim get(ConquerFactionLandUnclaimEvent conquerFactionLandUnclaimEvent) {
                return conquerFactionLandUnclaimEvent.getClaim();
            }
        });
    }
}
