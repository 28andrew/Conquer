package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Last Activity of Faction Player")
@Description("Gets the last activity of a faction player")
public class ExprPlayerLastActivity extends SimplePropertyExpression<ConquerPlayer, Date> {
    static {
        register(ExprPlayerLastActivity.class, Date.class,
                "[the] last [faction] (activity|login) [date]", "conquerplayers");
    }

    @Override
    protected String getPropertyName() {
        return "last activity date";
    }

    @Override
    public Date convert(ConquerPlayer player) {
        return new Date(player.getLastActivity().getTime());
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class<?>[]{Date.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerPlayer player = getExpr().getSingle(e);
        if (player == null || delta == null || delta.length == 0 || delta[0] == null) {
            return;
        }
        player.setLastActivity(new java.util.Date(((Date) delta[0]).getTimestamp()));
    }
}
