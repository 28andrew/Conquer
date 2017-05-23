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
@Name("MOTD of Faction/Kingdom/Guild")
@Description("Retrieve the MOTD (message of the day) of a factions/kingdom/guild")
@BSyntax(syntax = {"[the] motd [of] [the] [(faction|kingdom|guild)] %conquerfaction%", "[(faction|kingdom|guild)] %conquerfaction%'s motd"},
bind = "factions")
@Examples({
        @Example({
                "message \"The MOTD of your faction is %motd of faction of player%\""
        })
})
public class ExprMotdOfFaction extends ASAExpression<String>{
    @Override
    public String getValue() throws NullExpressionException {
        ConquerFaction faction = (ConquerFaction) exp().get("factions");
        assertNotNull(faction, "Faction given is null");
        return faction.getMotd();
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
                ConquerFaction faction = (ConquerFaction) exp().get("factions");
                assertNotNull(faction, "Faction given to set the MOTD of is null");
                faction.setMotd(objects[0]);
            }
        }};
    }
}
