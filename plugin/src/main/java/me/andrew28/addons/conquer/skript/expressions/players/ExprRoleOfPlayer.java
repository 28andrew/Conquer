package me.andrew28.addons.conquer.skript.expressions.players;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.conquer.api.Role;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("Faction/Kingdom/Guild Rank/Role of Player")
@Description("Retrieve the faction/kingdom/guild rank/role of a given player")
@BSyntax(syntax = {"[the] (faction|kingdom|guild) (rank|role) (of|for) [the] [player] %player%",
"%player%'s (faction|kingdom|guild) (rank|role)"}, bind = "player")
@Examples({
        @Example({
                "message \"Your Role: %role of player%\""
        })
})
public class ExprRoleOfPlayer extends ASAExpression<Role>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public Role getValue() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player given is null");
        return factionsPlugin.getConquerPlayer(player).getRole();
    }
}
