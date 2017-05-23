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
@Name("Identifier (ID) of Faction/Kingdom/Guild")
@Description("Retrieve the identifier (ID) of a factions/kingdom/guild")
@BSyntax(syntax = {"[the] id[entifier] [of] [the] [(faction|kingdom|guild)] %conquerfaction%", "[(faction|kingdom|guild)] %conquerfaction%'s id[entifier]"},
bind = "faction")
@Examples({
        @Example({
                "message \"The id (identifier) of your faction is %identifier of faction of player%\""
        })
})
public class ExprIdentifierOfFaction extends ASAExpression<String>{
    @Override
    public String getValue() throws NullExpressionException {
        ConquerFaction faction = (ConquerFaction) exp().get("faction");
        assertNotNull(faction, "Faction given is null");
        return faction.getIdentifier();
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
                assertNotNull(faction, "Faction given to set the identifier of is null");
                faction.setIdentifier(objects[0]);
            }
        }};
    }
}
