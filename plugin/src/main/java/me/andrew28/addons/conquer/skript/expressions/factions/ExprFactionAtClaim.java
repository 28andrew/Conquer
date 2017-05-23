package me.andrew28.addons.conquer.skript.expressions.factions;

import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Faction/Kingdom/Guild At Claim")
@Description("Retrieve the faction/kingdom/guild at a given claim")
@BSyntax(syntax = "[the] (faction|kingdom|guild) at claim %conquerclaim%", bind = "claim")
@Examples({
        @Example({
                "message \"Faction at your claim: %faction at claim at player%\""
        })
})
public class ExprFactionAtClaim extends ASAExpression<ConquerFaction>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public ConquerFaction getValue() throws NullExpressionException {
        Claim claim = (Claim) exp().get("claim");
        assertNotNull(claim, "Claim given is null");
        return factionsPlugin.getFaction(claim);

    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
                new ASAChanger<ConquerFaction>(){
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
                    }

                    @Override
                    public void change(ConquerFaction[] factions) throws NullExpressionException {
                        Claim claim = (Claim) exp().get("claim");
                        assertNotNull(claim, "Claim given to set faction of is null");
                        claim.setFaction(factions[0]);
                    }
                }, new ASAChanger<ConquerFaction>(){
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.RESET, Changer.ChangeMode.REMOVE_ALL};
                    }

                    @Override
                    public void change(ConquerFaction[] factions) throws NullExpressionException {
                        Claim claim = (Claim) exp().get("claim");
                        assertNotNull(claim, "Claim given to reset faction of is null");
                        claim.resetFaction();
                    }
                }
        };
    }
}
