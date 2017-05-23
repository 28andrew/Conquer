package me.andrew28.addons.conquer.skript.expressions.factions;

import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.Location;

/**
 * @author Andrew Tran
 */
@Name("Home of Faction/Kingdom/Guild")
@Description("Retrieve the home (location) of a factions/kingdom/guild")
@BSyntax(syntax = {"[the] home [location] [of] [the] [(faction|kingdom|guild)] %conquerfaction%", "[(faction|kingdom|guild)] %conquerfaction%'s home [location]"},
bind = "factions")
@Examples({
        @Example({
                "command /factionhome:",
                "\ttrigger:",
                "\t\tteleport player to home of faction of player"
        })
})
public class ExprHomeOfFaction extends ASAExpression<Location>{
    @Override
    public Location getValue() throws NullExpressionException {
        ConquerFaction faction = (ConquerFaction) exp().get("factions");
        assertNotNull(faction, "Faction given is null");
        return faction.getHome();
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{new ASAChanger<Location>() {
            @Override
            public Changer.ChangeMode[] getChangeModes() {
                return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
            }

            @Override
            public void change(Location[] objects) throws NullExpressionException{
                ConquerFaction faction = (ConquerFaction) exp().get("factions");
                assertNotNull(faction, "Faction given to set the home of is null");
                faction.setHome(objects[0]);
            }
        }};
    }
}
