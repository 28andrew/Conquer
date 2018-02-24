package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Auto Claim State of Player")
@Description("Gets whether a player is auto claiming")
@Examples({
        "if auto claim state of player is true:",
        "\t\"Fancy, aren't ya?\""
})
public class ExprAutoClaimState extends SimplePropertyExpression<ConquerPlayer, Boolean> {
    static {
        register(ExprAutoClaimState.class, Boolean.class,
                "auto [chunk] claim[ing] [state]", "conquerplayers");
    }

    @Override
    protected String getPropertyName() {
        return "auto claim state";
    }

    @Override
    public Boolean convert(ConquerPlayer player) {
        return player.isAutoClaiming();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class<?>[]{Boolean.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerPlayer[] players = getExpr().getArray(e);

        boolean state = false;
        if (mode == Changer.ChangeMode.SET) {
            if (delta == null || delta.length == 0 || delta[0] == null) {
                return;
            }
            state = (boolean) delta[0];
        }

        for (ConquerPlayer player : players) {
            if (player == null) {
                continue;
            }
            player.setAutoClaiming(state);
        }
    }
}
