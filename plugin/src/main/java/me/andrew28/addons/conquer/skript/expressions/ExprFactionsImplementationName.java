package me.andrew28.addons.conquer.skript.expressions;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Factions Implementation Name")
@Description("Retrieve the factions implementation name (currently can only be: Original Factions (Massive Core) ,Factions One (Sataniel), Factions UUID (drtshock), or Kingdoms (Hex_27)")
@Syntax("faction[s] impl[ementation] name")
@Examples({
        @Example({
                "broadcast \"Factions Implementation Name: %faction impl name%\""
        })
})
public class ExprFactionsImplementationName extends ASAExpression<String>{
    @Override
    public String getValue() throws NullExpressionException {
        return Conquer.getInstance().getFactionsPluginType().getFriendlyName();
    }
}
