package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Name of Faction")
@Description("The name of a faction")
@Examples({
        "send \"You're in %name of player's faction%.\""
})
public class ExprNameOfFaction extends SimplePropertyExpression<ConquerFaction, String> {
    static {
        register(ExprNameOfFaction.class, String.class, "[the] [display] (name|tag)", "conquerfactions");
    }

    @Override
    protected String getPropertyName() {
        return "name";
    }

    @Override
    public String convert(ConquerFaction faction) {
        return faction.getName();
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
        if (faction == null) {
            return;
        }
        faction.setName((String) delta[0]);
    }
}
