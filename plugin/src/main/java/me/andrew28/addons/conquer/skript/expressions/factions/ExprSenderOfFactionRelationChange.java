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
@Name("Sender of Faction/Kingdom/Guild Relation Change/Wish")
@Description("Retrieve the sender (the factions/kingdom/guild who started) of a relation change/wish event")
@Syntax("sender faction [of] [(faction|kingdom|guild)] [relation] [change|wish]")
@Examples({
        @Example({
                "#or faction relation wish",
                "on faction relation change:",
                "\tbroadcast \"Sender: %sender faction%\""
        })
})
public class ExprSenderOfFactionRelationChange extends ASAExpression<ConquerFaction>{
    @Override
    public ConquerFaction getValue() throws NullExpressionException {
        if (getCurrentEvent() instanceof ConquerFactionRelationChangeEvent){
            return ((ConquerFactionRelationChangeEvent) getCurrentEvent()).getSender();
        }else if (getCurrentEvent() instanceof ConquerFactionRelationWishEvent){
            return ((ConquerFactionRelationWishEvent) getCurrentEvent()).getSender();
        }
        return null;
    }

    @Override
    public Class<? extends Event>[] getRequiredEvents() {
        return new Class[]{ConquerFactionRelationChangeEvent.class, ConquerFactionRelationWishEvent.class};
    }
}
