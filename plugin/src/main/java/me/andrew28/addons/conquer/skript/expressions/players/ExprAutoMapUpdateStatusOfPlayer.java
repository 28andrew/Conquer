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
@Name("Automatic Map Update Mode of Player")
@Description("Retrieve the automatic map update mode of a given player")
@BSyntax(syntax = {"[the] auto[matic] map [update] (mode|state|status) (of|for) [player] %player%",
"%player%'s auto[matic] map [update] (mode|state|status)"}, bind = "player")
@Examples({
        @Example({
                "message \"Auto Map Mode: %auto map mode of player%\""
        })
})
public class ExprAutoMapUpdateStatusOfPlayer extends ASAExpression<Boolean>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public Boolean getValue() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player given is null");
        return factionsPlugin.getConquerPlayer(player).getAutomaticMapUpdateMode();
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
                new ASAChanger<Boolean>(){
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
                    }

                    @Override
                    public void change(Boolean[] booleans) throws NullExpressionException {
                        Player player = (Player) exp().get("player");
                        assertNotNull(player, "Player given to change the automatic map update mode of is null");
                        factionsPlugin.getConquerPlayer(player).setAutomaticMapUpdateMode(booleans[0]);
                    }
                }, new ASAChanger<Boolean>() {
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.RESET};
                    }

                    @Override
                    public void change(Boolean[] booleans) throws NullExpressionException {
                        Player player = (Player) exp().get("player");
                        assertNotNull(player, "Player given to reset the automatic map update mode of is null");
                        factionsPlugin.getConquerPlayer(player).setAutomaticMapUpdateMode(false);
                    }
                }
        };
    }
}
