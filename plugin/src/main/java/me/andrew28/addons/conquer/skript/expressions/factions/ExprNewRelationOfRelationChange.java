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
@Name("New Relation Of Faction/Kingdom/Guild Relation Change/Wish")
@Description("Retrieve the new relation (the relation after the change / the relation of the wish) of a relation change/wish event")
@Syntax("[new] relation [of] [(faction|kingdom|guild)] [relation] [change]")
@Examples({
        @Example({
                "#or faction relation wish",
                "on faction relation change:",
                "\tbroadcast \"New Relation: %new relation%\""
        })
})
public class ExprNewRelationOfRelationChange extends ASAExpression<ConquerFactionRelationChangeEvent.Relation>{
    @Override
    public ConquerFactionRelationChangeEvent.Relation getValue() throws NullExpressionException {
        if (getCurrentEvent() instanceof ConquerFactionRelationChangeEvent){
            return ((ConquerFactionRelationChangeEvent) getCurrentEvent()).getNewRelation();
        }else if (getCurrentEvent() instanceof ConquerFactionRelationWishEvent){
            return ((ConquerFactionRelationWishEvent) getCurrentEvent()).getNewRelation();
        }
        return null;
    }

    @Override
    public Class<? extends Event>[] getRequiredEvents() {
        return new Class[]{ConquerFactionRelationChangeEvent.class, ConquerFactionRelationWishEvent.class};
    }
}
