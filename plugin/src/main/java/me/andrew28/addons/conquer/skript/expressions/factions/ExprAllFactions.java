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
@Name("All Factions/Kingdoms/Guilds")
@Description("Retrieve all the factions/kingdoms/guilds")
@Syntax("all (factions|kingdoms|guilds) [on] [the] [server]")
@Examples({
        @Example({
                "broadcast \"All Factions: %all factions%\""
        })
})
public class ExprAllFactions extends ASAExpression<ConquerFaction>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public ConquerFaction[] getValues() throws NullExpressionException {
        return factionsPlugin.getAll();
    }
}
