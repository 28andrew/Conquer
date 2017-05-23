package me.andrew28.addons.conquer.skript.expressions.factions;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationChangeEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationWishEvent;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Target of Faction/Kingdom/Guild Relation Change/Wish")
@Description("Retrieve the target (the factions/kingdom/guild who is targeted) of a relation change/wish event")
@Syntax("target faction [of] [(faction|kingdom|guild)] [relation] [change|wish]")
@Examples({
        @Example({
                "#or faction relation wish",
                "on faction relation change:",
                "\tbroadcast \"Target: %target faction%\""
        })
})
public class ExprTargetOfFactionRelationChange extends ASAExpression<ConquerFaction>{
    @Override
    public ConquerFaction getValue() throws NullExpressionException {
        if (getCurrentEvent() instanceof ConquerFactionRelationChangeEvent){
            return ((ConquerFactionRelationChangeEvent) getCurrentEvent()).getTarget();
        }else if (getCurrentEvent() instanceof ConquerFactionRelationWishEvent){
            return ((ConquerFactionRelationWishEvent) getCurrentEvent()).getTarget();
        }
        return null;
    }

    @Override
    public Class<? extends Event>[] getRequiredEvents() {
        return new Class[]{ConquerFactionRelationChangeEvent.class, ConquerFactionRelationWishEvent.class};
    }
}
