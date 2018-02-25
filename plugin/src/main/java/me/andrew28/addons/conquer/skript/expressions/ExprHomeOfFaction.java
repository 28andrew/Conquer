package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.Location;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Home of Faction")
@Description("The home location of a faction")
@Examples({
        "teleport the player to home of player's faction"
})
public class ExprHomeOfFaction extends SimplePropertyExpression<ConquerFaction, Location> {
    static {
        register(ExprHomeOfFaction.class, Location.class, "home [location]", "conquerfactions");
    }

    @Override
    protected String getPropertyName() {
        return "creation date";
    }

    @Override
    public Location convert(ConquerFaction faction) {
        return faction.getHome();
    }

    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
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
        if (faction == null) {
            return;
        }
        faction.setHome((Location) delta[0]);
    }
}
