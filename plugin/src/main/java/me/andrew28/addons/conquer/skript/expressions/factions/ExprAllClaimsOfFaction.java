package me.andrew28.addons.conquer.skript.expressions.factions;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("All Claims Of Faction/Kingdom/Guild")
@Description("Retrieve all the claims/land/region of a given factions/kingdom/guild")
@BSyntax(syntax = {"[all] [the] (claims|land|regions) (of|for) [(faction|kingdom|guild)] %conquerfaction%",
        "%conquerfaction%'[s] (claims|land|regions)"}, bind = "factions")
@Examples({
        @Example({
                "loop all claims of faction of player:",
                "\tbroadcast \"Claim: %loop-claim%\""
        })
})
public class ExprAllClaimsOfFaction extends ASAExpression<Claim>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public Claim[] getValues() throws NullExpressionException{
        ConquerFaction faction = (ConquerFaction) exp().get("factions");
        assertNotNull(faction, "Faction given is null");
        return factionsPlugin.getClaims(faction);
    }
}
