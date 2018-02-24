package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

import java.util.Set;

/**
 * @author Andrew Tran
 */
@Name("Warp Names of Faction")
@Description("The warp names of a faction")
@Examples({
        "send \"Valid Warps: %warp names of player's faction%\""
})
public class ExprWarpNamesOfFaction extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprWarpNamesOfFaction.class, String.class, ExpressionType.PROPERTY,
                "[the] warp names of %conquerfaction%", "%conquerfaction%'s warp names");
    }

    private Expression<ConquerFaction> faction;

    @Override
    protected String[] get(Event e) {
        ConquerFaction faction = this.faction.getSingle(e);
        if (faction == null || faction.getWarps() == null) {
            return null;
        }
        Set<String> warps = faction.getWarps().keySet();
        return warps.toArray(new String[warps.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "warp names of " + faction.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        faction = (Expression<ConquerFaction>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.REMOVE ||
                mode == Changer.ChangeMode.REMOVE_ALL ||
                mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class<?>[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerFaction faction = this.faction.getSingle(e);
        if (faction == null) {
            return;
        }
        if (mode == Changer.ChangeMode.REMOVE) {
            if (delta == null || delta.length == 0 || delta[0] == null) {
                return;
            }
            String name = (String) delta[0];
            faction.getWarps().remove(name);
        } else {
            faction.getWarps().clear();
        }
    }
}
