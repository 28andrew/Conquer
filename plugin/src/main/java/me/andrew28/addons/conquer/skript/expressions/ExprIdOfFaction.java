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
@Name("Id of Faction")
@Description("The id of a faction")
@Examples({
        "send \"Your faction's id: %id of player's faction%\""
})
public class ExprIdOfFaction extends SimplePropertyExpression<ConquerFaction, String> {
    static {
        register(ExprIdOfFaction.class, String.class, "[unique] id[entifier]", "conquerfactions");
    }

    @Override
    protected String getPropertyName() {
        return "id";
    }

    @Override
    public String convert(ConquerFaction faction) {
        return faction.getId();
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
        faction.setId((String) delta[0]);
    }
}
