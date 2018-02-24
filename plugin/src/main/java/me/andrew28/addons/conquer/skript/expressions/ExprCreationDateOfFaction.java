package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Creation Date of Faction")
@Description("The creation date of a faction")
@Examples(
        "send \"Your faction was created %difference between now and creation date of player's faction% ago.\""
)
public class ExprCreationDateOfFaction extends SimplePropertyExpression<ConquerFaction, Date> {
    static {
        register(ExprCreationDateOfFaction.class, Date.class, "[the] creation date", "conquerfactions");
    }

    @Override
    protected String getPropertyName() {
        return "creation date";
    }

    @Override
    public Date convert(ConquerFaction faction) {
        return new Date(faction.getCreationDate().getTime());
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
        if (delta == null || delta.length == 0 || delta[0] == null) {
            return;
        }
        ConquerFaction faction = getExpr().getSingle(e);
        faction.setCreationDate(new java.util.Date(((Date) delta[0]).getTimestamp()));
    }
}
