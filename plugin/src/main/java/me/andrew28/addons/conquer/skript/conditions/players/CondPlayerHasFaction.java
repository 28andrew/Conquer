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
@Name("Player Has Faction/Kingdom/Guild")
@Description("Passes if the given player is in a faction/kingdom/guild")
@BSyntax(syntax = {"%player% (0¦has|1¦(doesn't have|does not have)) [a] (faction|kingdom|guild)",
"%player% (0¦in|1¦(isn't|is not)) [a] (faction|kingdom|guild)"},
bind = "player")
@Examples({
        @Example({
                "if player has a faction:",
                "\tmessage \"You have a faction!\"",
                "else:",
                "\tmessage \"You don't have a faction :(\""
        })
})
public class CondPlayerHasFaction extends ASACondition{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public boolean check() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player given is null");
        Boolean invert = getParseResult().mark == 1;
        return invert != factionsPlugin.getConquerPlayer(player).hasFaction();
    }
}
