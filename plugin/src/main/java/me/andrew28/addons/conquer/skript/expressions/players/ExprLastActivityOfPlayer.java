package me.andrew28.addons.conquer.skript.expressions.players;

import ch.njol.skript.util.Date;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("Last Activity Of Player")
@Description("Retrieve when a given player was last active")
@BSyntax(syntax = {"[the] last activ(ity|e) [(date|time)] (for|or|of) [player] %player%",
"%player%'s last activ(ity|e) (date|time)"}, bind = "player")
@Examples({
        @Example({
                "message \"Your last activity: %last activity of player%\""
        })
})
public class ExprLastActivityOfPlayer extends ASAExpression<Date>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public Date getValue() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player given is null");
        return new Date(factionsPlugin.getConquerPlayer(player).getLastActivity().getTime());
    }
}
