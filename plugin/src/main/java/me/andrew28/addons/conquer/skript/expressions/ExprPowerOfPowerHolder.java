package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.conquer.api.PowerHolder;
import me.andrew28.addons.conquer.api.PowerChangeable;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Power Of Player/Faction/Kingdom/Guild")
@Description("Retrieve the power of a player/kingdom/guild")
@BSyntax(syntax = {"[the] power [level] [of] [the] [(faction|kingdom|guild|player)] %powerholder%",
"[(faction|kingdom|guild|player)]  %powerholder%'s power [level]"}, bind = "powerholder")
@Examples({
        @Example({
                "message \"Your Power: %power of player%\"",
                "message \"Faction Power: %power of faction of player%\""
        })
})
public class ExprPowerOfPowerHolder extends ASAExpression<Double>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public Double getValue() throws NullExpressionException {
        PowerHolder powerHolder = (PowerHolder) exp().get("powerholder");
        assertNotNull(powerHolder, "Power holder given is null");
        return powerHolder.getPower();
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
                        assertNotNull(object, "Given powerchangeable to set the power of is null");
                        if (!(object instanceof PowerChangeable)){
                            Skript.error("Cannot change power of given object because object is not a PowerChangeable");
                            return;
                        }
                        PowerChangeable powerChangeable = (PowerChangeable) object;
                        powerChangeable.setPower(doubles[0]);
                    }
                }, new ASAChanger<Double>(){
            @Override
            public Changer.ChangeMode[] getChangeModes() {
                return new Changer.ChangeMode[]{Changer.ChangeMode.RESET};
            }

            @Override
            public void change(Double[] doubles) throws NullExpressionException {
                Object object = exp().get("powerholder");
                assertNotNull(object, "Given powerchangeable to reset the power of is null");
                if (!(object instanceof PowerChangeable)){
                    Skript.error("Cannot reset power of given object because object is not a PowerChangeable");
                    return;
                }
                PowerChangeable powerChangeable = (PowerChangeable) object;
                powerChangeable.resetPower();
            }
        }
        };
    }
}
