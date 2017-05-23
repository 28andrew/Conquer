package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.events.ConquerFactionRelationChangeEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventTime;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Relation Change")
@Description("Called when a factions/kingdom/guild changes their relation with another factions/kingdom/guild")
@Syntax("[(faction|kingdom|guild)] relation (change|update)")
@Examples({
        @Example({
                "on faction relation change:",
                "\tbroadcast \"Faction %sender faction% is now a(n) %event-relation% to %target faction%, they used to be %former event-relation%\""
        })
})
public class EvtFactionRelationChange extends ASASimpleEvent<ConquerFactionRelationChangeEvent> {
    public EvtFactionRelationChange() {
        registerEventValue(new EventValue<ConquerFactionRelationChangeEvent, ConquerFactionRelationChangeEvent.Relation>() {
            @Override
            public ConquerFactionRelationChangeEvent.Relation get(ConquerFactionRelationChangeEvent conquerFactionRelationChangeEvent) {
                return conquerFactionRelationChangeEvent.getOldRelation();
            }
        }, EventTime.BEFORE);
        registerEventValue(new EventValue<ConquerFactionRelationChangeEvent, ConquerFactionRelationChangeEvent.Relation>() {
            @Override
            public ConquerFactionRelationChangeEvent.Relation get(ConquerFactionRelationChangeEvent conquerFactionRelationChangeEvent) {
                return conquerFactionRelationChangeEvent.getNewRelation();
            }
        }, EventTime.NORMAL);
    }
}
