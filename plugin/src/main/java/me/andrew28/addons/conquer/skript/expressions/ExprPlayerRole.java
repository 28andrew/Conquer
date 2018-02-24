package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Faction Role of Player")
@Description("The role of a faction player")
public class ExprPlayerRole extends SimplePropertyExpression<ConquerPlayer, ConquerPlayer.Role>{
    static {
        register(ExprPlayerRole.class, ConquerPlayer.Role.class,
                "[the] [faction] role", "conquerplayers");
    }

    @Override
    protected String getPropertyName() {
        return "faction role";
    }

    @Override
    public ConquerPlayer.Role convert(ConquerPlayer player) {
        return player.getRole();
    }

    @Override
    public Class<? extends ConquerPlayer.Role> getReturnType() {
        return ConquerPlayer.Role.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class<?>[]{ConquerPlayer.Role.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerPlayer[] players = getExpr().getArray(e);
        if (players == null) {
            return;
        }

        ConquerPlayer.Role role = ConquerPlayer.Role.NORMAL;
        if (mode == Changer.ChangeMode.SET) {
            if (delta == null || delta.length == 0 || delta[0] == null) {
                return;
            }
            role = (ConquerPlayer.Role) delta[0];
        }
        for (ConquerPlayer player : players) {
            if (player == null) {
                continue;
            }
            player.setRole(role);
        }
    }
}
