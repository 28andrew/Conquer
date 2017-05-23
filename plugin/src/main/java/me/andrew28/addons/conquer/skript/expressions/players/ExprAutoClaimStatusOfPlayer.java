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
@Name("Auto Claiming Status/State/Mode of a Player")
@Description("Retrieve the auto claiming status/state/mode for a given player")
@BSyntax(syntax = {"[the] auto[matic][ ]claim[ing] ((stat(e|us))|mode) (of|for|from) [player] %player%",
"%player%'s auto[matic][ ]claim[ing] ((stat(e|us))|mode)"}, bind = "player")
@Examples({
        @Example({
                "message \"Auto Claim Mode: %auto claim state of player%\""
        })
})
public class ExprAutoClaimStatusOfPlayer extends ASAExpression<Boolean>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public Boolean getValue() throws NullExpressionException {
        Player player = (Player) exp().get("player");
        assertNotNull(player, "Player given is null");
        return factionsPlugin.getConquerPlayer(player).isAutoClaiming();
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
                        assertNotNull(player, "Player given to set auto claim mode of is null");
                        factionsPlugin.getConquerPlayer(player).setAutoClaiming(booleans[0]);
                    }
                }, new ASAChanger<Boolean>(){
            @Override
            public Changer.ChangeMode[] getChangeModes() {
                return new Changer.ChangeMode[]{Changer.ChangeMode.RESET};
            }

            @Override
            public void change(Boolean[] booleans) throws NullExpressionException {
                Player player = (Player) exp().get("player");
                assertNotNull(player, "Player given to reset auto claim mode of is null");
            }
        }
        };
    }
}
