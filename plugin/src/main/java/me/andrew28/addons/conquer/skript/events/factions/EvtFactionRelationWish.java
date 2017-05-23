package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.events.ConquerFactionRelationChangeEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationWishEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventTime;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Relation Change Request/Wish")
@Description("Called when a factions/kingdom/guild requests to change a relation with another factions/kingdom/guild")
@Syntax("[(faction|kingdom|guild)] relation (wish|request)")
@Examples({
        @Example({
                "on faction relation wish:",
                "\tbroadcast \"Faction %sender faction% would like to be %event-relation% to %target faction%, they are currently %former event-relation%\""
        })
})
public class EvtFactionRelationWish extends ASASimpleEvent<ConquerFactionRelationWishEvent>{
    public EvtFactionRelationWish() {
        registerEventValue(new EventValue<ConquerFactionRelationWishEvent, ConquerFactionRelationChangeEvent.Relation>() {
            @Override
            public ConquerFactionRelationChangeEvent.Relation get(ConquerFactionRelationWishEvent conquerFactionRelationWishEvent) {
                return conquerFactionRelationWishEvent.getOldRelation();
            }
        }, EventTime.BEFORE);
        registerEventValue(new EventValue<ConquerFactionRelationWishEvent, ConquerFactionRelationChangeEvent.Relation>() {
            @Override
            public ConquerFactionRelationChangeEvent.Relation get(ConquerFactionRelationWishEvent conquerFactionRelationWishEvent) {
                return conquerFactionRelationWishEvent.getNewRelation();
            }
        }, EventTime.NORMAL);
    }
}
