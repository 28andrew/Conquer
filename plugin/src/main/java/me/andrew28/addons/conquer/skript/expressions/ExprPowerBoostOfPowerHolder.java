package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.api.PowerHolder;
import me.andrew28.addons.conquer.api.PowerChangeable;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Power Boost of Player/Faction/Kingdom/Guild")
@Description("Retrieve the power boost of a given player/faction/kingdom/guild")
@BSyntax(syntax = {"[the] power[ ]boost [level] [of] [the] [(faction|kingdom|guild|player)] %powerholder%",
"[the] [(faction|kingdom|guild|player)] %powerholder%'s power[ ]boost [level]"}, bind = "powerholder")
@Examples({
        @Example({
                "message \"Your Power Boost: %power boost of player%\"",
                "message \"Faction Power Boost: %power boost of faction of player%\""
        })
})
public class ExprPowerBoostOfPowerHolder extends ASAExpression<Double>{
    @Override
    public Double getValue() throws NullExpressionException {
        PowerHolder powerHolder = (PowerHolder) exp().get("powerholder");
        assertNotNull(powerHolder, "Power holder given is null");
        return powerHolder.getPowerBoost();
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
                new ASAChanger<Double>(){
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
                    }

                    @Override
                    public void change(Double[] doubles) throws NullExpressionException {
                        Object object = exp().get("powerholder");
                        assertNotNull(object, "Power Changeable given to the power boost of is null");
                        if (!(object instanceof PowerChangeable)){
                            Skript.error("Cannot set power boost of given object because object is not a PowerChangeable");
                            return;
                        }
                        PowerChangeable powerChangeable = (PowerChangeable) object;
                        powerChangeable.setPowerBoost(doubles[0]);
                    }
                }, new ASAChanger<Double>(){
            @Override
            public Changer.ChangeMode[] getChangeModes() {
                return new Changer.ChangeMode[]{Changer.ChangeMode.RESET};
            }

            @Override
            public void change(Double[] doubles) throws NullExpressionException {
                Object object = exp().get("powerholder");
                assertNotNull(object, "Power Changeable given to the power boost of is null");
                if (!(object instanceof PowerChangeable)){
                    Skript.error("Cannot reset power boost of given object because object is not a PowerChangeable");
                    return;
                }
                PowerChangeable powerChangeable = (PowerChangeable) object;
                powerChangeable.resetPowerBoost();
            }
        }
        };
    }
}
