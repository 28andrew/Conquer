package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.Location;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Faction Checkpoint")
@Description("Gets the checkpoint location of a faction")
@Examples({
        "set checkpoint of player's faction to player's location"
})
public class ExprFactionCheckpoint extends SimplePropertyExpression<ConquerFaction, Location> {
    static {
        register(ExprFactionCheckpoint.class, Location.class,
                "checkpoint [warp] [location]", "conquerfaction");
    }

    @Override
    protected String getPropertyName() {
        return "checkpoint location";
    }

    @Override
    public Location convert(ConquerFaction faction) {
        return faction.getCheckpoint();
    }

    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class<?>[]{Location.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0 || delta[0] == null) {
            return;
        }
        Location location = (Location) delta[0];

        ConquerFaction[] factions = getExpr().getArray(e);
        if (factions == null) {
            return;
        }

        for (ConquerFaction faction : factions) {
            if (faction == null) {
                continue;
            }
            faction.setCheckpoint(location);
        }
    }
}
