package me.andrew28.addons.conquer.skript.expressions.players;

import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("Display Name/Title of Player")
@Description("Retrieve the title (display name) a given player has in a faction/kingdom/guild")
@BSyntax(syntax = {"[the] (faction|kingdom|guild) [display] [(title|name)] (of|for) %player%",
"%player%'s (faction|kingdom|guild) [display] [(title|name)]"}, bind = "player")
@Examples({
        @Example({
                "message \"Your Title: %faction title of player%\""
        })
})
public class ExprTitleOfPlayer extends ASAExpression<String>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public String getValue() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player is null");
        return factionsPlugin.getConquerPlayer(player).getTitle();
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
                new ASAChanger<String>(){
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
                    }

                    @Override
                    public void change(String[] strings) throws NullExpressionException {
                        Player player = (Player) exp().get("player");
                        assertNotNull(player, "Player to set the faction title/name of is null");
                        factionsPlugin.getConquerPlayer(player).getTitle();
                    }
                },
                new ASAChanger<String>(){
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{
                                Changer.ChangeMode.RESET,
                                Changer.ChangeMode.DELETE,
                                Changer.ChangeMode.REMOVE,
                                Changer.ChangeMode.REMOVE_ALL};
                    }

                    @Override
                    public void change(String[] strings) throws NullExpressionException {
                        Player player = (Player) exp().get("player");
                        assertNotNull(player, "Player to clear the faction title/name of is null");
                        factionsPlugin.getConquerPlayer(player).resetTitle();
                    }
                }
        };
    }
}
