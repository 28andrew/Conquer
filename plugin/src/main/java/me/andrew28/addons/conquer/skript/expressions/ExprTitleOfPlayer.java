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
@Name("Faction Title of Player")
@Description("Gets the faction title of a player")
@Examples({
        "reset the player's title"
})
public class ExprTitleOfPlayer extends SimplePropertyExpression<ConquerPlayer, String> {
    static {
        register(ExprTitleOfPlayer.class, String.class, "[faction] title", "conquerplayers");
    }

    @Override
    protected String getPropertyName() {
        return "faction title";
    }

    @Override
    public String convert(ConquerPlayer player) {
        return player.getTitle();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class<?>[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerPlayer[] players = getExpr().getArray(e);
        if (players == null) {
            return;
        }

        String title = "";
        if (mode == Changer.ChangeMode.SET) {
            if (delta == null || delta.length == 0 || delta[0] == null) {
                return;
            }
            title = (String) delta[0];
        }

        for (ConquerPlayer player : players) {
            if (player == null) {
                continue;
            }
            player.setTitle(title);
        }
    }
}
