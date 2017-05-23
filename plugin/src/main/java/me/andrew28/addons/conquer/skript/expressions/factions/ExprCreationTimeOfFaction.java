package me.andrew28.addons.conquer.skript.expressions.factions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.util.Date;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Creation Time of Faction/Kingdom/Guild")
@Description("Retrieve the creation time of a given factions/kingdom/guild")
@BSyntax(syntax = {"[the] (time|date) [when] %conquerfaction% was created", "%conquerfaction%'s (time|date) created",
"[the] creat(ed|ion) (time|date) of %conquerfaction%", "%conquerfaction%'s creat(ed|ion) (time|date)"},
bind = "faction")
@Examples({
        @Example({
                "message \"Your faction was created on %creation date of faction of player%\""
        })
})
public class ExprCreationTimeOfFaction extends ASAExpression<Date>{
    @Override
    public Date getValue() throws NullExpressionException {
        ConquerFaction faction = (ConquerFaction) exp().get("faction");
        assertNotNull(faction, "Faction given is null");
        return new Date(faction.getCreationDate().getTime());
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
                new ASAChanger<Date>() {
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                       return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
                    }

                    @Override
                    public void change(Date[] dates) throws NullExpressionException {
                        ConquerFaction faction = (ConquerFaction) exp().get("faction");
                        assertNotNull(faction, "Faction given to set the creation time of is null");
                        faction.setCreationDate(new java.util.Date(dates[0].getTimestamp()));
                    }
                }
        };
    }
}
