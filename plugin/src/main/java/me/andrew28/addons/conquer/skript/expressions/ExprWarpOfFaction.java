package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.Location;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Warp of Faction")
@Description("The warp with a given name of a faction")
public class ExprWarpOfFaction extends SimpleExpression<Location> {
    static {
        Skript.registerExpression(ExprWarpOfFaction.class, Location.class, ExpressionType.COMBINED,
                "[the] warp [with] [name] %string% (of|from) %conquerfaction%",
                "%conquerfaction%'s %string% warp");
    }

    private Expression<String> name;
    private Expression<ConquerFaction> faction;

    @Override
    protected Location[] get(Event e) {
        String name = this.name.getSingle(e);
        ConquerFaction faction = this.faction.getSingle(e);
        if (name == null || faction == null || faction.getWarps() == null) {
            return null;
        }
        return new Location[]{faction.getWarps().get(name)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "warp " + name.toString(e, debug) + " of " + faction.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        faction = (Expression<ConquerFaction>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET ||
                mode == Changer.ChangeMode.REMOVE ||
                mode == Changer.ChangeMode.REMOVE_ALL ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class<?>[]{Location.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String name = this.name.getSingle(e);
        ConquerFaction faction = this.faction.getSingle(e);
        if (name == null || faction == null || faction.getWarps() == null) {
            return;
        }
        switch (mode) {
            case SET:
                if (delta == null || delta.length == 0 || delta[0] == null) {
                    return;
                }
                faction.getWarps().put(name, (Location) delta[0]);
                break;
            case REMOVE:
            case REMOVE_ALL:
            case DELETE:
                faction.getWarps().remove(name);
                break;
        }
    }
}
