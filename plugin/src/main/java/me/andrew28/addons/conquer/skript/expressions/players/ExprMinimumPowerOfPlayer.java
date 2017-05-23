package me.andrew28.addons.conquer.skript.expressions.players;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("Minimum Power of Player")
@Description("Retrieve the minimum power of a given player")
@BSyntax(syntax = {"[the] min[imum] power [level] of [player] %player%","%player%'s min[imum] power [level]"},
bind = "player")
@Examples({
        @Example({
                "message \"Your minimum power: %min power of player%\""
        })
})
public class ExprMinimumPowerOfPlayer extends ASAExpression<Double>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public Double getValue() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player given is null");
        return factionsPlugin.getConquerPlayer(player).getMinimumPower();
    }
}
