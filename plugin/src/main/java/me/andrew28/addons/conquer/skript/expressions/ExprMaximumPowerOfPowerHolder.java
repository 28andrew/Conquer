package me.andrew28.addons.conquer.skript.expressions;

import me.andrew28.addons.conquer.api.PowerHolder;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Maximum Power of Player/Faction/Kingdom/Guild")
@Description("Retrieve the power of a given Player/Faction/Kingdom/Guild")
@BSyntax(syntax = {"[the] max[imum] power [level] of [(player|faction|kingdom|guild)] %powerholder%","[(player|faction|kingdom|guild)] %powerholder%'s max[imum] power [level]"}, bind = "powerholder")
@Examples({
        @Example({
                "message \"Your Max Power: %max power of player%\"",
                "message \"Faction Max Power: %max power of faction of player%\""
        })
})
public class ExprMaximumPowerOfPowerHolder extends ASAExpression<Double>{
    @Override
    public Double getValue() throws NullExpressionException {
        PowerHolder powerHolder = (PowerHolder) exp().get("powerholder");
        assertNotNull(powerHolder, "Given powerholder is null");
        return powerHolder.getMaximumPower();
    }
}
