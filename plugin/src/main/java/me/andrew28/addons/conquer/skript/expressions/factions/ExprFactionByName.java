package me.andrew28.addons.conquer.skript.expressions.factions;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Faction/Kingdom/Guild with Name/Identifier")
@Description("Retrieve a factions/kingdom/guild by its name/identifier")
@BSyntax(syntax = "[the] (faction|kingdom|guild) (with|by) [(name|id[entifier])] %string%", bind = "identifier")
@Examples({
        @Example({
                "command /factionbyname <text>",
                "\ttrigger:",
                "\t\tset {_fac} to faction by name arg-1",
                "\t\tif {_fac} is not set:",
                "\t\t\tmessage \"Faction not found\"",
                "\t\t\tstop trigger",
                "\t\tmessage \"Faction found!\"",
        })
})
public class ExprFactionByName extends ASAExpression<ConquerFaction>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public ConquerFaction getValue() throws NullExpressionException {
        String identifier = (String) exp().get("identifier");
        assertNotNull(identifier, "Name/Identifier given is null");
        return factionsPlugin.getByName(identifier);
    }
}
