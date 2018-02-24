package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.PowerHolder;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Normal/Maximum/Boosting Power")
@Description("The normal/maximum/boosting power of a faction or player")
@Examples({
        "set the power of the player to the player's maximum power"
})
public class ExprPower extends SimplePropertyExpression<PowerHolder, Double> {
    static {
        register(ExprPower.class, Double.class,
                "[the] (1¦power|2¦max[imum] power|3¦power [boost][er])", "powerholders");
    }

    private int mark;

    @Override
    protected String getPropertyName() {
        switch (mark) {
            case 1:
                return "power";
            case 2:
                return "maximum power";
            case 3:
                return "power boost";
        }
        return null;
    }

    @Override
    public Double convert(PowerHolder powerHolder) {
        if (powerHolder == null) {
            return null;
        }
        switch (mark) {
            case 1:
                return powerHolder.getPower();
            case 2:
                return powerHolder.getMaximumPower();
            case 3:
                return powerHolder.getPowerBoost();
        }
        return null;
    }

    @Override
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        mark = parseResult.mark;
        return super.init(exprs, matchedPattern, isDelayed, parseResult);
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mark == 1 || mark == 3) {
            return new Class<?>[]{Double.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Double amount;
        if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.REMOVE_ALL) {
            amount = 0D;
        } else {
            if (delta == null || delta.length == 0) {
                return;
            }
            amount = delta[0] == null ? 0D : (Double) delta[0];
        }
        PowerHolder powerHolder = getExpr().getSingle(e);
        if (powerHolder == null) {
            return;
        }
        switch (mark) {
            case 1:
                powerHolder.setPower(amount);
                break;
            case 3:
                powerHolder.setPowerBoost(amount);
                break;
        }
    }
}
