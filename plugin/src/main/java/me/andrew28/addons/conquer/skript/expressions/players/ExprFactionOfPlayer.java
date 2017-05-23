package me.andrew28.addons.conquer.skript.expressions.players;

import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("Faction/Kingdom/Guild Of Player")
@Description("Retrieve the faction/kingdom/guild of a given player")
@BSyntax(syntax = {"[the] [current] (faction|kingdom|guild) (of|for) [player] %player%",
"%player%'s [current] (faction|kingdom|guild)"}, bind = "player")
@Examples({
        @Example({
                "message \"Your Faction: %faction of player%\""
        })
})
public class ExprFactionOfPlayer extends ASAExpression<ConquerFaction>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public ConquerFaction getValue() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player given is null");
        return factionsPlugin.getConquerPlayer(player).getFaction();
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
                new ASAChanger<ConquerFaction>() {
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
                    }

                    @Override
                    public void change(ConquerFaction[] factions) throws NullExpressionException {
                        Player player = (Player) exp().get("player");
                        assertNotNull(player, "Player given to set the faction of is null");
                        factionsPlugin.getConquerPlayer(player).setFaction(factions[0]);
                    }
                },
                new ASAChanger<ConquerFaction>() {
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.DELETE,
                                Changer.ChangeMode.REMOVE, Changer.ChangeMode.RESET};
                    }

                    @Override
                    public void change(ConquerFaction[] factions) throws NullExpressionException {
                        Player player = (Player) exp().get("player");
                        assertNotNull(player, "Player given to remove the faction of is null");
                        factionsPlugin.getConquerPlayer(player).resetFaction();
                    }
                }
        };
    }
}
