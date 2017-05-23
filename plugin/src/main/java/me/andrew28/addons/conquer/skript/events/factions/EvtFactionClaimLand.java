package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionLandClaimEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Land Claim")
@Description("Called when a factions/kingdom/guild claims a piece/chunk of land")
@Syntax({"(faction|kingdom|guild)[s] (chunk|land) claim","(faction|kingdom|guild)['s][s] claim[ing] (chunk|land) "})
@Examples({
        @Example({
                "on faction land claim:",
                "\tbroadcast \"%event-player% from %event-faction% claimed the land %event-claim%\""
        })
})
public class EvtFactionClaimLand extends ASASimpleEvent<ConquerFactionLandClaimEvent>{
    public EvtFactionClaimLand(){
        registerEventValue(new EventValue<ConquerFactionLandClaimEvent, ConquerFaction>() {
            @Override
            public ConquerFaction get(ConquerFactionLandClaimEvent conquerFactionLandClaimEvent) {
                return conquerFactionLandClaimEvent.getFaction();
            }
        });
        registerEventValue(new EventValue<ConquerFactionLandClaimEvent, Player>() {
            @Override
            public Player get(ConquerFactionLandClaimEvent conquerFactionLandClaimEvent) {
                return conquerFactionLandClaimEvent.getClaimer();
            }
        });
        registerEventValue(new EventValue<ConquerFactionLandClaimEvent, Claim>() {
            @Override
            public Claim get(ConquerFactionLandClaimEvent conquerFactionLandClaimEvent) {
                return conquerFactionLandClaimEvent.getClaim();
            }
        });
    }
}
