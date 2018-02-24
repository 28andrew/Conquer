package me.andrew28.addons.conquer.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Warp of Faction Has Password")
@Description("Whether a warp of a faction has a password")
@Examples({
        "if the warp \"jail\" of player's faction has a password:",
        "\tsend \"You'll need a password to enter the jail warp.\""
})
public class CondWarpHasPass extends Condition{
    static {
        Skript.registerCondition(CondWarpHasPass.class,
                "[the] warp[s] [with] [name][s] %strings% (of|from) [the] [faction] %conquerfaction%" +
                        " (use[s]|ha(s|ve)) (a password|authentication)",
                    "[the] warp[s] [with] [name][s] %strings% (of|from) [the] [faction] %conquerfaction%" +
                            " (doesn't| does not|don't|do not) (use|has) (a password|authentication)");
    }

    private Expression<String> names;
    private Expression<ConquerFaction> faction;

    @Override
    public boolean check(Event e) {
        ConquerFaction faction = this.faction.getSingle(e);
        return faction != null && names.check(e, faction::hasWarpPassword, isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "warp" + (names.isSingle() ? "" : "s") + " " + names.toString(e, debug) + " of "
                + faction.toString(e, debug) + (isNegated() ? "doesn't" : "does") + " use a password";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        names = (Expression<String>) exprs[0];
        faction = (Expression<ConquerFaction>) exprs[1];
        setNegated(matchedPattern == 1);
        return true;
    }
}
