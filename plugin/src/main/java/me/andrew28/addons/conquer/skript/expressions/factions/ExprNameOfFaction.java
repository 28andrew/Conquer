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
@Name("Name of Faction/Kingdom/Guild")
@Description("Retrieve the name of a factions/kingdom/guild")
@BSyntax(syntax = {"[the] name [of] [the] [(faction|kingdom|guild)] %conquerfaction%", "[(faction|kingdom|guild)] %conquerfaction%'s name"},
bind = "faction")
@Examples({
        @Example({
                "#You don't even need `name of`, it automatically prints out the name if used in string form",
                "message \"The name of your faction is %name of faction of player%\""
        })
})
public class ExprNameOfFaction extends ASAExpression<String>{
    @Override
    public String getValue() throws NullExpressionException {
        ConquerFaction faction = (ConquerFaction) exp().get("faction");
        assertNotNull(faction, "Faction given is null");
        return faction.getName();
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
                assertNotNull(faction, "Faction given to set the name of is null");
                faction.setName(objects[0]);
            }
        }};
    }
}
