package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionPowerLossEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Power Loss")
@Description("Called when a faction/kingdom/guild loses some of their power")
@Syntax("(faction|kingdom|guild) power los(s|e)")
@Examples({
        @Example({
                "on faction power loss:",
                "\tbroadcast \"%event-faction% has lost some power\""
        })
})
public class EvtFactionPowerLoss extends ASASimpleEvent<ConquerFactionPowerLossEvent>{
    public EvtFactionPowerLoss() {
        registerEventValue(new EventValue<ConquerFactionPowerLossEvent, ConquerFaction>() {
            @Override
            public ConquerFaction get(ConquerFactionPowerLossEvent conquerFactionPowerLossEvent) {
                return conquerFactionPowerLossEvent.getFaction();
            }
        });
    }
}
