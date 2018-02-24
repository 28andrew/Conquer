package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("MOTD of Faction")
@Description("The message of the day (MOTD) of a faction")
public class ExprMotdOfFaction extends SimplePropertyExpression<ConquerFaction, String> {
    static {
        register(ExprMotdOfFaction.class, String.class,
                "[the] (motd|message of the day)", "conquerfactions");
    }

    @Override
    protected String getPropertyName() {
        return "motd";
    }

    @Override
    public String convert(ConquerFaction faction) {
        return faction.getMotd();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class<?>[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0 || delta[0] == null) {
            return;
        }
        ConquerFaction faction = getExpr().getSingle(e);
        faction.setMotd((String) delta[0]);
    }
}
