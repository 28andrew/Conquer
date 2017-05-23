package me.andrew28.addons.conquer.skript.expressions.factions;

import me.andrew28.addons.conquer.api.events.ConquerFactionRelationChangeEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationWishEvent;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Old Relation Of Faction/Kingdom/Guild Relation Change")
@Description("Retrieve the old relation (the relation before the change) of a relation change event")
@Syntax("old relation [of] [(faction|kingdom|guild)] [relation] [change]")
@Examples({
        @Example({
                "#or faction relation wish",
                "on faction relation change:",
                "\tbroadcast \"New Relation: %old relation%\""
        })
})
public class ExprOldRelationOfRelationChange extends ASAExpression<ConquerFactionRelationChangeEvent.Relation>{
    @Override
    public ConquerFactionRelationChangeEvent.Relation getValue() throws NullExpressionException {
        if (getCurrentEvent() instanceof ConquerFactionRelationChangeEvent){
            return ((ConquerFactionRelationChangeEvent) getCurrentEvent()).getOldRelation();
        }else if (getCurrentEvent() instanceof ConquerFactionRelationWishEvent){
            return ((ConquerFactionRelationWishEvent) getCurrentEvent()).getOldRelation();
        }
        return null;
    }

    @Override
    public Class<? extends Event>[] getRequiredEvents() {
        return new Class[]{ConquerFactionRelationChangeEvent.class, ConquerFactionRelationWishEvent.class};
    }
}
