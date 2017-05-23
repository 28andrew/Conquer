package me.andrew28.addons.conquer.skript.expressions.factions;

import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Description of Faction/Kingdom/Guild")
@Description("Retrieve the description of a factions/kingdom/guild")
@BSyntax(syntax = {"[the] description [of] [the] [(faction|kingdom|guild)] %conquerfaction%", "[(faction|kingdom|guild)] %conquerfaction%'s description"},
bind = "faction")
@Examples({
        @Example({
                "message \"The the description of your faction is %description of faction at player%\""
        })
})
public class ExprDescriptionOfFaction extends ASAExpression<String>{
    @Override
    public String getValue() throws NullExpressionException {
        ConquerFaction faction = (ConquerFaction) exp().get("faction");
        assertNotNull(faction, "Faction given is null");
        return faction.getDescription();
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{new ASAChanger<String>() {
            @Override
            public Changer.ChangeMode[] getChangeModes() {
                return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
            }

            @Override
            public void change(String[] objects) throws NullExpressionException{
                ConquerFaction faction = (ConquerFaction) exp().get("faction");
                assertNotNull(faction, "Faction given to set the description of is null");
                faction.setDescription(objects[0]);
            }
        }};
    }
}
