package me.andrew28.addons.conquer.skript.conditions.players;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASACondition;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("Player Is Automatically Claiming")
@Description("Passes if the player is automatically claiming land")
@BSyntax(syntax = {"%player% (0¦is|1¦is not) auto[(matical[ly])] claim[ing] [land]", "%player%'s auto[matic] claim[ing] (state|mode) (0¦is|1¦is not) on"},
bind = "player")
@Examples({
        @Example({
                "if player is automatically claiming land:",
                "\tmessage \"You are automatically claiming land!\"",
                "else:",
                "\tmessage \"You are not automatically claiming land :(\""
        })
})
public class CondPlayerIsAutoClaiming extends ASACondition{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public boolean check() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player given is null");
        boolean invert = getParseResult().mark == 1;
        boolean autoClaiming = factionsPlugin.getConquerPlayer(player).isAutoClaiming();
        return invert != autoClaiming;
    }
}
