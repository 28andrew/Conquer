package me.andrew28.addons.conquer.skript.expressions.factions;

import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.OfflinePlayer;

/**
 * @author Andrew Tran
 */
@Name("Leader of Faction/Kingdom/Guild")
@Description("Retrieve the leader of a given factions/kingdom/guild")
@BSyntax(syntax = {"[the] leader of [the] [(faction|kingdom|guild)] %conquerfaction%",
"%conquerfaction%'s leader"}, bind = "faction")
@Examples({
        @Example({
                "message \"The leader of your faction is %leader of faction of player%\""
        })
})
public class ExprLeaderOfFaction extends ASAExpression<OfflinePlayer>{
    @Override
    public OfflinePlayer getValue() throws NullExpressionException {
        ConquerFaction faction = (ConquerFaction) exp().get("faction");
        assertNotNull(faction, "Faction given is null");
        return faction.getLeader();
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
            new ASAChanger<OfflinePlayer>(){
                @Override
                public Changer.ChangeMode[] getChangeModes() {
                    return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
                }

                @Override
                public void change(OfflinePlayer[] offlinePlayers) throws NullExpressionException {
                    ConquerFaction faction = (ConquerFaction) exp().get("faction");
                    assertNotNull(faction, "Faction given to set the leader of is null");
                    faction.setLeader(offlinePlayers[0]);
                }
            }
        };
    }
}
